package Chess;

import javax.swing.ImageIcon;

public class Rook extends Piece {
	
	//constructor
	public Rook(boolean isTaken, boolean isWhite, int row, int col, Board board, char name) {
		super(isTaken, isWhite, row, col, board, name);
		this.sprite = isWhite ? new ImageIcon("res/WRook.png").getImage() : new ImageIcon("res/BRook.png").getImage();
	}
	
	//checking for the validity of a move, row and col are the values for the tile of the move
	//polymorphism
	public boolean isValidMove(int row, int col) {
		//if tile is in the same row or column, return true
		if(this.col == col || this.row == row) {
			return true;
		}
		return false;
	}
	
	//Checking if the move collides with a piece, meaning that it should stop
	public boolean moveCollided(int row, int col) {
		
		if(this.row == row || this.col == col) {
			
			//scanning up
			
			if (this.col > col) {
				for(int i = col+1; i < this.col; i++) {
					if(board.getPiece(this.row, i) != null) {
						return true;
					}
				}
			}
			
			//scanning down
			
			if (this.col < col) {
				for(int i = col-1; i > this.col; i--) {
					if(board.getPiece(this.row, i) != null) {
						return true;
					}
				}
			}
			
			//scanning right
			
			if (this.row < row) {
				for(int i = row-1; i > this.row; i--) {
					if(board.getPiece(i, this.col) != null) {
						return true;
					}
				}
			}
			
			//scanning left
			
			if (this.row > row) {
				for(int i = row+1; i < this.row; i++) {
					if(board.getPiece(i, this.col) != null) {
						return true;
					}
				}
			}
			
			return false;
		}
		
		return false;
		
	}

}
