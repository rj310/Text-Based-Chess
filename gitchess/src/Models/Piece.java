package Models;

import java.util.HashMap;
/**
 * @author Rohan Joshi
 * @author Nicholas Cheniara
 */

public abstract class Piece {

	/**
	 * Checks if a piece has been eliminated
	 */
	public boolean isElim=false;
	/**
	 * checks if a piece has moved, mostly used for castling, enpassant
	 */
	public boolean hasMoved=false;
	/**
	 * checks if a piece is white if true, if false it is black
	 */
	public boolean isWhite = false;


	/** 
     * The following legalMoves method first determines which side the piece belongs too then 
	 * provides a list of all the possible move sets that a piece can do
     * @param start
     *          start param contains x and y coordinates of the starting position of piece
     * @return HashMap(String, String)
     *          return map contains a list of all the possible moves that a given piece can move
     */
	public abstract HashMap<String, String> legalMoves(Position start);

	/** 
	 * isLegalMove first checks if the piece falls outside of the scope of the board which is an invalid move, 
	 * then it enables a piece to capture if an oppossing piece is in that location. 
	 * Finally it disables the ability for a piece to capture friendlies.
	 * @param start
	 * 			start param contains x and y coordinates of the starting position of piece
	 * @param finish
	 * 			finish param contains x and y coordinates of the starting position of piece
	 * @param a
	 * 			a param calls in the current board that contains the location of all other pieces
	 * @return boolean
	 * 			boolean return which states whether the piece can legally make the move 
	 */
	public abstract boolean isLegalMove(Position start, Position finish, Board a);
	
	
	
	
	
}
