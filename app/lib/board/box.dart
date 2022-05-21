import 'package:app/board/piece.dart';
import 'package:app/game/game-controller.dart';
import 'package:app/game/game-state.dart';
import 'package:flutter/material.dart';


class Box extends StatefulWidget {
  final Position position;
  final GameController controller;
  final double height;
  final double width;

  const Box(
      {required this.position,
      required this.controller,
      required this.height,
      required this.width});

  @override
  State<Box> createState() => BoxState();
}

class BoxState extends State<Box> {
  PieceInfo? piece;

  @override
  Widget build(BuildContext context) {
    return ValueListenableBuilder<GameState>(
        valueListenable: widget.controller,
        builder: (context, gameState, _) {
          GameState gameState = widget.controller.gameState;
          piece = gameState.getPiece(widget.position);
          Widget? child;
          if (piece != null) {
            child = ActivePiece(piece!);
          }
          var box = Container(
              height: widget.height,
              width: widget.width,
              color: gameState.getBoardColor(widget.position),
              child: child);
          var dragTarget = DragTarget<ActivePiece>(
            builder: (
              BuildContext context,
              List<dynamic> accepted,
              List<dynamic> rejected,
            ) {
              return box;
            },
            onAccept: (ActivePiece data) async {
              PieceInfo draggedPiece = data.piece;
              bool moved =
                  widget.controller.makeMove(draggedPiece, widget.position);
              if (moved) {
                if (!widget.controller.gameState.isAgainstHumanPlayer) {
                  widget.controller.makeComputerMove();
                }
              }
            },
          );

          return LayoutBuilder(builder: (context, constraints) {
            return GestureDetector(
              child: dragTarget,
            );
          });
        });
  }

  refresh() {
    setState(() {});
  }
}
