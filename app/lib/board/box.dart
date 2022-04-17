import 'dart:developer';
import 'package:app/game/game-controller.dart';
import 'package:app/game/game-state.dart';
import 'package:chess_vectors_flutter/chess_vectors_flutter.dart';
import 'package:flutter/material.dart';

final pieces = [
  [
    WhitePawn(),
    WhiteKnight(),
    WhiteBishop(),
    WhiteRook(),
    WhiteQueen(),
    WhiteKing()
  ],
  [
    BlackPawn(),
    BlackKnight(),
    BlackBishop(),
    BlackRook(),
    BlackQueen(),
    BlackKing()
  ]
];

class Box extends StatefulWidget {
  final Position position;
  late GameController controller;
  final double height;
  final double width;

  Box(
      {required this.position,
      required this.controller,
      required this.height,
      required this.width}) {}

  @override
  State<Box> createState() => BoxState();
}

class BoxState extends State<Box> {
  GamePiece? piece;

  @override
  Widget build(BuildContext context) {
    return ValueListenableBuilder<GameState>(
        valueListenable: widget.controller,
        builder: (context, gameState, _) {
          GameState gameState = widget.controller.gameState;
          piece = gameState.getPiece(widget.position);
          Widget? child;
          if (piece != null) {
            child = ActivePiece(piece!, this);
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
              GamePiece draggedPiece = data.piece;
              BoxState sourceState = data.boxState;
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

class ActivePiece extends StatelessWidget {
  GamePiece piece;
  BoxState boxState;
  ActivePiece(this.piece, this.boxState);

  @override
  Widget build(BuildContext context) {
    return Draggable<ActivePiece>(
        data: this,
        child: pieces[piece.color.index][piece.type.index],
        feedback: pieces[piece.color.index][piece.type.index],
        childWhenDragging: ColorFiltered(
            colorFilter: const ColorFilter.mode(
              Colors.grey,
              BlendMode.saturation,
            ),
            child: pieces[piece.color.index][piece.type.index]));
  }
}
