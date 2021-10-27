package Models;
import java.util.Arrays;
/**
 * @author Rohan Joshi
 * @author Nicholas Cheniara
 */

public class Board {

	/**
	 * boardmat is the 2D array that holds the pieces and simulates the board
	 */
	public Piece[][] boardmat;
	/**
	 * @deprecated
	 */
	public boolean draw;
	/**
	 * boolean that is true if white is in check false otherwise
	 */
	public boolean checkWhite;
	/**
	 * boolean that is true if black is in check false otherwise
	 */
	public boolean checkBlack;
	/**
	 * boolean that is true if it is whites turn, false if blacks turn
	 */
	public boolean whiteTurn;
	/**
	 * @deprecated
	 */
	public boolean ResignBlack;
	/**
	 * @deprecated
	 */
	public boolean ResignWhite;
	/**
	 * @deprecated
	 */
	public boolean Promotion;
	/**
	 * @deprecated
	 */
	public boolean CastledWhite;
	/**
	 * Is ended up being used as a utility for either team castling,
	 * is set to true in order to allow the castle to not be marked
	 * as illegal.
	 */
	public boolean CastledBlack;
	/**
	 * Boolean value that checks if enpassant is allowed in that turn
	 */
	public boolean enpassantactive;
	/**
	 * Position that holds the position at which the pawn that moved too spaces stopped at
	 */
	public Position enpassantpos;

	/**
	 * Default constructor intializes all values and puts the chess pieces in their current place on the board
	 * All other values should still be null
	 * Booleans all get intialized as necessary
	 */
	public Board() {
		boardmat = new Piece[8][8];
		boardmat[0][0] = new Rook(false,false,false);
		boardmat[0][1] = new Knight(false,false,false);
		boardmat[0][2] = new Bishop(false,false,false);
		boardmat[0][3] = new Queen(false,false,false);
		boardmat[0][4] = new King(false,false,false);
		boardmat[0][5] = new Bishop(false,false,false);
		boardmat[0][6] = new Knight(false,false,false);
		boardmat[0][7] = new Rook(false,false,false);
		for(int i=0;i<8;i++){
			boardmat[1][i] = new Pawn(false,false,false);
		}
		boardmat[7][0] = new Rook(false,false,true);
		boardmat[7][1] = new Knight(false,false,true);
		boardmat[7][2] = new Bishop(false,false,true);
		boardmat[7][3] = new Queen(false,false,true);
		boardmat[7][4] = new King(false,false,true);
		boardmat[7][5] = new Bishop(false,false,true);
		boardmat[7][6] = new Knight(false,false,true);
		boardmat[7][7] = new Rook(false,false,true);
		for(int i=0;i<8;i++){
			boardmat[6][i] = new Pawn(false,false,true);
		}
		draw=false;
		checkWhite=false;
		checkBlack = false;
		whiteTurn = true;
		ResignBlack = false;
		ResignWhite = false;
		Promotion = false;
		CastledBlack = false;
		CastledWhite = false;
		enpassantactive = false;
		enpassantpos = null;

	}
	
	
	
	/** 
	 * Method created to convert from the user input
	 * to a Position instance which we can use for 
	 * other methods etc.
	 * 
	 * @param x	input such as a,b,c,e etc which contains the 
	 * 			x value of the pieces position
	 * @param y input such as 1,2,3,4 etc which contains the 
	 * 			y value of the pieces position
	 * 
	 * @return Position		the position of the user inputted string
	 */
	public static Position ctop(char x,char y) {
		char[] letters = {'a','b','c','d','e','f','g','h'};
		char[] numbers = {0,1,2,3,4,5,6,7};
		
		for(int i=0;i<letters.length;i++) {
			if(letters[i]==(x)) {
				int ypos = 8-Character.getNumericValue(y);
				return new Position(ypos,numbers[i]);
			}
		}
		return null;
	}
	
