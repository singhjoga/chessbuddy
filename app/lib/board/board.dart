
import 'package:app/board/box.dart';
import 'package:app/board/piece.dart';
import 'package:app/game/game-controller.dart';
import 'package:app/game/game-state.dart';
import 'package:blinking_text/blinking_text.dart';
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
                  playerArea(sideLength, gameState.opponentColor),
                  const Divider(height: 4,thickness: 2),
                  GameArea(squareCount,controller),
                  const Divider(height: 4,thickness: 2),
                  playerArea(sideLength, gameState.myColor)
                ]
            )
        );
      },
  //    ),
    );
  }
  Container playerArea(double sideLength, PieceColor player) {
    Position pos = Position(0, 0, gameState);
    Color color = Colors.grey;
    return Container(color: color, height: sideLength, width: sideLength*8,
    child: PlayerArea(controller, gameState, player));
  }
}
class PlayerArea extends StatefulWidget {
  final GameState gameState;
  final PieceColor player;
  final GameController controller;
  PlayerArea(this.controller, this.gameState, this.player);

  @override
  State<PlayerArea> createState() => PlayerState();
}
class PlayerState extends State<PlayerArea> {

  @override
  Widget build(BuildContext context) {
    return ValueListenableBuilder<GameState>(
        valueListenable: widget.controller,
        builder: (context, gameState, _) {

          return Row(
              children: [
                if (widget.gameState.isInCheck(widget.player) && !widget.gameState.hasLost(widget.player)) const BlinkText(
                  'Check!',
                  style: TextStyle(fontSize: 36.0, color: Colors.red, fontWeight: FontWeight.bold),
                ),
                if (widget.gameState.hasWon(widget.player)) const BlinkText(
                  'Won!',
                  style: TextStyle(fontSize: 36.0, color: Colors.green, fontWeight: FontWeight.bold)
                ),
                Row(
                  children: getCapturedPieces(),
                )
              ]
          );
        });
  }
  List<Widget> getCapturedPieces() {
    Map<PieceType, int> capturedPieces = widget.gameState.getCapturedPiecesByColor(widget.player);
    List<Widget> result = [];
    capturedPieces.forEach((pieceType, count) {
      if (count > 0 ) {
        PieceColor capturedColor = widget.player == PieceColor.white?PieceColor.black: PieceColor.white;
        Position pos = Position(0, 0, widget.gameState);
        PieceInfo pieceInfo = PieceInfo(pos,pieceType,capturedColor);
        VisualPiece vp =  VisualPiece(pieceInfo);
        Container container = Container(
          child: Stack(
            children: [
              vp,
              if (count > 1)
                Positioned(
                  top: 0,
                  right: 0,
                  child: Container(
                    padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
                    decoration: const BoxDecoration(
                        shape: BoxShape.circle, color: Colors.red),
                    alignment: Alignment.center,
                    child: Text('$count'),
                  ),
                )
            ],
          ),
        );
        result.add(container);
      }
    });

    return result;
  }
}
class GameArea extends StatelessWidget {
  final int squareCount;
  final GameController controller;
  const GameArea(this.squareCount, this.controller);

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
    Position pos = Position(rank, file, controller.gameState);

    return Box(position: pos, height: sideLength, width: sideLength, controller: controller);
  }

}