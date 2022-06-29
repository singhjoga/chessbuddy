import 'package:http/testing.dart';

class CommonConfig {
  bool isTesting=false;
  String apiBaseUrl="http://localhost:8080";
  late MockClientHandler mockHandler;
  CommonConfig._internal();
  static final _instance = CommonConfig._internal();
  factory CommonConfig() => _instance;
  static CommonConfig  getInstance() {
    return _instance;
  }
}