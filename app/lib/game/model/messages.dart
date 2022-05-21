import 'dart:convert';

import 'package:flutter/material.dart';

class CodedData {
  String code;
  String message;

  CodedData(this.code, this.message);
  factory CodedData.fromJson(Map<String, dynamic> json) {
    return CodedData(json['code'], json['message']);
  }
  toJson() {
    return toMap({});
  }
  Map<String, dynamic> toMap(Map<String, dynamic> json) {
    json['code']= code;
    json['message']= message;

    return json;
  }
}

class GameInitiateData {
  String? buddy;
  bool waitForBuddy;
  GameInitiateData([this.buddy, this.waitForBuddy=false]);

  factory GameInitiateData.fromJson(Map<String, dynamic> json) {
    return GameInitiateData(json['buddy'], json['waitForBuddy']);
  }
  toJson() {
    return toMap({});
  }
  Map<String, dynamic> toMap(Map<String, dynamic> json) {
    json['buddy']= buddy;
    json['waitForBuddy']= waitForBuddy;
    return json;
  }
}

class GameStartData {
  String firstMove;

  GameStartData(this.firstMove);

  factory GameStartData.fromJson(Map<String, dynamic> json) {
    return GameStartData(json['firstMove']);
  }

  toJson() {
    return toMap({});
  }
  Map<String, dynamic> toMap(Map<String, dynamic> json) {
    json['firstMove']= firstMove;
    return json;
  }
}

abstract class Message<T> {
  static const int messageTypeError=11;
  static const int messageTypeInfo=0;
  static const int messageTypeGameInitiate=1;
  static const int messageTypeGameStart=2;
  static const int messageTypeGameMoveRequest=3;
  static const int messageTypeGameMoveResponse=4;
  int type;

  Message(this.type);

  T getData();

  fromJson(Map<String, dynamic> json) {
    type = json['type'];
  }

  Map<String, dynamic> toMap(Map<String, dynamic> json) {
    json['type']= type;
    return json;
  }
}

class ErrorMessage extends Message<CodedData>{
  CodedData data;
  ErrorMessage(String code, String message): data =CodedData(code, message), super(Message.messageTypeError);

  @override
  CodedData getData() {
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

class InfoMessage extends Message<CodedData>{
  CodedData data;

  InfoMessage(String code, String message): data =CodedData(code, message), super(Message.messageTypeInfo);

  @override
  CodedData getData() {
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

class MessageParser {
  static Message parse(String jsonStr) {
    Map<String, dynamic> json = jsonDecode(jsonStr);
    int type = json['type'];
    switch (type) {
      case Message.messageTypeError:
        return ErrorMessage.fromJson(json);
      default:
        return InfoMessage.fromJson(json);
    }
  }
}