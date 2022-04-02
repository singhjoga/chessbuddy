import 'package:app/board/board.dart';
import 'package:flutter/material.dart';
import 'package:app/home/left_menu.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
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
      body: Board(),
    );
  }
}
