
import 'package:flutter/material.dart';

class LeftMenu extends StatelessWidget {
  const LeftMenu({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView(
      padding: EdgeInsets.zero,
      children: <Widget>[
        const Padding(
          padding: EdgeInsets.all(16.0),
          child: Text(
            'Header',
          ),
        ),
        const Divider(
          height: 1,
          thickness: 1,
        ),
        ListTile(
          leading: const Icon(Icons.favorite),
          title: const Text('Item 1'),
          onTap: () => selectDestination(0),
        ),
        ListTile(
          leading: const Icon(Icons.delete),
          title: const Text('Item 2'),
          onTap: () => selectDestination(1),
        ),
        ListTile(
          leading: const Icon(Icons.label),
          title: const Text('Item 3'),
          onTap: () => selectDestination(2),
        ),
        const Divider(
          height: 1,
          thickness: 1,
        ),
        const Padding(
          padding: EdgeInsets.all(16.0),
          child: Text(
            'Label',
          ),
        ),
        ListTile(
          leading: const Icon(Icons.bookmark),
          title: const Text('Item A'),
          onTap: () => selectDestination(3),
        )
      ],
    );
  }
  void selectDestination(int index) {

  }
}