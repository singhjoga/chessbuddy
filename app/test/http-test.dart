

import 'package:app/common/model/common_types.dart';
import 'package:app/common/service/http_client.dart';
import 'package:flutter/material.dart';
import 'package:test/test.dart';

void main() {
  test("Game protocol", () async {
    dynamic origConverter(dynamic item) => Orig.fromJson(item);
    dynamic catConverter(dynamic item) => CuteCat.fromJson(item);

    HttpService http = HttpService();
    dynamic resp = await http.get('https://ipinfo.io/161.185.160.93/geo',origConverter);
    debugPrint(resp.toString());

    resp = await http.getList('https://cataas.com/api/cats?tags=cute',catConverter);
    debugPrint(resp.toString());
  });
}
class Orig {
  String? ip;
  String? city;
  String? region;
  String? country;
  String? loc;
  String? org;
  String? postal;
  String? timezone;
  String? readme;

  Orig();

  factory Orig.fromJson(JsonObject json){
    Orig result = Orig();
    result.ip=json['ip'];
    result.city=json['city'];
    result.region=json['region'];
    result.country=json['country'];
    result.loc=json['loc'];
    result.org=json['org'];
    result.postal=json['postal'];
    result.timezone=json['timezone'];
    result.readme=json['readme'];
    return result;
  }

  @override
  String toString() {
    return 'Orig{ip: $ip, city: $city, region: $region, country: $country, loc: $loc, org: $org, postal: $postal, timezone: $timezone, readme: $readme}';
  }
}
class CuteCat {
  String? id;
  DateTime? created_at;
  List<dynamic>? tags;

  CuteCat();

  factory CuteCat.fromJson(JsonObject json){
    CuteCat result = CuteCat();
    result.id = json['id'];
    result.created_at = DateTime.parse(json['created_at']);
    result.tags = json['tags'];

    return result;
  }

  @override
  String toString() {
    return 'CuteCat{id: $id, created_at: $created_at, tags: $tags}';
  }
}