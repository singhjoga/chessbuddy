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

class VisualPiece extends StatelessWidget {
  PieceInfo piece;

  VisualPiece(this.piece);

  @override
  Widget build(BuildContext context) {
    return pieces[piece.color.index][piece.type.index];
  }
}


class ActivePiece extends StatelessWidget {
  PieceInfo piece;
  ActivePiece(this.piece);

  @override
  Widget build(BuildContext context) {
    VisualPiece visualPiece = VisualPiece(piece);
    return Draggable<ActivePiece>(
        data: this,
        child: visualPiece,
        feedback: visualPiece,
        childWhenDragging: ColorFiltered(
            colorFilter: const ColorFilter.mode(
              Colors.grey,
              BlendMode.saturation,
            ),
            child: visualPiece));
  }
}
