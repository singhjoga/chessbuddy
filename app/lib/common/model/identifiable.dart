import 'package:app/common/model/common_types.dart';

abstract class Identifiable {
  String? id;

  Identifiable([this.id]);

  fromJson(JsonObject json) {
    id = json['id'];
  }
  Map<String, dynamic> toJson() {
    return toMap({});
  }
  toMap(Map<String, dynamic> json) {
    json['id']= id;
    return json;
  }
}