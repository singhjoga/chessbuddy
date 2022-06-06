
import 'package:app/common/model/common_types.dart';
import 'package:app/common/model/identifiable.dart';

class RefData extends Identifiable{
  String? code;
  String? referenceType;
  String? value;
  String? description;
  bool? isDisabled;
  int? displayOrder;

  RefData();

  factory RefData.fromJson(JsonObject json){
    RefData result = RefData();
    result.fromJson(json);
    result.referenceType=json['referenceType'];
    result.value=json['value'];
    result.description=json['description'];
    result.isDisabled=json['isDisabled'];
    result.displayOrder=json['displayOrder'];
    return result;
  }
}