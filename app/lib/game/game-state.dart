import 'dart:developer';

import 'package:app/common/model/move.dart';
import 'package:app/common/service/http_client.dart';
import 'package:chess/chess.dart' as chess;
import 'package:flutter/material.dart';
class GameState{
  late chess.Chess game;
  late HttpService http;
  bool isAgainstHumanPlayer=false;
  late PieceColor myColor;
  late PieceColor opponentColor;
  GameState(String startFen, [String color="white"]){
    game = chess.Chess.fromFEN(startFen);
    http = HttpService();
    myColor = PieceColor.white.name == color?PieceColor.white:PieceColor.black;
    opponentColor = PieceColor.white.name != color?PieceColor.white:PieceColor.black;
  }

  PieceInfo? getPiece(Position pos) {
    if (pos.file == 0 || pos.rank == 0) {
      return null;
    }
    String boardId = pos.toBoardId();
    chess.Piece? piece = game.get(boardId);
    if (piece == null) {
      return null;
    }
    PieceType pt = PieceType.values.elementAt(piece.type.shift);
    PieceColor color = PieceColor.values.elementAt(piece.color.index);

    return PieceInfo(pos, pt, color);
  }
  bool makeMove(PieceInfo piece, Position pos) {
    return makeMoveFromTo(piece.pos.toBoardId(), pos.toBoardId());
  }
  bool makeMoveFromMoveString(String moveStr) {
    List<String> moves = moveStr.split("-");
    String from = moves.elementAt(0);
    String to = moves.elementAt(1);
    return makeMoveFromTo(from, to);
  }
  bool makeMoveFromTo(String from, String to) {
    bool moved = game.move({"from": from, "to": to});
    if (moved) {
      log('Moved: from $from to $to');
    }else {
      log('Move failed: from $from to $to');
    }
    return moved;
  }
  Color getBoardColor(Position pos) {
    bool isRankOdd = pos.rank % 2 == 1;
    bool isFileOdd = pos.file % 2 == 1;
    Color color = ((isRankOdd && isFileOdd) || (!isRankOdd && !isFileOdd) )? Colors.lightGreen: Colors.pink;
    return color;
  }
  makeComputerMove() async{
    String fen = game.generate_fen();
    String reversed = _flipFen(fen);
    MoveRequest req = MoveRequest(fen);
    log("Move request 1: $fen");
   // log("Move request 2: $reversed");
    Map<String, dynamic> resp = await http.post("http://10.0.2.2:8080/engine/move", req);
    String move = resp["move"];
    log("Move response: $move");
    return makeMoveFromMoveString(move);
  }
  _flipFile(String move) {
    String correct = 'abcdefgh';
    String reversed = 'hgfedcba';
    String file = move.characters.elementAt(0);
    String rank = move.characters.elementAt(1);
    int fileIndex = correct.indexOf(file);
    String newFile = reversed.characters.elementAt(fileIndex);
    return newFile+rank;
  }
  _flipFen(String fen) {
    List<String> parts = fen.split(" ");
    String reversed = parts[0].split('/').reversed.join('/');
    parts[0]=reversed;
    return parts.join(' ');
  }
  bool isInCheck(PieceColor color) {
    return game.turn.index == color.index && game.in_check;
  }
  bool hasWonMe() {
    return hasWon(myColor);
  }
  bool hasLostMe() {
    return hasLost(myColor);
  }
  bool hasWon(PieceColor color) {
    return game.turn.index != color.index && game.in_checkmate;
  }
  bool hasLost(PieceColor color) {
    return game.turn.index == color.index && game.in_checkmate;
  }
  Map<PieceType, int> getCapturedPiecesByColor(PieceColor color) {
    PieceColor capturedColor = color == PieceColor.white?PieceColor.black: PieceColor.white;
    //by default all are captured
    Map<PieceType, int> result = {
      PieceType.king: 1,
      PieceType.queen: 1,
      PieceType.bishop: 2,
      PieceType.rook: 2,
      PieceType.knight: 2,
      PieceType.pawn: 8
    };
    for (String file in Position.files) {
      for (int rank=1; rank <= 8; rank++) {
        String pos='$file$rank';
        chess.Piece? piece = game.get(pos);
        if (piece == null) continue;
        PieceType pt = PieceType.values.elementAt(piece.type.shift);
        PieceColor color = PieceColor.values.elementAt(piece.color.index);
        if (color != capturedColor) continue;
        int? capturedCount = result[pt];
        if (capturedCount == null) continue;
        capturedCount--;
        result[pt]=capturedCount;
      }
    }

    return result;
  }
}

class PieceInfo {
  Position pos;
  PieceType type;
  PieceColor color;

  PieceInfo(this.pos, this.type, this.color);
}

class Position {
  static const validFiles = 'abcdefgh';
  static const files = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
  late int rank;
  late int file;
  late GameState game;
  Position(this.rank, this.file, this.game);
  Position.of(String boardPosition, GameState game1) {
    rank = int.parse(boardPosition.substring(1, 2));
    file = validFiles.indexOf(boardPosition.substring(0, 1)) + 1;
    game=game1;
  }
  String toBoardId() {
    if (game.myColor == PieceColor.white) {
      return '${files[file - 1]}$rank';
    }else{
      int r = 9-rank;
      return '${files[8-file]}$r';
    }

  }
}

enum PieceType { pawn, knight, bishop, rook, queen, king }

enum PieceColor { white, black }
