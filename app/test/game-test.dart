
import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:chess/chess.dart' as chess;
void main() {
  _flipFen(String fen) {
    List<String> parts = fen.split(" ");
    String reversed = parts[0].split('/').reversed.join('/');
    parts[0]=reversed;
    return parts.join(' ');
  }
  test("Game promotion", (){
    String startFen="4K1N1/2p1PP1n/6P1/3b3P/4p3/2P5/pb1n1ppp/R5kr b - - 0 22";
    chess.Chess game = chess.Chess.fromFEN(startFen);
    // "flags":"p","square":"c2"
    List<dynamic> moves = game.moves({"asObjects":true,  "verbose": true,"square":"f2"});
    for (chess.Move move in moves) {
      debugPrint(move.fromAlgebraic+'-'+move.toAlgebraic+" "+(move.promotion==null?"":move.promotion!.name));
    }
  });
  test("Game checkmate", (){
    String startFen="r1b1k1r1/pppp1ppp/n3p2P/P7/7q/P4PP1/2PPP3/1NBQKBNn b q - 0 10";
    chess.Chess game = chess.Chess.fromFEN(startFen);
    // "flags":"p","square":"c2"
    game.move({"from":"h4","to":"g3"});
    expect(game.in_checkmate,true);
    if (game.in_checkmate) {
      debugPrint("Checkmate");
    }
  });
  test("Game in check", (){
    String startFen="r1bqk2r/pppp1ppp/2n1p3/P2n2b1/1P6/2PP3P/4P1P1/1N1QKBNR b Kkq - 0 9";
    chess.Chess game = chess.Chess.fromFEN(startFen);
    // "flags":"p","square":"c2"
    game.move({"from":"g5","to":"h4"});
    expect(game.in_check,true);
    if (game.in_check) {
      debugPrint("In Check");
    }
  });


}