package net.chessbuddies.engine;
import org.junit.jupiter.api.Test;

public class EngineTest {
	@Test
	public void fenTest() {
		String startFEN ="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		Board b = new Board(startFEN);
		AI ai = new AI(3, 1, b.getTurnColour(), "AI", 3000);
		//System.out.println(ai.getMove(b).getGuiString());
	}
	@Test
	public void move1() {
		String startFEN ="rnbqkbnr/1ppppppp/p7/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1";
		Board b = new Board(startFEN);
		AI ai = new AI(3, 1, b.getTurnColour(), "AI", 3000);
		Move move = ai.getMove(b);
		System.out.println(move.getGuiString());
	}
	@Test
	public void move2() {
		String startFEN ="1nbqkbnr/rppppppp/p7/8/8/5N2/PPPPPPPP/RNBQKB1R b Kkq - 2 2";
		Board b = new Board(startFEN);
		AI ai = new AI(3, 1, b.getTurnColour(), "AI", 3000);
		Move move = ai.getMove(b);
		System.out.println(move.getGuiString());
	}	
	@Test
	public void move3() {
		String startFEN ="1nbqkbnr/r2pppp1/p6p/2p5/4P3/2N2N2/PPP2PPP/R1BQKB1R b Kkq - 0 6";
		Board b = new Board(startFEN);
		AI ai = new AI(3, 1, b.getTurnColour(), "AI", 3000);
		Move move = ai.getMove(b);
		System.out.println(move.getGuiString());
	}
	@Test
	public void move4() {
		String startFEN ="4k2N/1p4p1/nNP1p3/p6p/2B3n1/P7/P1PP1PPP/R1BQK2R b kq - 1 15";
		Board b = new Board(startFEN);
		AI ai = new AI(3, 1, b.getTurnColour(), "AI", 3000);
		Move move = ai.getMove(b);
		System.out.println(move.getGuiString());
	}	
	@Test
	public void promotion() {
		//String startFEN ="1n2k1n1/2P1pp1N/6p1/3B3p/4P3/2p5/PB1N1PPP/R5KR b - - 0 22";
		String startFEN ="4K1N1/2p1PP1n/6P1/3b3P/4p3/2P5/pb1n1ppp/R5kr b - - 0 22";
		Board b = new Board(startFEN);
		AI ai = new AI(3, 1, b.getTurnColour(), "AI", 3000);
		Move move = ai.getMove(b);
		System.out.println(move.getGuiString());
	}
	@Test
	public void checkmate() {
		//String startFEN ="1n2k1n1/2P1pp1N/6p1/3B3p/4P3/2p5/PB1N1PPP/R5KR b - - 0 22";
		String startFEN ="r1b1k1r1/pppp1ppp/n3p2P/P7/7q/P4PP1/2PPP3/1NBQKBNn b q - 0 10";
		Board b = new Board(startFEN);
		AI ai = new AI(3, 1, b.getTurnColour(), "AI", 3000);
		Move move = ai.getMove(b);
		
		System.out.println(move.getGuiString());
		if (b.isCheckmate()) {
			System.out.println("Checkmate !!");
		}
	}
}
