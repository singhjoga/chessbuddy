import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:app/common/config.dart';
import 'package:app/common/exceptions/exceptions.dart';
import 'package:app/common/model/errors.dart';
import 'package:http/http.dart' as http;
import 'package:http/testing.dart';

typedef JsonConverter = dynamic Function(dynamic);

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

  Future get(String url, [JsonConverter? jsonConverter]) async {
    return _call(Method.get, url, null,jsonConverter:jsonConverter);
  }
  Future getList(String url, [JsonConverter? jsonConverter]) async {
    return _call(Method.get, url, null,jsonConverter:jsonConverter, resultAsList: true);
  }
  Future<Map<String, dynamic>> post(String url, Object body) async {
    return _call(Method.post, url, body) as Map<String, dynamic>;
  }
  Future<Map<String, dynamic>> put(String url, Object body) async {
    return _call(Method.put, url, body) as Map<String, dynamic>;
  }
  Future<Map<String, dynamic>> patch(String url, Object body) async {
    return _call(Method.patch, url, body) as Map<String, dynamic>;
  }
  Future<Map<String, dynamic>> delete(String url) async {
    return _call(Method.delete, url, null) as Map<String, dynamic>;
  }
  Future _call(Method method, String url, Object? body, {bool? resultAsList, JsonConverter? jsonConverter}) async{
    String? jsonBody;
    if (body != null) {
      jsonBody = jsonEncode(body);
    }
    Map<String, String> headers = {
      'Content-Type': 'application/json; charset=UTF-8'
    };
    Completer completer = Completer();
    Future result;
    try {
      http.Response response;
      if (method == Method.post)  {
        result = client.post(Uri.parse(url),body: jsonBody, headers: headers);
      }else if (method == Method.put)  {
        result = client.put(Uri.parse(url),body: jsonBody, headers: headers);
      }else if (method == Method.patch)  {
        result = client.patch(Uri.parse(url),body: jsonBody, headers: headers);
      }else if (method == Method.delete)  {
        result = client.delete(Uri.parse(url));
      }else {
        result = client.get(Uri.parse(url));
      }
      result.then((response){
        if (response.statusCode == 200) {
            dynamic resp = jsonDecode(response.body);
            if (jsonConverter != null) {
              if (resultAsList != null && resultAsList) {
                List list = resp;
                resp = list.map((e) => jsonConverter(e)).toList();
              }else{
                resp = jsonConverter(resp);
              }
            }
            completer.complete(resp);
        } else {
            ErrorResponse resp = ErrorResponse.fromJson(jsonDecode(response.body));
            completer.completeError(ServerException(resp.code, resp.message, resp));
        }
      });
      return completer.future;
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