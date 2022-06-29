import 'package:app/common/config.dart';
import 'package:app/common/model/refdata.dart';
import 'package:app/common/service/http_client.dart';

class ReferenceDataService {
  static final ReferenceDataService _singleton = ReferenceDataService._internal();
  final HttpService _http = HttpService();
  final CommonConfig _config = CommonConfig();
  final String apiUrl='/api/v1/system';
  factory ReferenceDataService() {
    return _singleton;
  }

  ReferenceDataService._internal();

  Future<List<RefData>> find(String type) async{
    return _http.getList('url', (item) {
      return RefData.fromJson(item);
    }) as List<RefData>;
    /*
    then((resp) => resp.map((item) => RefData.fromJson(item)).toList());
   List<dynamic> resp = await _http.getList('url');
    final List<RefData> result =resp.map((item) => RefData.fromJson(item)).toList();
    return result;
    */

  }
}