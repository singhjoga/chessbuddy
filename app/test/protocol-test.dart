
import 'dart:io';

import 'package:app/common/exceptions/exceptions.dart';
import 'package:app/game/game-channel.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:web_socket_channel/web_socket_channel.dart';
import 'package:web_socket_channel/status.dart' as status;
void main() {
  test("Game protocol", () async{
    GameChannel channel = GameChannel('ws://localhost:8080/game', 'white');
    try {
      await channel.connect();
      GameI
     // String data = await channel.waitForData(2000);
     // debugPrint('DDD: $data');


   //   await channel.send("TEST");
    //  String ok = await channel.waitForData(5000);
    } on ClientException catch (e) {
      debugPrint('ERROR: ${e.message}');
    }
  });

}