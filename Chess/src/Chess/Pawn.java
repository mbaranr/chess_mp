package Chess;

import javax.swing.ImageIcon;

public class Pawn extends Piece {
	
	//constructor for pawns
	public Pawn(boolean isTaken, boolean isWhite, int row, int col, Board board, char name) {
		super(isTaken, isWhite, row, col, board, name);
		
		//checking if the piece is black or white to choose the correct sprite
		
		this.sprite = isWhite ? new ImageIcon("res/WPawn.png").getImage() : new ImageIcon("res/BPawn.png").getImage();
	}
	
	//checking for the validity of a move, row and col are the values for the tile of the move
	public boolean isValidMove(int row, int col) {
		
		//if the pawn is white
		if(this.isWhite()) {
			//check for first move
			if(this.col == 1) {
				//the pawn can move up to 2 tiles
				if(col - this.col <= 2 && col - this.col > 0 && row == this.row && board.getPiece(row, col) == null) {
					if (col - this.col == 2 && board.getPiece(this.row, this.col+1) != null) {
						return false;
					}else {
						return true;
					}
				}
			//if it is not first move, it can only move one tile
			}else {
				if(col - this.col == 1 && row == this.row && board.getPiece(row, col) == null) {
					return true;
				}
			}
			
			//if it is diagonal to an enemy piece, it can take diagonally!
			if(col > this.col && board.getPiece(row, col) != null && Math.abs(this.row - row) == 1 && Math.abs(this.col - col) == 1) {
				return true;
			}
		
		//if the pawn is black
		}else {
			//check for first move
			if(this.col == 6) {
				//the pawn can move up to 2 tiles
				if(this.col - col <= 2 && this.col - col > 0 && row == this.row && board.getPiece(row, col) == null) {
					
					if (this.col - col == 2 && board.getPiece(this.row, this.col-1) != null) {
						return false;
					}else {
						return true;
					}

				}
			//if it is not first move, it can only move one tile
			}else {
				if(this.col - col == 1 && row == this.row && board.getPiece(row, col) == null) {
					return true;
				}
			}
			//if it is diagonal to an enemy piece, it can take diagonally!
			if(col < this.col && board.getPiece(row, col) != null && Math.abs(this.row - row) == 1 && Math.abs(this.col - col) == 1) {
				return true;
			}
			
		}
		
		return false;
	}
	
}
