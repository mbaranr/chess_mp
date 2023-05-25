package Chess;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Board {
	
	private boolean isWhiteTurn;		//boolean value that determines the turn
	
	private boolean isFirstMove;		//boolean value to check if the first move has been done (for pawn movement)		

	boolean checkMateBlack, checkMateWhite, staleMate;		//boolean values for game logic
	
	private int tilesize = 80;		
	
	//rows and columns of the board
	private int rows = 8;		
	private int cols = 8;		
	
	//matrix holding all the pieces
	private Piece[][] pieces = new Piece[rows][cols];
	
	//linked lists holding the taken pieces
	private LinkedList<Piece> takenWhitePieces = new LinkedList();
	private LinkedList<Piece> takenBlackPieces = new LinkedList();
	
	private CheckHandler checkhandler;
	
	//dummy piece to display it moving through the screen
	Piece pieceToMove;
	
	UIPanel uipanel;
	
	PiecesPanel piecespanel;
	
	//constructor
	public Board(UIPanel uipanel, PiecesPanel piecespanel) {
		
		this.uipanel = uipanel;
		this.piecespanel = piecespanel;
		
		piecespanel.board = this;
		
		checkhandler = new CheckHandler(this);
		
		isFirstMove = false;
		
		//according to conventional chess rules, white is always first
		isWhiteTurn = true;
		
		//setting initial values
		checkMateBlack = false;
		
		checkMateWhite = false;
		
		staleMate = false;
		
		//inserting the pieces into the board
		initializePieces();
	}
	
	public void initializePieces() {
		
		//adding pieces to the board according to chess convention
		
		//white
		pieces[0][0] = new Rook(false, true, 0, 0, this, 'R'); 
		pieces[1][0] = new Knight(false, true, 1, 0, this, 'H'); 
		pieces[2][0] = new Bishop(false, true, 2, 0, this, 'B'); 
		pieces[3][0] = new King(false, true, 3, 0, this, 'K'); 
		pieces[4][0] = new Queen(false, true, 4, 0, this, 'Q');
		pieces[5][0] = new Bishop(false, true, 5, 0, this, 'B'); 
		pieces[6][0] = new Knight(false, true, 6, 0, this, 'H'); 
		pieces[7][0] = new Rook(false, true, 7, 0, this, 'R'); 
		
		//black
		pieces[0][7] = new Rook(false, false, 0, 7, this, 'R'); 
		pieces[1][7] = new Knight(false, false, 1, 7, this, 'H'); 
		pieces[2][7] = new Bishop(false, false, 2, 7, this, 'B'); 
		pieces[3][7] = new King(false, false, 3, 7, this, 'K'); 
		pieces[4][7] = new Queen(false, false, 4, 7, this, 'Q');
		pieces[5][7] = new Bishop(false, false, 5, 7, this, 'B'); 
		pieces[6][7] = new Knight(false, false, 6, 7, this, 'H'); 
		pieces[7][7] = new Rook(false, false, 7, 7, this, 'R'); 
		
		for(int i = 0; i<8; i++) {
			pieces[i][1] = new Pawn(false, true, i, 1, this, 'P');
			pieces[i][6] = new Pawn(false, false, i, 6, this, 'P');
		}
		
	}
	
	public void draw(Graphics g) {
		
		//printing the white and black tiles
		
		for(int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				
				//if row+col is even, draw a white tile
				
				if((i+j) % 2 == 0) {
					g.setColor(new Color(249, 172, 113));
				}else {
					
					//if it is odd, draw a black tile
					
					g.setColor(new Color(103, 51, 20));
				}
				
				g.fillRect(i*tilesize, j*tilesize, tilesize, tilesize);
			}
		}
		
		//if i have clicked a piece, display the legal tiles in green
		
		if(pieceToMove != null) {
			for(int i = 0; i < rows; i++) {
				for (int j = 0; j < rows; j++) {
					if (isValidMove(pieceToMove,i,j)) {
						g.setColor(new Color(86, 218, 86, 140));
						g.fillRect(i*tilesize, j*tilesize, tilesize, tilesize);
					}
				}
			}
		}
		
		//iterate through the matrix and display each piece
		
		for(int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				if (pieces[i][j] != null) {
					pieces[i][j].draw(g);
				}
			}
		}
		
	}
	
	//check if a certain move is valid
	public boolean isValidMove(Piece piece, int row, int col) {
		
		//if any of these flags comes back as true, then the move is not valid
		
		//if it is not the correct turn
		if((piece.isWhite() && !isWhiteTurn) || (!piece.isWhite() && isWhiteTurn)) {
			return false;
		}
		
		//if the row and col I'm trying to go to is the same as the current row and col
		if(piece.row == row && piece.col == col) {
			return false;
		}
		
		//if the tile im trying to go to has a piece of the same color
		if(pieces[row][col]!=null) {
			if(pieces[row][col].sameTeam(piece)) {
				return false;
			}
		}
		
		//if the tile is not legal due to the rules of piece behaviour
		if(!piece.isValidMove(row, col)) {
			return false;
		}
		
		//if the path collides with a piece before reaching the tile
		if(piece.moveCollided(row, col)) {
			return false;
		}
		
		//if the move will make the king be in check
		if(checkhandler.kingInCheck(piece, row, col)) {
			return false;
		}
		
		//if false to all of these, the move is valid
		return true;
		
	}
	
	//making a move
	public void makeMove(Piece piece, int row, int col) {
		
		//searching the position of both kings
		
		Piece wKing = getKing(true);

		Piece bKing = getKing(false);
		
		//checking if this is a castling move, if it is, move the rook as well
		if(piece.name == 'K' && Math.abs(wKing.row - row) == 2 && piece.isWhite()) {
			
			//for white king
			
			pieces[2][0] = pieces[0][0];
			pieces[0][0] = null;
			
			pieces[2][0].row = 2; 
			pieces[2][0].col = 0;
			
			pieces[2][0].x = 2 * tilesize;
			pieces[2][0].y = 0 * tilesize;
		}else if (piece.name == 'K' && Math.abs(bKing.row - row) == 2 && !piece.isWhite()) {
			
			//for black king
			
			pieces[2][7] = pieces[0][7];
			pieces[0][7] = null;
			
			pieces[2][7].row = 2; 
			pieces[2][7].col = 7;
			
			pieces[2][7].x = 2 * tilesize;
			pieces[2][7].y = 7 * tilesize;
		}
		
		//let the program know the first Move has been done to start the timer
		setFirstMove(true);
		
		//if the move implies taking a piece, add this piece to the list
		if(pieces[row][col] != null) {
			if(pieces[row][col].isWhite()) {
				takenWhitePieces.add(pieces[row][col]);
				piecespanel.repaint();
			}else {
				takenBlackPieces.add(pieces[row][col]);
				piecespanel.repaint();
			}
		}
		
		//exchange the position of the piece
		pieces[row][col] = pieces[piece.row][piece.col];
		//delete the contents of the previous tile
		pieces[piece.row][piece.col] = null;
		
		//change the attributes of the piece object
		piece.row = row; 
		piece.col = col;
		
		piece.x = row * tilesize;
		piece.y = col * tilesize;
		
		pieceToMove = null;
		
		//check for pawn promotion
		if (piece.name == 'P' && piece.isWhite() && col == 7) {
			pieces[row][col] = null;
			pieces[row][col] = new Queen(false, true, row, col, this, 'Q');
		} else if(piece.name == 'P' && !piece.isWhite() && col == 0) {
			pieces[row][col] = null;
			pieces[row][col] = new Queen(false, false, row, col, this, 'Q');
		}
		
		//change the turn
		isWhiteTurn = !isWhiteTurn;
		
		//check if it is check mate or stale mate
		checkhandler.isCheckMateOrStaleMate();
		
	}
	
	//method to get the king object from the grid
	public Piece getKing(boolean isWhite) {
		for(int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				if (pieces[i][j] != null && pieces[i][j].name == 'K' && pieces[i][j].isWhite() == isWhite) {
					return pieces[i][j];
				}
			}
		}
		return null;
	}
	
	//size methods for the linked lists
	public int takenWhitePiecesLen() {
		return takenWhitePieces.size();
	}
	
	public int takenBlackPiecesLen() {
		return takenBlackPieces.size();
	}
	
	//getter and setters
	
	public Piece getTakenWhitePieces(int i){
		return takenWhitePieces.get(i);
	}
	
	public Piece getTakenBlackPieces(int i){
		return takenBlackPieces.get(i);
	}
	
	public Piece getPiece(int r, int c) {
		return pieces[r][c];
	}

	public int getTilesize() {
		return tilesize;
	}

	public boolean isWhiteTurn() {
		return isWhiteTurn;
	}

	public void setWhiteTurn(boolean isWhiteTurn) {
		this.isWhiteTurn = isWhiteTurn;
	}

	public boolean isFirstMove() {
		return isFirstMove;
	}

	public void setFirstMove(boolean isFirstMove) {
		this.isFirstMove = isFirstMove;
	}
	
	
	
	

}
