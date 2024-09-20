import javax.swing.ImageIcon;

public class Queen extends Piece {
	
	//constructor for queens
	
	public Queen(boolean isTaken, boolean isWhite, int row, int col, Board board, char name) {
		super(isTaken, isWhite, row, col, board, name);
		
		//checking if the piece is black or white to choose the correct sprite
		
		this.sprite = isWhite ? new ImageIcon("../res/WQueen.png").getImage() : new ImageIcon("../res/BQueen.png").getImage();
	}
	
	public boolean isValidMove(int row, int col) {
		//checking if the tile is in the same row or column than the current queen tile
		if(this.col == col || this.row == row) {
			return true;
		}
		//checking if the tile is in the same diagonal than the current queen tile
		if(Math.abs(col - this.col) == Math.abs(row - this.row)) {
			return true;
		}
		
		return false;
	}
	
	//combining both rook and bishop methods
	//Checking if the move collides with a piece, meaning that it should stop
	
	public boolean moveCollided(int row, int col) {
		
		
		if(Math.abs(this.row - row) == Math.abs(this.col - col)) {
			
			//up right
			if (this.row < row && this.col > col ) {
				for(int i = 1; i < Math.abs(this.col - col); i++) {
					if(board.getPiece(row - i, col + i) != null) {
						return true;
					}
				}
			}
			
			//up left
			if (this.row > row && this.col > col) {
				for(int i = 1; i < Math.abs(this.col - col); i++) {
					if(board.getPiece(row + i, col + i) != null) {
						return true;
					}
				}
			}
			
			//down right
			if (this.row < row && this.col < col) {
				for(int i = 1; i < Math.abs(this.col - col); i++) {
					if(board.getPiece(row - i, col - i) != null) {
						return true;
					}
				}
			}
			
			//down left
			if (this.row > row && this.col < col) {
				for(int i = 1; i < Math.abs(this.col - col); i++) {
					if(board.getPiece(row + i, col - i) != null) {
						return true;
					}
				}
			}
			
		}
		
		if(this.row == row || this.col == col) {
			
			//up
			
			if (this.col > col) {
				for(int i = col+1; i < this.col; i++) {
					if(board.getPiece(this.row, i) != null) {
						return true;
					}
				}
			}
			
			//down
			
			if (this.col < col) {
				for(int i = col-1; i > this.col; i--) {
					if(board.getPiece(this.row, i) != null) {
						return true;
					}
				}
			}
			
			//right
			
			if (this.row < row) {
				for(int i = row-1; i > this.row; i--) {
					if(board.getPiece(i, this.col) != null) {
						return true;
					}
				}
			}
			
			//left
			
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
