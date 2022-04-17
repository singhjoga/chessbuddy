import 'package:app/common/model/errors.dart';

class AppException implements Exception {
  String? code;
  String? message;

  AppException(this.code, this.message);
}
class ClientException extends AppException {
  ClientException(String? code, String? message) : super(code, message);
}
class ServerException extends AppException {
  ErrorResponse response;
  ServerException(String? code, String? message,this.response) : super(code, message);

}
