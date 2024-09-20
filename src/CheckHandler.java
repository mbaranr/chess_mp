public class CheckHandler {
	
	Board board;	
	
	public CheckHandler(Board board) {
		
		this.board = board;		//passing the board as a parameter
		
	}
	
	//if it is white's turn, iterate through every black piece and see if the white king is in check after that move
	//if it is not white's turn, do the opposite
	public boolean kingInCheck(Piece piece, int row, int col) {
		
		if (board.isWhiteTurn()) {
			
			//for white king
			Piece king = board.getKing(true);
			
			for(int i = 0; i < 8; i++) {
				
				for(int j = 0; j < 8; j++) {
					
					if(board.getPiece(i, j) != null && !board.getPiece(i, j).isWhite()) {
						
						//for every black piece, see if it is threatening the king
						switch (board.getPiece(i, j).name) {
						
						//for knights
						case 'H':
							
							if(checkByKnight(piece, king, row, col, i, j)) {
								return true;
							}
						
							break;
						
						//for rooks
						case 'R':
							
							if(checkByRook(piece, king, row, col, i, j)) {
								return true;
							}
							
							break;
						
						//for bishops
						case 'B':
							
							if(checkByBishop(piece, king, row, col, i, j)) {
								return true;
							}
							
							break;
						
						//for queens
						case 'Q':
							
							if(checkByBishop(piece, king, row, col, i, j) || checkByRook(piece, king, row, col, i, j)) {
								return true;
							}
							
							break;
						
						//for kings
						case 'K':
							
							if(checkByKing(piece, king, row, col, i, j)) {
								return true;
							}
							
							break;
						
						//for pawns
						case 'P':
							
							if(checkByPawn(piece, king, row, col, i, j)) {
								return true;
							}
							
							break;
						}
					}
				}
			}
			
		} else {
			
			//do the same but for black king
			
			Piece king = board.getKing(false);
			
			for(int i = 0; i < 8; i++) {
				
				for(int j = 0; j < 8; j++) {
					
					if(board.getPiece(i, j) != null && board.getPiece(i, j).isWhite()) {
						
						//for every white piece, see if it is threatening the king

						switch (board.getPiece(i, j).name) {
						
						//for knights
						case 'H':
							
							if(checkByKnight(piece, king, row, col, i, j)) {
								return true;
							}
						
							break;
						
						//for rooks
						case 'R':
							
							if(checkByRook(piece, king, row, col, i, j)) {
								return true;
							}
							
							break;
						
						//for bishops	
						case 'B':
							
							if(checkByBishop(piece, king, row, col, i, j)) {
								return true;
							}
							
							break;
							
						//for queens	
						case 'Q':
							
							if(checkByBishop(piece, king, row, col, i, j) || checkByRook(piece, king, row, col, i, j)) {
								return true;
							}
							
							break;
							
						//for kings	
						case 'K':
							
							if(checkByKing(piece, king, row, col, i, j)) {
								return true;
							}
							
							break;
							
						//for pawns	
						case 'P':
							
							if(checkByPawn(piece, king, row, col, i, j)) {
								return true;
							}
							
							break;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	//check if the rook is threatening the king
	public boolean checkByRook(Piece piece, Piece king, int row, int col, int i, int j) {
		//if the piece i am moving is a king, do additional checking for tiles in the path of the new king's pos
		if(piece.name == 'K') {
			
			if((col == j || row == i) && !(row == i && col == j)) {
				
				//because the rook's scan will detect the old king position as an obstacle in its path, we want to ignore it
				
				//scan down
				
				if (j > col) {
					for(int m = col+1; m < j; m++) {
						if(board.getPiece(i, m) != null) {
							if(board.getPiece(i, m).name != 'K') {
								return false;
							}
						}
					}
					return true;
				}
				
				//scan up
				
				if (j < col) {
					for(int m = col-1; m > j; m--) {
						if(board.getPiece(i, m) != null) {
							if(board.getPiece(i, m).name != 'K') {
								return false;
							}
						}
					}
					return true;
				}
				
				//scan right
				
				if (i < row) {
					for(int m = row-1; m > i; m--) {
						if(board.getPiece(m, j) != null) {
							if(board.getPiece(m, j).name != 'K') {
								return false;
							}
						}
					}
					return true;
				}
				
				//scan left
				
				if (i > row) {
					for(int m = row+1; m < i; m++) {
						if(board.getPiece(m, j) != null) {
							if(board.getPiece(m, j).name != 'K') {
								return false;
							}
						}
					}
					return true;
				}
			}
		}else {
			//if it is any other piece, scan normally
			if((king.col == j || king.row == i) && !(row == i && col == j)) {
				
				//scan up
				
				if (j > king.col) {
					for(int m = king.col+1; m < j; m++) {
						if((row == i && col == m) || (board.getPiece(i, m) != null && (piece != board.getPiece(i, m)))) {
							return false;
						}
					}
					return true;
				}
				
				//scan down
				
				if (j < king.col) {
					for(int m = king.col-1; m > j; m--) {
						if((row == i && col == m) || (board.getPiece(i, m) != null && (piece != board.getPiece(i, m)))) {
							return false;
						}
					}
					return true;
				}
				
				//scan right
				
				if (i < king.row) {
					for(int m = king.row-1; m > i; m--) {
						if((row == m && col == j) || (board.getPiece(m, j) != null && (piece != board.getPiece(m, j)))) {
							return false;
						}
					}
					return true;
				}
				
				//scan left
				
				if (i > king.row) {
					for(int m = king.row+1; m < i; m++) {
						if((row == m && col == j) || (board.getPiece(m, j) != null && (piece != board.getPiece(m, j)))) {
							return false;
						}
					}
					return true;
				}
			}
		}
		
		return false;
	}
	
	//check if the knight is threatening the king
	public boolean checkByKnight(Piece piece, Piece king, int row, int col, int i, int j) {
		
		//if the piece to move is the king, check if its new position is threatened by a knight
		if(piece.name == 'K') {
			if(Math.abs(col - j) * Math.abs(row - i) == 2 && !(row == i && col == j)) {
				return true;
			}
		//if it is any other piece, check if the current king position is threatened by a knight
		}else {
			if((Math.abs(king.col - j) * Math.abs(king.row - i) == 2) && !(row == i && col == j)) {
				return true;
			} 
		}
		return false;
	}
	
	
	//check if the knight is threatening the king
	public boolean checkByBishop(Piece piece, Piece king, int row, int col, int i, int j) {
		
		if(piece.name == 'K') {
			
			//similar to the rook, if the piece we are moving is a king, the bishop's scan will detect the old king position as an obstacle in its path, we want to ignore it
			if((Math.abs(col - j) == Math.abs(row - i)) && !(row == i && col == j)) {
				
				//up right
				if (i < row && j > col ) {
					for(int m = 1; m < Math.abs(j - col); m++) {
						if(board.getPiece(row - m, col + m) != null) {
							if(board.getPiece(row - m, col + m).name != 'K') {
								return false;
							}
						}
					}
					return true;
				}
				
				//up left
				if (i > row && j > col) {
					for(int m = 1; m < Math.abs(j - col); m++) {
						if(board.getPiece(row + m, col + m) != null) {
							if(board.getPiece(row + m, col + m).name != 'K') {
								return false;
							}
						}
					}
					return true;
				}
				
				//down right
				if (i < row && j < col) {
					for(int m = 1; m < Math.abs(j - col); m++) {
						if(board.getPiece(row - m, col - m) != null) {
							if(board.getPiece(row - m, col - m).name != 'K') {
								return false;
							}
						}
					}
					return true;
				}
				
				//down left
				if (i > row && j < col) {
					for(int m = 1; m < Math.abs(j - col); m++) {
						if(board.getPiece(row + m, col - m) != null) {
							if(board.getPiece(row + m, col - m).name != 'K') {
								return false;
							}
						}
					}
					return true;
				}	
				
			}
		}else {
			
			//if it is any other piece, test if the current king position is threatened by the bishop after the new piece's position
			if((Math.abs(king.col - j) == Math.abs(king.row - i)) && !(row == i && col == j)) {
				//up right
				if (i < king.row && j > king.col ) {
					for(int m = 1; m < Math.abs(j - king.col); m++) {
						if((row == king.row - m && col == king.col + m) || (board.getPiece(king.row - m, king.col + m) != null && (piece != board.getPiece(king.row - m, king.col + m)))) {
							return false;
						}
					}
					return true;
				}
				
				//up left
				if (i > king.row && j > king.col) {
					for(int m = 1; m < Math.abs(j - king.col); m++) {
						if((row == king.row + m && col == king.col + m) || (board.getPiece(king.row + m, king.col + m) != null && (piece != board.getPiece(king.row + m, king.col + m)))) {
							return false;
						}
					}
					return true;
				}
				
				//down right
				if (i < king.row && j < king.col) {
					for(int m = 1; m < Math.abs(j - king.col); m++) {
						if((row == king.row - m && col == king.col - m) || (board.getPiece(king.row - m, king.col - m) != null && (piece != board.getPiece(king.row - m, king.col - m)))) {
							return false;
						}
					}
					return true;
				}
				
				//down left
				if (i > king.row && j < king.col) {
					for(int m = 1; m < Math.abs(j - king.col); m++) {
						if((row == king.row + m && col == king.col - m) || (board.getPiece(king.row + m, king.col - m) != null && (piece != board.getPiece(king.row + m, king.col - m)))) {
							return false;
						}
					}
					return true;
				}	
			}
			
		}
		return false;
	}
	
	//check if the enemy king is threatening the turn's king
	public boolean checkByKing(Piece piece, Piece king, int row, int col, int i, int j) {
		
		//in this case, there are no paths, as kings can only traverse 1 block
		if(piece.name == 'K') {
			//if the piece we are moving is a king, check for its new position
			if((i == row && (Math.abs(j - col) == 1)) || (Math.abs(i - row) == 1 && j == col) || (Math.abs(i - row) == 1 && Math.abs(j - col) == 1)) {
				return true;
			}
			
		}else {
			//if it is not, check for the current position
			if((i == king.row && (Math.abs(j - king.col) == 1)) || (Math.abs(i - king.row) == 1 && j == king.col) || (Math.abs(i - king.row) == 1 && Math.abs(j - king.col) == 1)) {
				return true;
			}
			
		}
		
		return false;
		
	}
	
	//check if the king is threatened by a pawn
	public boolean checkByPawn(Piece piece, Piece king, int row, int col, int i, int j) {
		
		//if the piece we are trying to move is a king, check the new king's position will be threatened by a pawn
		if(piece.name == 'K') {
			
			if(!board.getPiece(i, j).isWhite() && j > col && Math.abs(row - i) == 1 && Math.abs(col - j) == 1) {
				return true;
			}
			
			if(board.getPiece(i, j).isWhite() && j < col && Math.abs(row - i) == 1 && Math.abs(col - j) == 1) {
				return true;
			}
			
		//if it is not a king, check if the current piece position will be threatened by a pawn
		}else {
			
			if(!board.getPiece(i, j).isWhite() && j > king.col && Math.abs(king.row - i) == 1 && Math.abs(king.col - j) == 1 && !(row == i && col == j)) {
				return true;
			}
			
			if(board.getPiece(i, j).isWhite() && j < king.col && Math.abs(king.row - i) == 1 && Math.abs(king.col - j) == 1 && !(row == i && col == j)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	//check if the current configuration of the pieces represents a checkmate or a stalemate
	public void isCheckMateOrStaleMate() {
		
		//if it is white's turn, check for white checkmate
		if(board.isWhiteTurn()) {
			//assign a dummy variable with the king
			Piece dummy = board.getKing(true);
			
			//if the king is in check and there are no valid moves, set the checkmate flag to true
			if(kingInCheck(dummy, dummy.row, dummy.col)) {
				if(noValidMoves(true)) {
					board.checkMateWhite = true;
				}
			//if the king is not in check, and there are not valid moves, set the stalemate flag to true
			}else {
				if(noValidMoves(true)) {
					board.staleMate = true;
				}
			}
			
		//if it is not, check for black checkmate
		}else {
			Piece dummy = board.getKing(false);
			
			//if the king is in check and there are no valid moves, set the checkmate flag to true
			if(kingInCheck(dummy, dummy.row, dummy.col)) {
				if(noValidMoves(false)) {
					board.checkMateBlack = true;
				}
				
			//if the king is not in check, and there are not valid moves, set the stalemate flag to true
			}else {
				if(noValidMoves(false)) {
					board.staleMate = true;
				}
			}
		}
	}
	
	public boolean noValidMoves(boolean isWhite) {
		
		//iterate through every piece of the color, and check if there is at least 1 legal move, if not, return true
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Piece dummy = board.getPiece(i, j);
				if(dummy != null) {
					if(dummy.isWhite() == isWhite) {
						for(int m = 0; m < 8; m++) {
							for(int n = 0; n < 8; n++) {
								if(board.isValidMove(dummy, m, n)) {
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
	
}
