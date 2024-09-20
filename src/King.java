import javax.swing.ImageIcon;

public class King extends Piece {

	//constructor
	public King(boolean isTaken, boolean isWhite, int r, int c, Board board, char name) {
		
		super(isTaken, isWhite, r, c, board, name);
		
		//checking if the piece is black or white to choose the correct sprite
		
		this.sprite = isWhite ? new ImageIcon("../res/WKing.png").getImage() : new ImageIcon("../res/BKing.png").getImage();
	}
	
	public boolean isValidMove(int row, int col) {
		//if tile is one block away from king, return true
		if((this.row == row && Math.abs(this.col - col) == 1) || (Math.abs(this.row - row) == 1 && this.col == col) || (Math.abs(this.row - row) == 1 && Math.abs(this.col - col) == 1)) {
			return true;
		}
		if(this.isWhite()) {
			
			//special case for castling, meaning that the king can move 2 tiles
			//checking if the rook is in the correct position
			
			if(this.col == 0 && this.row == 3 && board.getPiece(this.row - 3, this.col) != null && board.getPiece(this.row - 3, this.col).name == 'R') {
				
				//checking if there are no pieces blocking the king from doing the move
				
				if(board.getPiece(this.row - 2, this.col) == null && board.getPiece(this.row - 1, this.col) == null) {
					if(row == 1 && col == 0) {
						return true;
					}
				}
			}
		}else {
			
			//same castling case but for black king
			//checking if the rook is in the correct position
			
			if(this.col == 7 && this.row == 3 && board.getPiece(this.row - 3, this.col) != null && board.getPiece(this.row - 3, this.col).name == 'R') {
				
				//checking if there are no pieces blocking the king from doing the move
				
				if(board.getPiece(this.row - 2, this.col) == null && board.getPiece(this.row - 1, this.col) == null) {
					if(row == 1 && col == 7) {
						return true;
					}
				}
			}
		}
		return false;
	}
	

}
