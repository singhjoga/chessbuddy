
import 'package:app/common/exceptions/exceptions.dart';
import 'package:app/game/game-channel.dart';
import 'package:app/game/model/messages.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';


void main() {
  test("Game protocol", () async {
    GameChannel white = GameChannel('ws://localhost:8080/game', 'white');
    GameChannel black = GameChannel('ws://localhost:8080/game', 'black');
    try {
      await white.connect();
      //white starts play and waits for buddy to join
      GameInitiateMessage msg = GameInitiateMessage(null, true);
      white.send(msg);
      await white.waitForOK();
      //black connects and initiates game to play with white
      await black.connect();
      msg = GameInitiateMessage("white", false);
      black.send(msg);
      await black.waitForOK();
      // since white started first, he is asked to select the player who will play first. White says that he will play first.
      CommandMessage cmdMsg = await white.waitForCommand(CommandMessage.commandPlayFirstAsk);
      white.sendReply(cmdMsg, {'playFirst': 'white'});
      await white.waitForOK();

      // black need to confirm the first play decision by white
      cmdMsg = await black.waitForCommand(CommandMessage.commandPlayFirstConfirm);
      // black rejects the proposal from white to play first
      black.sendReply(cmdMsg, {'response': 'reject'});
      await black.waitForOK();

      //since black rejected the proposal, white would get the new proposal
      cmdMsg = await white.waitForCommand(CommandMessage.commandPlayFirstConfirm);
      // white accepts it
      white.sendReply(cmdMsg, {'response': 'accept'});
      await white.waitForOK();

  //    debugPrint(cmdMsg.toJson().toString());

    } on ClientException catch (e) {
      debugPrint('ERROR: ${e.message}');
    }
  });
}