	/** 
	 * Simple method to convert from
	 * int to char
	 * 
	 * @param i	the int i to be converted
	 * @return char		the char holds the converted int
	 */
	public static char intToChar(int i){
		return (char) (i+97);
	}

	/**
	 * Utility method to print the board in
	 * the format specified in the assignment description
	 */
	public void drawBoard() {
		System.out.println();

		String[][] newboard = new String[9][9];

		for (int i = 0; i < 8; i++){
			newboard[i][8] = Integer.toString(8 - i);
		}
		for (int i = 0; i < 8; i++){
			newboard[8][i] = " "+Character.toString(Board.intToChar(i));
		}
		newboard[8][8] = "  ";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardmat[i][j] != null) {
					newboard[i][j] = boardmat[i][j].toString();
				} 
				else {
					if ((i % 2 == 1 && j % 2 == 0) || (i % 2 == 0 && j % 2 == 1)) {
						newboard[i][j] = "##";
					} 
					else {
						newboard[i][j] = "  ";
					}
				}
			}
		}

		for (int i = 0; i < 9; i++) {
			String tempL = "";
			for (int j = 0; j < 9; j++) {
				if (j != 9 || j != 0){
					tempL+=" ";
				}
				tempL+=newboard[i][j];
			}
			System.out.println(tempL);
		}

	}

	
	/** 
	 * Utility method that takes in an x and y coordinate,
	 * and returns the Piece sitting at the coordinate.
	 * In this case, x and y refers to the 2 indices
	 * of the matrix boardmat declared in this class
	 * 
	 * @param x	contains the first index of the piece to return
	 * @param y	contains the second index of the piece to return
	 * @return Piece	the Piece that is requested by the user from the x,y coords
	 */
	public Piece getXY(int x, int y){
		return boardmat[x][y];
	}
	
	/** 
	 * Utility method to set the 
	 * piece at a certain position to the
	 * piece speficied by the parameter n
	 * x and y here again refer to the 2 indices
	 * of matrix holding the pieces, boardmat
	 * 
	 * @param x	contains the first index of the piece to set
	 * @param y contains the second index of the piece to set
	 * @param n	contains the piece meant to be set at the specified x,y location
	 */
	public void setXY(int x, int y,Piece n){
		boardmat[x][y] = n;
	}


	
	/** 
	 * Method to detect if black is in check,
	 * does so by finding the location of the black king first. 
	 * Then, if there is any white piece who has a legal move to
	 * the kings position, it means the king is in check.
	 * 
	 * @param a	The board in which to check if there is a black check
	 * @return boolean		True if the black king is in check, false if not.
	 */
	public static boolean detectblackcheck(Board a){
		Position kingpos = new Position(0,0);
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(a.getXY(i, j)==null){
					continue;
				}
				if(a.getXY(i, j).toString().equals("bK")){
					kingpos = new Position(i,j);
				}
			}
		}
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				Piece cur = a.getXY(i, j);
				if(cur==null || !cur.isWhite){
					continue;
				}
				if(cur.isWhite){
					Position curpos = new Position(i,j);
					if(cur.legalMoves(curpos).containsKey(kingpos.toString())){
						if(cur.isLegalMove(curpos, kingpos, a)){
							return true;
						}
					}
				}
			}
		}
		

		return false;
	}

	
	/** 
	 * Method to detect if white is in check,
	 * does so by finding the location of the white king first. 
	 * Then, if there is any black piece who has a legal move to
	 * the kings position, it means the king is in check.
	 * 
	 * @param a	The board in which to check if there is a white check
	 * @return boolean		True if the white king is in check, false if not.
	 */
	public static boolean detectwhitecheck(Board a){
		Position kingpos = new Position(0,0);
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(a.getXY(i, j)==null){
					continue;
				}
				if(a.getXY(i, j).toString().equals("wK")){
					kingpos = new Position(i,j);
				}
			}
		}
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				Piece cur = a.getXY(i, j);
				if(cur==null || cur.isWhite){
					continue;
				}
				if(!cur.isWhite){
					Position curpos = new Position(i,j);
					if(cur.legalMoves(curpos).containsKey(kingpos.toString())){
						if(cur.isLegalMove(curpos, kingpos, a)){
							return true;
						}
					}
				}
			}
		}
		

		return false;
	}

	
	/** 
	 * Method to detect if white is in checkmate.
	 * Does so by taking each white piece, and simulating 
	 * every possible legal move. If the simulated move 
	 * shows that there is no check, it means there is a possible move to be
	 * made and so it is not checkmate. If it never finds such a move, it will
	 * return true, meaning checkmate.
	 * 
	 * @param a	The board in which to check if there is a white checkmate
	 * @return boolean		True if the white king is in checkmate, false if not.
	 */
	public static boolean detectCheckmatewhite(Board a){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				Piece cur = a.getXY(i, j);
				Position curpos = new Position(i,j);
				if(cur != null){
					if(cur.isWhite){
						for(int n=0;n<8;n++){
							for(int m=0;m<8;m++){
								Position finpos = new Position(n,m);
								if(cur.isLegalMove(curpos,finpos,a)){
									if(Board.simulateMove(curpos, finpos, a).equals("white")){
										continue;
									}
									if(Board.simulateMove(curpos, finpos, a).equals("none")){
										return false;
									}
								}
							}
						}
					}
				}
		
				
			}
		}
		return true;
	}

	
	/** 
	 * Method to detect if black is in checkmate.
	 * Does so by taking each black piece, and simulating 
	 * every possible legal move. If the simulated move 
	 * shows that there is no check, it means there is a possible move to be
	 * made and so it is not checkmate. If it never finds such a move, it will
	 * return true, meaning checkmate.
	 * 
	 * @param a	The board in which to check if there is a black checkmate
	 * @return boolean		True if the black king is in checkmate, false if not.
	 */
	public static boolean detectCheckmateblack(Board a){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				Piece cur = a.getXY(i, j);
				Position curpos = new Position(i,j);
				if(cur != null){
					if(!cur.isWhite){
						for(int n=0;n<8;n++){
							for(int m=0;m<8;m++){
								Position finpos = new Position(n,m);
								if(cur.isLegalMove(curpos,finpos,a)){
									if(Board.simulateMove(curpos, finpos, a).equals("black")){
										continue;
									}
									if(Board.simulateMove(curpos, finpos, a).equals("none")){
										return false;
									}
								}
							}
						}
					}
				}
		
				
			}
		}
		return true;
	}

	
	/** 
	 * Utility method to move a piece from
	 * a starting position to a finishing position.
	 * This method does not check to make sure the move is legal,
	 * and so it is only called after ensuring so.
	 * 
	 * @param start	The starting position of the piece to be moved
	 * @param finish The ending position that the piece will be moved to
	 */
	public void movePiece(Position start, Position finish){
		setXY(finish.x, finish.y, getXY(start.x, start.y));
		setXY(start.x, start.y, null);
	}

	
	/** 
	 * This method simulates a move from start to finish
	 * based on the input parameter board a. It does so
	 * by creating a deep copy of the Board, and then running the move,
	 * and checking whether or not either team is in check after the move.
	 *
	 * 
	 * @param start	The starting position of the piece to be moved
	 * @param finish The ending position that the piece will be moved to
	 * @param a	The board that the move will be simulated on
	 * @return String	returns the color of the team in check, or "none" if neither are
	 */
	public static String simulateMove(Position start, Position finish,Board a){
		Board copy = new Board();
		for(int i=0;i<8;i++){
			copy.boardmat[i] = Arrays.copyOf(a.boardmat[i], 8);
		}
		copy.movePiece(start, finish);
		if(detectblackcheck(copy)){
			return "black";
		}
		if(detectwhitecheck(copy)){
			return "white";
		}
		return "none";
	}

	




}
