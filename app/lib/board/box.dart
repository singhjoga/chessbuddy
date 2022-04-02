
import 'package:flutter/cupertino.dart';

class Box extends StatelessWidget {
  final Position position;
  final Color color;
  final Widget? piece;
  final double height;
  final double width;
  Box({required this.position, required this.color, this.piece, required this.height, required this.width});

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
        builder: (context, constraints) {
      /*    double _side = 50;
          if (constraints.maxWidth != double.infinity) {
            _side = constraints.maxWidth;
          } else if (constraints.maxHeight != double.infinity) {
            _side = constraints.maxHeight;
          }

       */
          return GestureDetector(
            child: Container(
              height: height,
              width: width,
              color: color,
            ),
          );
        }
    );
  }

}

class Position {
  int rank;
  int file;

  Position(this.rank, this.file);
}