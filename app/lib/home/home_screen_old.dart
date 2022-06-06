import 'package:app/board/board.dart';
import 'package:app/common/exceptions/exceptions.dart';
import 'package:app/game/game-channel.dart';
import 'package:app/game/game-controller.dart';
import 'package:app/game/game-state.dart';
import 'package:flutter/material.dart';
import 'package:app/home/left_menu.dart';

class HomePageOld extends StatelessWidget {
 // final _initialFEN = 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1';
  final _initialFEN = 'r1b1k1r1/pppp1ppp/n3p2P/P7/7q/P5P1/2PPPP2/1NBQKBNn w q - 0 10';
  const HomePageOld({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    GameController controller = GameController.fromFEN(_initialFEN);
    return Scaffold(
      drawer: const Drawer(
        child: LeftMenu(),
      ),
      appBar: AppBar(
        leading: Builder(builder: (context) => // Ensure Scaffold is in context
          IconButton(
            icon: const Icon(Icons.menu),
            onPressed: () => Scaffold.of(context).openDrawer()
         ),
        ),
        title: const Text('Chess Buddies'),
        actions: const [Icon(Icons.supervised_user_circle), Icon(Icons.more_vert)],
      ),
      body: Board(controller),
    );
  }
}
