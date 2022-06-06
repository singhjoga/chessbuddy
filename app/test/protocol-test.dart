
import 'package:app/common/exceptions/exceptions.dart';
import 'package:app/game/game-channel.dart';
import 'package:app/game/game-state.dart';
import 'package:app/game/model/messages.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';


void main() {
  test("Game protocol", () async {
    GameChannel anand = GameChannel('ws://localhost:8080/game', 'anand');
    GameChannel carlos = GameChannel('ws://localhost:8080/game', 'carlos');
    try {
      await anand.connect();
      //anand starts play and waits for buddy to join
      GameInitiateMessage msg = GameInitiateMessage(null, true);
      anand.send(msg);
      await anand.waitForOK();
      //carlos connects and initiates game to play with anand
      await carlos.connect();
      msg = GameInitiateMessage("anand", false);
      carlos.send(msg);
      await carlos.waitForOK();
      // since anand started first, he is asked to select the player who will play first. anand says that he will play first.
      CommandMessage cmdMsg = await anand.waitForCommand(CommandMessage.commandPlayFirstAsk);
      anand.sendReply(cmdMsg, {'playFirst': 'anand'});
      await anand.waitForOK();

      // carlos need to confirm the first play decision by anand
      cmdMsg = await carlos.waitForCommand(CommandMessage.commandPlayFirstConfirm);
      // carlos rejects the proposal from anand to play first
      carlos.sendReply(cmdMsg, {'response': 'reject'});
      await carlos.waitForOK();

      //since carlos rejected the proposal, anand would get the new proposal where carlos will play first
      cmdMsg = await anand.waitForCommand(CommandMessage.commandPlayFirstConfirm);
      // anand accepts it
      anand.sendReply(cmdMsg, {'response': 'accept'});
      await anand.waitForOK();

      //both players will get the initial FEN for game initialization and color allocation. Here carlos will be assigned white color since he is playing first
      cmdMsg = await anand.waitForCommand(CommandMessage.commandPlayStart);
      String anandStartFen = cmdMsg.payload.data!['initialFen'];
      String anandColor = cmdMsg.payload.data!['color'];
      expect(anandColor,'black');
      cmdMsg = await carlos.waitForCommand(CommandMessage.commandPlayStart);
      String carlosStartFen = cmdMsg.payload.data!['initialFen'];
      String carlosColor = cmdMsg.payload.data!['color'];
      expect(carlosColor,'white');
      GameState anandGame = GameState(anandStartFen, anandColor);
      GameState carlosGame = GameState(carlosStartFen, carlosColor);

      // carlos makes first move, which will make anand to check mate
      bool ok = carlosGame.makeMoveFromTo("f2", "f3");
      expect(ok,true);
      //send the move message to server, so that anand can move his move.
      carlos.sendCommand(CommandMessage.commandMakeMove, {'move':'f2-f3'});
      await carlos.waitForOK();

      // anand will receive the same command which carlos sent, to update its board
      cmdMsg = await anand.waitForCommand(CommandMessage.commandMakeMove);
      String move = cmdMsg.payload.data!['move'];
      ok = anandGame.makeMoveFromMoveString(move);
      expect(ok,true);

      // now it is anand's turn to make move. Current FEN was setup to do checkmate with the below move.
      ok = anandGame.makeMoveFromTo("h4", "g3");
      expect(ok,true);
      // anand has check mated. verify it
      expect(ok,anandGame.hasWonMe());
      // send the move to carlos to update his board
      anand.sendCommand(CommandMessage.commandMakeMove, {'move':'h4-g3'});
      await anand.waitForOK();
      //carlos will receive the message and update the game board
      cmdMsg = await carlos.waitForCommand(CommandMessage.commandMakeMove);
      move = cmdMsg.payload.data!['move'];
      ok = carlosGame.makeMoveFromMoveString(move);
      expect(ok,true);
      //carlos has lost
      expect(ok,carlosGame.hasLostMe());
      
      //close channels
      anand.close();
      carlos.close();
    } on ClientException catch (e) {
      debugPrint('ERROR: ${e.message}');
    }
  });
}
