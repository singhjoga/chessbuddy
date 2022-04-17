import 'dart:developer';

import 'package:chess/chess.dart' as chess;
import 'package:flutter/material.dart';
class GameState{
  late chess.Chess game;
  bool isAgainstHumanPlayer=false;
  PieceColor localPieceColor = PieceColor.white;

  GameState(String startFen){
    game = chess.Chess();
    game.update_setup(startFen);
  }

  GamePiece? getPiece(Position pos) {
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

    return GamePiece(pos, pt, color);
  }
  makeMove(GamePiece piece, Position pos) {
    bool moved = game.move({"from": piece.pos.toBoardId(), "to": pos.toBoardId()});
    if (moved) {
      log('Moved: from ${piece.pos.toBoardId()} to ${pos.toBoardId()}');
    }else {
      log('Move failed: from ${piece.pos.toBoardId()} to ${pos.toBoardId()}');
    }
    return moved;
  }
  Color getBoardColor(Position pos) {
    bool isRankOdd = pos.rank % 2 == 1;
    bool isFileOdd = pos.file % 2 == 1;
    Color color = ((isRankOdd && isFileOdd) || (!isRankOdd && !isFileOdd) )? Colors.lightGreen: Colors.pink;
    return color;
  }
  makeComputerMove(){
    var moves = game.moves();
    var move = moves[0];
    return game.move(move);
  }
}

class GamePiece {
  Position pos;
  PieceType type;
  PieceColor color;

  GamePiece(this.pos, this.type, this.color);
}

class Position {
  static const validFiles = 'abcdefgh';
  static const files = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
  late int rank;
  late int file;

  Position(this.rank, this.file);
  Position.of(String boardPosition) {
    rank = int.parse(boardPosition.substring(1, 2));
    file = validFiles.indexOf(boardPosition.substring(0, 1)) + 1;
  }
  String toBoardId() {
    return '${files[file - 1]}$rank';
  }
}

enum PieceType { pawn, knight, bishop, rook, queen, king }

enum PieceColor { white, black }
