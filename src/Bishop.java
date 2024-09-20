import javax.swing.ImageIcon;

public class Bishop extends Piece {
	
	//constructor for bishops
	
	public Bishop(boolean isTaken, boolean isWhite, int row, int col, Board board, char name) {
		super(isTaken, isWhite, row, col, board, name);
		
		//checking if the piece is black or white to choose the correct sprite
		this.sprite = isWhite ? new ImageIcon("../res/WBishop.png").getImage() : new ImageIcon("../res/BBishop.png").getImage();
	}
	
	
	//checking for the validity of a move, row and col are the values for the tile of the move
	public boolean isValidMove(int row, int col) {
		//if tile is diagonal to the bishop, return true
		if(Math.abs(col - this.col) == Math.abs(row - this.row)) {
			return true;
		}
		return false;
	}
	
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
		
		return false;
		
	}
	
}
