import 'dart:ffi';

import 'package:app/common/model/identifiable.dart';

abstract class BaseEntity extends Identifiable {
  String? createUser;
  DateTime? createDate;
  Bool? isDisabled;

  BaseEntity(Map<String, dynamic> json): super(json['id']) {
    createUser = json['createUser'];
    createDate = json['createDate'];
    isDisabled = json['isDisabled'];
  }
  @override
  toMap(Map<String, dynamic> json) {
    super.toMap(json);
    json['createUser']= createUser;
    json['createDate']= createDate;
    json['isDisabled']= isDisabled;
  }
}