import 'package:squares/squares.dart' as sq;
import 'package:bishop/bishop.dart' as bp;
import 'package:equatable/equatable.dart';
import 'package:squares/squares.dart';

extension ToBishopSize on sq.BoardSize {
  /// Gets an equivalent Bishop BoardSize.
  bp.BoardSize toBishop() => bp.BoardSize(h, v);
}

extension ToSquaresSize on bp.BoardSize {
  /// Gets an equivalent Squares BoardSize.
  sq.BoardSize toSquares() => sq.BoardSize(h, v);
}

extension GameExtensions on bp.Game {
  /// Gets the current `PlayState`.
  PlayState get playState {
    if (gameOver) return PlayState.finished;
    return PlayState.playing;
  }

  /// Builds a Squares BoardState from the current state of the game.
  sq.BoardState boardState(int? orientation) {
    sq.BoardSize _size = squaresSize;
    return sq.BoardState(
      board: boardSymbols(),
      lastFrom: info.lastFrom != null ? _size.squareNumber(info.lastFrom!) : null,
      lastTo: info.lastTo != null ? _size.squareNumber(info.lastTo!) : null,
      checkSquare: info.checkSq != null ? _size.squareNumber(info.checkSq!) : null,
      player: turn,
      orientation: orientation,
    );
  }

  /// Builds a SquaresState from the current state of the game, from the perspective of [player].
  SquaresState squaresState(int player) {
    return SquaresState(
      state: playState,
      size: size.toSquares(),
      board: boardState(player),
      moves: squaresMoves(player),
      history: squaresHistory,
      hands: handSymbols(),
    );
  }

  sq.BoardSize get squaresSize => size.toSquares();

  /// Gets all the available moves (in Bishop format) for [player].
  /// If it's [player]'s turn, this will generate legal moves, and if not it will generate premoves.
  List<bp.Move> movesForPlayer(int player) => turn == player ? generateLegalMoves() : generatePremoves();

  /// Gets all the available moves (in Squares format) for [player].
  /// /// If it's [player]'s turn, this will generate legal moves, and if not it will generate premoves.
  List<sq.Move> squaresMoves(int player) => movesForPlayer(player).map((e) => squaresMove(e)).toList();

  /// Returns the move history in Squares move format.
  List<sq.Move> get squaresHistory => history.where((e) => e.move != null).map((e) => squaresMove(e.move!)).toList();

  /// Converts a Bishop move to a Squares move.
  sq.Move squaresMove(bp.Move move) {
    String alg = toAlgebraic(move);
    return squaresSize.moveFromAlgebraic(alg);
  }

  /// Converts a Squares move to a Bishop move.
  /// Can return null if the move is not valid.
  bp.Move? bishopMove(sq.Move move) {
    String alg = squaresSize.moveToAlgebraic(move);
    return getMove(alg);
  }

  /// Converts a Squares [move] to Bishop format and makes it.
  /// Returns false if the move is invalid.
  bool makeSquaresMove(sq.Move move) {
    bp.Move? m = bishopMove(move);
    if (m == null) return false;
    return makeMove(m);
  }
}


/// A state representation containing the common values that are needed to manage a Squares `BoardController`.
class SquaresState extends Equatable {
  /// The current state of play. Can be idle, playing or finished.
  final PlayState state;

  /// A representation of the board dimensions.
  final BoardSize size;

  /// The state of the pieces on the board.
  final BoardState board;

  /// A list of possible moves.
  /// These could be premoves depending on how this was generated.
  final List<Move> moves;

  /// The pieces in each player's hand, such as in variants like Crazyhouse.
  final List<List<String>> hands;

  /// Is there thinking happening? Set this yourself.
  final bool thinking;

  /// A history of moves played.
  final List<Move> history;

  /// Can [player] currently move?
  bool canMove(int player) => state == PlayState.playing && board.player == player;

  SquaresState({
    required this.state,
    required this.size,
    required this.board,
    required this.moves,
    this.hands = const [[], []],
    this.thinking = false,
    this.history = const [],
  });
  factory SquaresState.initial() =>
      SquaresState(state: PlayState.idle, size: BoardSize.standard(), board: BoardState.empty(), moves: []);

  /// Creates a copy of the state with the relevant values modified.
  SquaresState copyWith({
    PlayState? state,
    BoardSize? size,
    BoardState? board,
    List<Move>? moves,
    List<List<String>>? hands,
    bool? thinking,
    int? orientation,
    List<Move>? history,
  }) {
    return SquaresState(
      state: state ?? this.state,
      size: size ?? this.size,
      board: board ?? this.board,
      moves: moves ?? this.moves,
      hands: hands ?? this.hands,
      thinking: thinking ?? this.thinking,
      history: history ?? this.history,
    );
  }

  /// Returns a copy of the state, with [board.orientation] flipped.
  SquaresState flipped() => copyWith(board: board.flipped());

  List<Object> get props => [state, size, board, moves, hands, thinking];
  bool get stringify => true;
}

enum PlayState {
  idle,
  playing,
  finished,
}