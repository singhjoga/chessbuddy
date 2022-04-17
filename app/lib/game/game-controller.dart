import 'package:app/game/game-state.dart';
import 'package:flutter/material.dart';

class GameController extends ValueNotifier<GameState> {
  final GameState gameState;
  //factory GameController() => GameController._(GameState(intialFEN));
  factory GameController.fromFEN(String fen) => GameController._(GameState(fen));
  GameController._(this.gameState): super(gameState);

  makeMove(GamePiece piece, Position pos) {
    if (gameState.makeMove(piece, pos)) {
      notifyListeners();
      return true;
    }
    return false;
  }

  makeComputerMove() async{
    if (gameState.makeComputerMove()) {
      notifyListeners();
    }
  }
}