import 'dart:io';

class ErrorDetail {
  String? code;
  String? message;

  ErrorDetail(this.code, this.message);
  ErrorDetail.fromJson(Map<String, dynamic> data):
    code = data['code'],
    message = data['message'];

}
class ValidationError extends ErrorDetail {
  String field;

  ValidationError(this.field, String? code, String? message): super(code,message);

  @override
  ValidationError.fromJson(Map<String, dynamic> data):
    field = data['field'],
    super.fromJson(data);
}

class ErrorResponse extends ErrorDetail {
  ErrorResponse(String? code, String? message) : super(code, message);
  List<ValidationError>? errors;
  factory ErrorResponse.fromJson(Map<String, dynamic> data){
    ErrorResponse obj = ErrorResponse(data['code'], data['message']);
    if (data.containsKey('errors')) {
      List<Map<String, dynamic>> errorsJson = data['errors'];
      var errors = <ValidationError>[];
      for (Map<String, dynamic> json in errorsJson) {
        errors.add(ValidationError.fromJson(json));
      }
      obj.errors=errors;
    }
    return obj;
  }



}