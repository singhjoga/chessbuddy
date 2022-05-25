
import 'package:app/common/exceptions/exceptions.dart';
import 'package:app/game/game-channel.dart';
import 'package:app/game/model/messages2.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';


void main() {
  test("Game protocol", () async {
    GameChannel white = GameChannel('ws://localhost:8080/game', 'white');
    GameChannel black = GameChannel('ws://localhost:8080/game', 'black');
    try {
      await white.connect();
      GameInitiateMessage msg = GameInitiateMessage(null, true);
      white.send(msg);
      await white.waitForOK();
      await black.connect();
      msg = GameInitiateMessage("white", false);
      black.send(msg);
      await black.waitForOK();
      await white.waitForCommand(CommandMessage.commandPlayFirstAsk);
    //  String data = await white.waitForData(1000);
    //  debugPrint('Data: $data');
      //   await white.send("TEST");
      //  String ok = await white.waitForData(5000);
    } on ClientException catch (e) {
      debugPrint('ERROR: ${e.message}');
    }
  });
}
