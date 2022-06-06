import 'package:app/home/login.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}
class _HomeState extends State<HomePage> {
  bool loggedIn=false;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
          title: const Text('Chess Buddies'),
          backgroundColor: Colors.teal),
      body: _MainMenu(),
    );
  }
}

class _MainMenu extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          MainButton("Login", () {
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => const LoginForm()),
            );
          }),
          MainButton("Play with Buddy", () {

          })
        ],
      )
    );
  }

}

class MainButton  extends StatelessWidget{
  String text;
  VoidCallback onPressed;

  MainButton(this.text, this.onPressed);

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      style: TextButton.styleFrom(
        textStyle: const TextStyle(fontSize: 20),
      ),
      onPressed: onPressed,
      child: Text(text),
    );
  }

}