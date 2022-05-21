
class MoveRequest {
  String fen;

  MoveRequest(this.fen);
  Map<String, dynamic> toJson() => {
    'fen': fen
  };
}

