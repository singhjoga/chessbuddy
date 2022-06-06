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

  static final HttpService _singleton = HttpService._internal();

  factory HttpService() {
    return _singleton;
  }
  HttpService._internal() {
    if (config.isTesting) {
      client=MockClient(config.mockHandler);
    }else{
      client = HttpClient();
    }
  }

  Future<Map<String, dynamic>> get(String url) async {
    return _call(Method.get, url, null);
  }
  Future<Map<String, dynamic>> post(String url, Object body) async {
    return _call(Method.post, url, body);
  }
  Future<Map<String, dynamic>> put(String url, Object body) async {
    return _call(Method.put, url, body);
  }
  Future<Map<String, dynamic>> patch(String url, Object body) async {
    return _call(Method.patch, url, body);
  }
  Future<Map<String, dynamic>> delete(String url) async {
    return _call(Method.delete, url, null);
  }
  Future<Map<String, dynamic>> _call(Method method, String url, Object? body) async{
    String? jsonBody;
    if (body != null) {
      jsonBody = jsonEncode(body);
    }
    Map<String, String> headers = {
      'Content-Type': 'application/json; charset=UTF-8'
    };

    try {
      http.Response response;
      if (method == Method.post)  {
        response = await client.post(Uri.parse(url),body: jsonBody, headers: headers);
      }else if (method == Method.put)  {
        response = await client.put(Uri.parse(url),body: jsonBody, headers: headers);
      }else if (method == Method.patch)  {
        response = await client.patch(Uri.parse(url),body: jsonBody, headers: headers);
      }else if (method == Method.delete)  {
        response = await client.delete(Uri.parse(url));
      }else {
        response = await client.get(Uri.parse(url));
      }
      if (response.statusCode == 200) {
        return jsonDecode(response.body);
      } else {
        ErrorResponse resp = ErrorResponse.fromJson(jsonDecode(response.body));
        throw ServerException(resp.code, resp.message, resp);
      }
    } on SocketException catch (e){
      throw ClientException('', 'Socket exception: '+e.message);
    } on HttpException catch (e){
      throw ClientException('', 'Http exception: '+e.message);
    } on FormatException catch (e){
      throw ClientException('', 'Format exception: '+e.message);
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
enum Method {
  get,
  post,
  delete,
  put,
  patch
}