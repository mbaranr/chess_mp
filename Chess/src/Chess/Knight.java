package Chess;

import javax.swing.ImageIcon;

public class Knight extends Piece{
	
	//constructor
	
	public Knight(boolean isTaken, boolean isWhite, int row, int col, Board board, char name) {
		
		super(isTaken, isWhite, row, col, board, name);
		
		//checking if the piece is black or white to choose the correct sprite

		this.sprite = isWhite ? new ImageIcon("res/WKnight.png").getImage() : new ImageIcon("res/BKnight.png").getImage();
	}
	
	//checking for the validity of a move, row and col are the values for the tile of the move
	//polymorphism
	public boolean isValidMove(int row, int col) {
		//if the tile is in an "L" position relative to the knight, return true
		if(Math.abs(col - this.col) * Math.abs(row - this.row) == 2) {
			return true;
		}
		return false;
	}
	
	//not that we don't have to check for piece collision, as knights can jump over pieces
	
}
