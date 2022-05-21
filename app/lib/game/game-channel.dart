
import 'dart:async';
import 'dart:convert';

import 'package:app/common/exceptions/exceptions.dart';
import 'package:app/game/model/messages.dart';
import 'package:flutter/material.dart';
import 'package:web_socket_channel/io.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

class GameChannel {
  WebSocketChannel? channel;
  String endpointUrl;
  String playerName;

  GameChannel(this.endpointUrl, this.playerName);
  connect() async{
    try {
      String url ='$endpointUrl/$playerName';
      debugPrint('Connecting to: $url');
      channel = WebSocketChannel.connect(Uri.parse(url));
      await waitForOK();
     // channel!.sink.add('OK');
     // String ok = await waitForData(1000);
     // if (ok != 'OK') {
     //   throw ClientException('', 'Connection response: $ok');
     // }
    }on WebSocketChannelException catch (e) {
      throw ClientException('', 'Socket exception: ${e.message}');
    }
  }
  Future<String> listen() async{
    await for (final data in channel!.stream) {
      debugPrint('Received: $data');
      return data;
    }
    return "";
 /*   channel!.stream.listen((data) {
      debugPrint('Received: $data');
    },
        onError: (e) {
          debugPrint('Error: $e');
        });

  */
  }
  send(String data){
    channel!.sink.add(data);
  }
  waitForOK() async {
    String data = await waitForData(2000);
    Message resp = MessageParser.parse(data);
    if (resp is InfoMessage) {
      if (resp.data.code=='OK') {
        debugPrint('OK received');
        return;
      }
    }
    throw ClientException('', 'OK message expected, but received: $data');
  }
  Future<String> waitForData(int timeoutMs) async {
    final stream = channel!.stream.timeout(Duration(milliseconds: timeoutMs));
    try {
     await for (final data in stream) {
        debugPrint('Received: $data');
        return data;
      }

    } on TimeoutException catch (e) {
      throw ClientException('code', e.message);
    }
    return "";
  }
}