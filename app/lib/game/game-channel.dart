
import 'dart:async';
import 'dart:convert';

import 'package:app/common/exceptions/exceptions.dart';
import 'package:app/game/model/messages.dart';
import 'package:flutter/material.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

class GameChannel {
  late WebSocketChannel? _wsChannel;
  late StreamController channel;
  String endpointUrl;
  String playerName;
  String? _lastMessage;

  GameChannel(this.endpointUrl, this.playerName);
  connect() async{
    try {
      String url ='$endpointUrl/$playerName';
      debugPrint('Connecting to: $url');
      _wsChannel = WebSocketChannel.connect(Uri.parse(url));
      channel = StreamController.broadcast();
      listen();
     // channel.addStream(_wsChannel!.stream);
      await waitForOK();
    }on WebSocketChannelException catch (e) {
      throw ClientException('', 'Socket exception: ${e.message}');
    }
  }
  listen(){
    _wsChannel!.stream.listen((data) {
      channel.add(data);
      debugPrint('$playerName received: $data');
      _lastMessage=data;
    });

 /*   await for (final data in channel.stream) {
      debugPrint('$playerName received: $data');
      return data;
    }
    return "";
    */
  }
  close() {
    _wsChannel!.sink.close();
  }
  sendReply(CommandMessage cmdMsg, Map<String, String> data) {
    sendCommandResponse(cmdMsg.payload.command, data);
  }
  sendCommandResponse(int command, Map<String, String> data) {
    Map<String, dynamic> cmdData = {'command':command, 'data': data};
    CommandResponseMessage respMsg = CommandResponseMessage(cmdData);
    send(respMsg);
  }
  sendCommand(int command, Map<String, String> data) {
    Map<String, dynamic> cmdData = {'command':command, 'data': data};
    CommandMessage respMsg = CommandMessage(cmdData);
    send(respMsg);
  }
  send(Message msg){
    String json = jsonEncode(msg.toJson());
    _wsChannel!.sink.add(json);
    debugPrint('$playerName message sent ${msg.type}');
  }

  waitForOK() async {
    await waitForInfoMessage('OK');
    debugPrint('$playerName received OK');
  }
  Future<CommandMessage> waitForCommand(int? command) async {
    CommandMessage msg = await waitForMessageType(Message.messageTypeCommand) as CommandMessage;
    if (command != null && msg.payload.command != command) {
      throw ClientException('', '$playerName Invalid InfoMessage received. Expected command: $command, Actual command: ${msg.payload.command}');
    }

    return msg;
  }
  Future<InfoMessage> waitForInfoMessage(String? code) async {
    InfoMessage msg = await waitForMessageType(Message.messageTypeInfo) as InfoMessage;
    if (code != null && msg.payload.code != code) {
      throw ClientException('', '$playerName Invalid InfoMessage received. Expected code: $code, Actual code: ${msg.payload.code}');
    }

    return msg;
  }
  Future<ErrorMessage> waitForErrorMessage(String? code) async {
    ErrorMessage msg = await waitForMessageType(Message.messageTypeError) as ErrorMessage;
    if (code != null && msg.payload.code != code) {
      throw ClientException('', '$playerName Invalid InfoMessage received. Expected code: $code, Actual code: ${msg.payload.code}');
    }

    return msg;
  }
  Future<Message> waitForMessageType(int type) async {
    Message msg = await waitForMessage();
    if (msg.type != type) {
      debugPrint(msg.toJson().toString());
      throw ClientException('', '$playerName Invalid message received. Expected type: $type, Actual type: ${msg.type}');
    }
    return msg;
  }
  Future<Message> waitForMessage() async {
    String data = await waitForData(2000);
    Message resp = MessageParser.parse(data);

    return resp;
  }
  Future<String> waitForData(int timeoutMs) async {
    try {
      if (_lastMessage != null) {
        // message received when the channel was not listened
        return _lastMessage!;
      }
      final stream = channel.stream.timeout(Duration(milliseconds: timeoutMs));
      String data = await stream.first;
      return data;
    } on TimeoutException catch (e) {
      throw ClientException('code', '$playerName: ${e.message}');
    } finally {
      _lastMessage=null;
    }
  }
  Future<String> getNext() {
    return getNextFrom(channel.stream);
  }
  Future<String> getNextFrom(Stream stream) {
    Completer<String> completer = Completer();
    StreamSubscription subscription = stream.listen(null,  cancelOnError: true);
    subscription.onData((dynamic value) {
      debugPrint('Received: $value');
      subscription.cancel(). then((event)  {
        debugPrint('Subscription cancelled');
        completer.complete(value);
      });

    });
    return completer.future;
  }
}