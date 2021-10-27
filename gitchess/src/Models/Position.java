package Models;
/**
 * @author Rohan Joshi
 * @author Nicholas Cheniara
 */
public class Position {
	/**
	 * x coordinate, holds the first index of the value in the matrix
	 */
	public int x;
	/**
	 * y coordinate, holds the second index of the value in the matrix
	 */
	public int y;

	/**
	 * Constructor that takes in two coordinates 
	 * nx and ny to set the values of x and y within the
	 * position instance
	 * 
	 * @param nx the x coordinate to be set
	 * @param ny the y coordinate to be set
	 */
	public Position(int nx, int ny) {
		x=nx;
		y=ny;
	}

	/**
	 * @return String 	returns the string form of a Position instance
	 */
	public String toString(){
		return "("+x+","+y+")";
	}
}
