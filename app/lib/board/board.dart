
import 'package:app/board/box.dart';
import 'package:app/game/game-controller.dart';
import 'package:app/game/game-state.dart';
import 'package:flutter/material.dart';

class Board extends StatelessWidget {
  final int squareCount=8;
  GameState gameState;
  GameController controller;
  Board(this.controller): gameState=controller.gameState;

  @override
  Widget build(BuildContext context) {

      return LayoutBuilder( builder: (context, constraints) {
        double sideLength = constraints.maxWidth/squareCount;
        return Padding(
            padding: const EdgeInsets.all(4.0),
            child:  Column(
                children: [
                  player1Area(sideLength),
                  const Divider(height: 4,thickness: 2),
                  PlayArea(squareCount,controller),
                  const Divider(height: 4,thickness: 2),
                  player2Area(sideLength)
                ]
            )
        );
      },
  //    ),
    );
  }
  Container player1Area(double sideLength) {
    Position pos = Position(0, 0);
    Color color = Colors.grey;
    return Container(color: color, height: sideLength, width: sideLength*8);
  }
  Container player2Area(double sideLength) {
    Position pos = Position(0, 0);
    Color color = Colors.grey;
    return Container(color: color, height: sideLength, width: sideLength*8);
  }
}


class PlayArea extends StatelessWidget {
  final int squareCount;
  final GameController controller;
  const PlayArea(this.squareCount, this.controller);

  Widget build(BuildContext context) {

    return AspectRatio(
      aspectRatio: 1,
      child: LayoutBuilder( builder: (context, constraints) {
        double sideLength = constraints.maxWidth/squareCount;
        return Column(
          children: List.generate(squareCount, (rank) => Expanded(
              child: Row(
                children: List.generate(squareCount, (file) => createBox(8-rank, file+1, sideLength)),
              )
          )
          ),
        );
      },
      ),
    );
  }

  Box createBox(int rank, int file, double sideLength) {
    Position pos = Position(rank, file);

    return Box(position: pos, height: sideLength, width: sideLength, controller: controller);
  }

}