import 'dart:convert';

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
class CommandData extends MessagePayload{
  int command;
  Map<String, String>? data;

  CommandData(this.command, this.data);

  factory CommandData.fromJson(Map<String, dynamic> json) {
    return CommandData(json['command'], json['data']);
  }

  @override
  toJson() {
    return toMap({});
  }
  @override
  Map<String, dynamic> toMap(Map<String, dynamic> json) {
    json['command']= command;
    json['data']= data;
    return json;
  }
}

abstract class Message<T extends MessagePayload> {
  static const int messageTypeError=11;
  static const int messageTypeInfo=0;
  static const int messageTypeGameInitiate=1;
  static const int messageTypeGameStart=2;
  static const int messageTypeGameMoveRequest=3;
  static const int messageTypeGameMoveResponse=4;
  static const int messageTypeCommand=5;
  int type;
  T payload;
  Message(this.type, this.payload);

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
}

class ErrorMessage extends Message<InfoData>{
  ErrorMessage(String code, String message): super(Message.messageTypeError, InfoData(code, message));

  @override
  factory ErrorMessage.fromJson(Map<String, dynamic> json) {
    return ErrorMessage(json['code'],json['message']);
  }
}

class InfoMessage extends Message<InfoData>{
  InfoMessage(String code, String message): super(Message.messageTypeInfo, InfoData(code, message));

  factory InfoMessage.fromJson(Map<String, dynamic> json) {
    return InfoMessage(json['code'],json['message']);
  }
}

class GameInitiateMessage extends Message<GameInitiateData>{
  GameInitiateMessage(String? buddy, bool waitForBuddy): super(Message.messageTypeGameInitiate, GameInitiateData(buddy, waitForBuddy));

  factory GameInitiateMessage.fromJson(Map<String, dynamic> json) {
    return GameInitiateMessage(json['buddy'],json['waitForBuddy']);
  }
}
class GameStartMessage extends Message<GameStartData>{
  GameStartMessage(String firstMove): super(Message.messageTypeInfo, GameStartData(firstMove));

  factory GameStartMessage.fromJson(Map<String, dynamic> json) {
    return GameStartMessage(json['firstMove']);
  }
}
class CommandMessage extends Message<CommandData>{
  static const int commandPlayFirstAsk=1;
  static const int commandPlayFirstConfirm=2;
  CommandMessage(Map<String, dynamic> json): super(Message.messageTypeCommand, CommandData.fromJson(json));

  factory CommandMessage.fromJson(Map<String, dynamic> json) {
    return CommandMessage(json);
  }
}
class MessageParser {
  static Message parse(String jsonStr) {
    Map<String, dynamic> json = jsonDecode(jsonStr);
    int type = json['type'];
    Map<String, dynamic> payload = json['payload'];
    switch (type) {
      case Message.messageTypeError:
        return ErrorMessage.fromJson(payload);
      case Message.messageTypeInfo:
        return InfoMessage.fromJson(payload);
      case Message.messageTypeGameInitiate:
        return GameInitiateMessage.fromJson(payload);
      case Message.messageTypeGameStart:
        return GameStartMessage.fromJson(payload);
      case Message.messageTypeCommand:
        return CommandMessage.fromJson(payload);
      default:
        return InfoMessage.fromJson(payload);
    }
  }
}
