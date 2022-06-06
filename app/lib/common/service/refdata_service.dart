import 'package:app/common/model/refdata.dart';
import 'package:app/common/service/http-client.dart';

class ReferenceDataService {
  static final ReferenceDataService _singleton = ReferenceDataService._internal();
  final HttpService _http = HttpService();
  factory ReferenceDataService() {
    return _singleton;
  }

  ReferenceDataService._internal();

  List<RefData> find(String type) async{
    return _http.get('url').
    final List<RefData> portasAbertasList =
    t.map((item) => PortasAbertas.fromJson(item)).toList();
    return portasAbertasList
  }
}