package Models;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Rohan Joshi
 * @author Nicholas Cheniara
 */
public class Pawn extends Piece {
	 /**
	 * Creation of a Pawn constructor 
	 * Constructor takes in three boolean values
	 * @param elim 	isElim determines if the piece has been eliminated
	 * @param moved	hasMoved determines if the piece has moved on the board
	 * @param white	isWhite is to determine the color piece 
	 */
	public Pawn(boolean elim, boolean moved, boolean white) {
		isElim = elim;
		hasMoved = moved;
		isWhite = white;
		
	}
	/**
	 * The following legalMoves method first determines which side the piece belongs too then 
	 * provides a list of all the possible move sets that a pawn piece can move based on whether
	 * it has moved or not.
	 * @param start
	 * 			start param contains x and y coordinates of the starting position of piece
	 * @return HashMap(String, String)
	 * 			return map contains a list of all the possible moves that a given piece can move
	 */
	@Override
	public HashMap<String, String> legalMoves(Position start) {
		ArrayList<Position> ans = new ArrayList<Position>();
		if(isWhite) {
			if(hasMoved == false){
				ans.add(new Position(start.x-1,start.y));
				ans.add(new Position(start.x-2,start.y));
			}
			else{
				ans.add(new Position(start.x-1,start.y));
			}
		}
		else {
			if(hasMoved == false){
				ans.add(new Position(start.x+1,start.y));
				ans.add(new Position(start.x+2,start.y));
			}
			else{
				ans.add(new Position(start.x+1,start.y));
			}
		}
		HashMap<String, String> map = new HashMap<>();
		for(Position p : ans){
			map.put(p.toString(), "");
		}
		return map;
	}
	
	
	/** 
	 * toString method returns is a String value of the pawn piece based on if it is white or black
	 * @return String
	 * 			String wp or bp is return based on the isWhite boolean condition
	 */
	public String toString() {
		if(isWhite) {
			return "wp";
		}
		else {
			return "bp";
		}
	}
	
	/** 
	 * isLegalMove first checks if the piece falls outside of the scope of the board which is an invalid move, 
	 * then it enables a pawn to capture diagnolly if an oppossing piece is in that location. 
	 * Finally it disables the ability for a pawn piece to capture vertically for oppossing team or even a friendly capture.
	 * @param start
	 * 			start param contains x and y coordinates of the starting position of piece
	 * @param finish
	 * 			finish param contains x and y coordinates of the starting position of piece
	 * @param a
	 * 			a param calls in the current board that contains the location of all other pieces
	 * @return boolean
	 * 			boolean return which states whether the piece can legally make the move 
	 */
	@Override
	public boolean isLegalMove(Position start, Position finish, Board a) {

		if(finish.x > 7 || finish.x <0 || finish.y >7 || finish.y <0){
			return false;
		}
		Piece s = a.getXY(start.x, start.y);
		Piece cur = a.getXY(finish.x, finish.y);

		if(isWhite){
			if(start.x - 1 == finish.x){
				if(finish.y == start.y-1){
					if(cur!=null){
						return true;
					}
				}
				if(finish.y == start.y+1){
					if(cur!=null){
						return true;
					}

				}
			}
		}
		else{
			if(start.x + 1 == finish.x){
				if(finish.y == start.y-1){
					if(cur!=null){
						return true;
					}
				}
				if(finish.y == start.y+1){
					if(cur!=null){
						return true;
					}

				}
			}
		}

		HashMap<String, String> map = legalMoves(start);
		if(!map.containsKey(finish.toString())){
			return false;
		}
		if(cur==null) {
			return true;
		}

		if(cur.isWhite==s.isWhite){
			return false;
		}
		if(!cur.isWhite==s.isWhite){
			return false;
		}
		return true;
	}

}
