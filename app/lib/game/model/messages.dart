import 'dart:convert';

import 'package:flutter/material.dart';

abstract class MessagePayload {
  toJson();
  Map<String, dynamic> toMap(Map<String, dynamic> json);
}
class InfoData extends MessagePayload{
  String code;
  String message;

  InfoData(this.code, this.message);
  factory InfoData.fromJson(Map<String, dynamic> json) {
    return InfoData(json['code'], json['message']);
  }
  @override
  toJson() {
    return toMap({});
  }
  @override
  Map<String, dynamic> toMap(Map<String, dynamic> json) {
    json['code']= code;
    json['message']= message;

    return json;
  }
}

class GameInitiateData extends MessagePayload{
  String? buddy;
  bool waitForBuddy;
  GameInitiateData([this.buddy, this.waitForBuddy=false]);

  factory GameInitiateData.fromJson(Map<String, dynamic> json) {
    return GameInitiateData(json['buddy'], json['waitForBuddy']);
  }
  @override
  toJson() {
    return toMap({});
  }
  @override
  Map<String, dynamic> toMap(Map<String, dynamic> json) {
    json['buddy']= buddy;
    json['waitForBuddy']= waitForBuddy;
    return json;
  }
}

class GameStartData extends MessagePayload{
  String firstMove;

  GameStartData(this.firstMove);

  factory GameStartData.fromJson(Map<String, dynamic> json) {
    return GameStartData(json['firstMove']);
  }

  @override
  toJson() {
    return toMap({});
  }
  @override
  Map<String, dynamic> toMap(Map<String, dynamic> json) {
    json['firstMove']= firstMove;
    return json;
  }
}

class Message {
  static const int messageTypeError=11;
  static const int messageTypeInfo=0;
  static const int messageTypeGameInitiate=1;
  static const int messageTypeGameStart=2;
  static const int messageTypeGameMoveRequest=3;
  static const int messageTypeGameMoveResponse=4;
  int type;
  MessagePayload payload;
  Message(this.type, this.payload);

  factory Message.fromJson(Map<String, dynamic> json) {
    int type = json['type'];
    Map<String, dynamic> payloadJson = json['payload'];
    MessagePayload payload;
    switch (type) {
      case Message.messageTypeError:
      case Message.messageTypeInfo:
        payload = InfoData.fromJson(payloadJson);
        break;
      case Message.messageTypeGameInitiate:
        payload = GameInitiateData.fromJson(payloadJson);
        break;
      case Message.messageTypeGameStart:
        payload = GameStartData.fromJson(payloadJson);
        break;
      default:
        payload = InfoData.fromJson(payloadJson);
    }
    return Message(type, payload);
  }
  toJson() {
    return toMap({});
  }
  Map<String, dynamic> toMap(Map<String, dynamic> json) {
    json['type']= type;
    json['payload']= payload.toJson();
    return json;
  }
  bool isInfoMessage() {
    return type == messageTypeInfo;
  }
  bool isErrorMessage() {
    return type == messageTypeError;
  }

  static Message parse(String jsonStr) {
    Map<String, dynamic> json = jsonDecode(jsonStr);
    return Message.fromJson(json);
  }
}
abstract class MessageOld<T> {
  static const int messageTypeError=11;
  static const int messageTypeInfo=0;
  static const int messageTypeGameInitiate=1;
  static const int messageTypeGameStart=2;
  static const int messageTypeGameMoveRequest=3;
  static const int messageTypeGameMoveResponse=4;
  int type;

  MessageOld(this.type);

  T getData();

  fromJson(Map<String, dynamic> json) {
    type = json['type'];
  }

  Map<String, dynamic> toMap(Map<String, dynamic> json) {
    json['type']= type;
    return json;
  }
}

class ErrorMessage extends MessageOld<InfoData>{
  InfoData data;
  ErrorMessage(String code, String message): data =InfoData(code, message), super(Message.messageTypeError);

  @override
  InfoData getData() {
    return data;
  }
  @override
  factory ErrorMessage.fromJson(Map<String, dynamic> json) {
    Map<String, dynamic> dataJson = json['data'];
    return ErrorMessage(dataJson['code'],dataJson['message']);
  }
  toJson() {
    return toMap({});
  }
  @override
  Map<String, dynamic> toMap(Map<String, dynamic> json) {
    super.toMap(json);
    json['data']= data.toMap(json);
    return json;
  }
}

class InfoMessage extends MessageOld<InfoData>{
  InfoData data;

  InfoMessage(String code, String message): data =InfoData(code, message), super(Message.messageTypeInfo);

  @override
  InfoData getData() {
    return data;
  }
  factory InfoMessage.fromJson(Map<String, dynamic> json) {
    Map<String, dynamic> dataJson = json['data'];
    return InfoMessage(dataJson['code'],dataJson['message']);
  }

  toJson() {
    return toMap({});
  }
  @override
  Map<String, dynamic> toMap(Map<String, dynamic> json) {
    super.toMap(json);
    json['data']= data.toMap(json);
    return json;
  }
}
