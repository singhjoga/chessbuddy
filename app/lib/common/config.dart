import 'package:http/testing.dart';

class CommonConfig {
  bool isTesting=false;
  late MockClientHandler mockHandler;
  CommonConfig._internal();
  static final _instance = CommonConfig._internal();
  factory CommonConfig() => _instance;
  static CommonConfig  getInstance() {
    return _instance;
  }
}