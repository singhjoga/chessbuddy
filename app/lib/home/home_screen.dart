import 'package:app/board/board.dart';
import 'package:app/game/game-controller.dart';
import 'package:app/game/game-state.dart';
import 'package:flutter/material.dart';
import 'package:app/home/left_menu.dart';

class HomePage extends StatelessWidget {
  final _intialFEN = 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1';
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    GameController controller = GameController.fromFEN(_intialFEN);
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
