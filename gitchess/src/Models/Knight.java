package Models;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Rohan Joshi
 * @author Nicholas Cheniara
 */
public class Knight extends Piece {
     /**
	 * Creation of a Knight constructor 
	 * Constructor takes in three boolean values
	 * @param elim 	isElim determines if the piece has been eliminated
	 * @param moved	hasMoved determines if the piece has moved on the board
	 * @param white	isWhite is to determine the color piece 
	 */
	public Knight(boolean elim, boolean moved, boolean white) {
		isElim = elim;
		hasMoved = moved;
		isWhite = white;
		
	}
	
    /** 
     * The following legalMoves method first determines which side the piece belongs too then 
	 * provides a list of all the possible move sets that a Knight piece can move with its unique moveset.
     * @param start
     *          start param contains x and y coordinates of the starting position of piece
     * @return HashMap(String, String)
     *          return map contains a list of all the possible moves that a given piece can move
     */
    @Override
	public HashMap<String, String> legalMoves(Position start) {
		ArrayList<Position> ans = new ArrayList<Position>();
		ans.add(new Position(start.x-1,start.y-2));
        ans.add(new Position(start.x+1,start.y-2));

        ans.add(new Position(start.x-2,start.y-1));
        ans.add(new Position(start.x+2,start.y-1));

        ans.add(new Position(start.x-1,start.y+2));
        ans.add(new Position(start.x+1,start.y+2));

        ans.add(new Position(start.x-2,start.y+1));
        ans.add(new Position(start.x+2,start.y+1));

        HashMap<String, String> map = new HashMap<>();
		for(Position p : ans){
			map.put(p.toString(), "");
		}
		return map;
    }
	
	/** 
	 * toString method returns is a String value of the Knight piece based on if it is white or black
	 * @return String
	 * 			String wN or bN is return based on the isWhite boolean condition
	 */
    public String toString() {
		if(isWhite) {
			return "wN";
		}
		else {
			return "bN";
		}
	}
	
    /** 
	 * isLegalMove first checks if the piece falls outside of the scope of the board which is an invalid move, 
	 * then it enables a knight to capture using its moveset if an oppossing piece is in that location. 
	 * Finally it disables the ability for a knight piece to capture friendlies.
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
		if(finish.x > 7 || finish.x < 0 || finish.y > 7 || finish.y < 0){
			return false;
		}
		HashMap<String, String> map = legalMoves(start);
		if(!map.containsKey(finish.toString())){
			return false;
		}
		if(a.getXY(finish.x, finish.y)!=null && a.getXY(finish.x, finish.y).isWhite==a.getXY(start.x, start.y).isWhite){
			return false;
		}
		return true;
	}

}
