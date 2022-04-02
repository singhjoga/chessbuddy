import 'dart:convert';
import 'dart:ffi';
import 'dart:io';

import 'package:app/common/config.dart';
import 'package:app/common/exceptions/exceptions.dart';
import 'package:app/common/model/errors.dart';
import 'package:http/http.dart' as http;
import 'package:http/testing.dart';

class HttpService {
  late http.BaseClient client;
  CommonConfig config = CommonConfig.getInstance();
  HttpService() {
    if (config.isTesting) {
      client=MockClient(config.mockHandler);
    }else{
      client = HttpClient();
    }
  }
  Future<Map<String, dynamic>> get(String url) async {

    try {
      final response = await client.get(Uri.parse(url));
      if (response.statusCode == 200) {
        return jsonDecode(response.body);
      } else {
        ErrorResponse resp = ErrorResponse.fromJson(jsonDecode(response.body));
        throw ServerException(resp.code, resp.message, resp);
      }
    } on SocketException {
      throw ClientException('', 'Socket exception');
    } on HttpException {
      throw ClientException('', 'Http exception');
    } on FormatException {
      throw ClientException('', 'Format exception');
    }
  }
}

class HttpClient extends http.BaseClient {
  final http.Client _inner = http.Client();
  HttpClient._internal();
  static final _instance = HttpClient._internal();
  factory HttpClient() => _instance;
  @override
  Future<http.StreamedResponse> send(http.BaseRequest request) {
    return _inner.send(request);
  }

  @override
  void close() {
    _inner.close();
    super.close();
  }
}