import 'dart:ffi';

abstract class BaseEntity {
  String? id;
  String? createUser;
  DateTime? createDate;
  Bool? isDisabled;
  fromJson(Map<String, dynamic> data) {
    id = data['id'];
    createUser = data['createUser'];
    createDate = data['createDate'];
    isDisabled = data['isDisabled'];
  }

  toMap(Map<String, dynamic> json) {
    json['id']= id;
    json['createUser']= createUser;
    json['createDate']= createDate;
    json['isDisabled']= isDisabled;
  }
}