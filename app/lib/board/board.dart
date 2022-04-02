
import 'package:app/board/box.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class Board extends StatelessWidget {
  final int squareCount=8;
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
                  PlayArea(squareCount),
                  const Divider(height: 4,thickness: 2),
                  player2Area(sideLength),
                  SvgPicture.asset(
                      'lib/assets/pieces/k.svg',
                      color: Colors.red,
                      semanticsLabel: 'A red up arrow'
                  )
                ]
            )
        );
      },
  //    ),
    );
  }
  Box player1Area(double sideLength) {
    Position pos = Position(0, 0);
    Color color = Colors.grey;

    return Box(position: pos, color: color, height: sideLength, width: sideLength*8);
  }
  Box player2Area(double sideLength) {
    Position pos = Position(0, 0);
    Color color = Colors.grey;

    return Box(position: pos, color: color, height: sideLength, width: sideLength*8);
  }
}


class PlayArea extends StatelessWidget {
  final int squareCount;

  const PlayArea(this.squareCount);

  Widget build(BuildContext context) {

    return AspectRatio(
      aspectRatio: 1,
      child: LayoutBuilder( builder: (context, constraints) {
        double sideLength = constraints.maxWidth/squareCount;
        return Column(
          children: List.generate(squareCount, (rank) => Expanded(
              child: Row(
                children: List.generate(squareCount, (file) => createBox(rank, file, sideLength)),
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
    bool isRankOdd = rank % 2 == 1;
    bool isFileOdd = file % 2 == 1;
    Color color = ((isRankOdd && isFileOdd) || (!isRankOdd && !isFileOdd) )? Colors.lightGreen: Colors.pink;

    return Box(position: pos, color: color, height: sideLength, width: sideLength);
  }

}