import 'dart:ffi';

import 'package:app/common/model/base-entity.dart';

class Work extends BaseEntity{
  String? name;
  String? description;
  @override
  fromJson(Map<String, dynamic> data) {
    name = data['name'];
    description = data['description'];
    super.fromJson(data);
  }
  Map<String, dynamic> toJson() {
    Map<String, dynamic> json= {
      'name': name,
      'description': description
    };
    super.toMap(json);
    return json;
  }
}