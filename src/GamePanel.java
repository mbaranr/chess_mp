import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

// panel class that inherits panel methods and implements the runnable class to create a thread

public class GamePanel extends JPanel implements Runnable{
	
	private static final int PANEL_WIDTH = 640;											// panel
	private static final int PANEL_HEIGHT = 640;										// dimensions
	
	private boolean gameStart;															// logic variables that will determine the current state of the game
	
	private boolean timerStart;
	
	private boolean gameOver;
	
	private int gameEndCode;
	
	// declaring game variables
	
	UIPanel uipanel;
	
	Thread gameThread;
	Graphics graphics;
	Image image;
	Board board;
	
	//images for black and white king for game over message
	Image whiteKing;
	Image blackKing;
	
	//mouse handling for the panel, as this will contain the board
	MouseHandler mousehandler;
	
	PiecesPanel piecespanel;
	
	// constructor
	
	public GamePanel(UIPanel uipanel, PiecesPanel piecespanel) {
																						
		// initializing logic variables as false
		gameStart = false;																
		gameOver = false;
		timerStart = false;
		
		whiteKing = new ImageIcon("../res/WKing.png").getImage();
		blackKing = new ImageIcon("../res/BKing.png").getImage();
		
		this.uipanel = uipanel;
		this.piecespanel = piecespanel;
		
		setLayout(null);
		setSize(PANEL_WIDTH, PANEL_HEIGHT);												// setting the size of the panel
	    setFocusable(true);																// setting focusable to true to receive keyboard input 
	    
	    setVisible(true);
	        
		gameThread = new Thread(this);													// create and
		gameThread.start();																// start thread
	}
	
	// paint function that paints bigger image into the panel
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);																	// calling draw method
		g.drawImage(image,0,0,this);
	}
	
	// draw method that varies depending on game state
	
	public void draw(Graphics g) {
		
		//if the game started, draw the board
		if(gameStart) {
			piecespanel.reset = false;
			board.draw(g);
		//if it finished, display the appropriate game ending conditions
		}else if(gameOver) {
			board = null;
			piecespanel.repaint();
			g.setFont(new Font("Arial", Font.PLAIN, 50));
			
			
			//each code represents a condition
			
			//black timer finished
			if(gameEndCode == 0) {
				g.drawImage(whiteKing, 180, 230, null);
				
				g.drawString("White wins!", 280, 300);
				
			//white timer finished
			} else if(gameEndCode == 1) {
				g.drawImage(blackKing, 180, 230, null);
				
				g.drawString("Black wins!", 280, 300);
				
			//white check mated
			} else if(gameEndCode == 2) {
				g.drawImage(blackKing, 180, 230, null);
				
				g.drawString("Black wins!", 280, 300);
				
			//black check mated
			} else if(gameEndCode == 3) {
				g.drawImage(whiteKing, 180, 230, null);
				
				g.drawString("White wins!", 280, 300);
				
			//stalemate
			} else {
				g.drawImage(whiteKing, 180, 230, null);
				
				g.drawString("Draw!", 280, 300);
				
				g.drawImage(blackKing, 440, 230, null);
			}
		}
	}
	
	// run method that handles 60 frames per second
	
	public void run() {
		
		// setting up thread
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;				
		
		// thread loop
		
		while(true) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			// if condition met for every frame
			
			if (delta >= 1) {
				
				repaint();														// repaint inherited method
				
				if(uipanel.isGameStart()) {
					
					if(board == null) {
						
						board = new Board(uipanel, piecespanel);
						mousehandler = new MouseHandler(board);
					    
						this.addMouseListener(mousehandler);
						this.addMouseMotionListener(mousehandler);
						
					}
					
					gameStart = true;
					
					//check for game ending flags
					if(board.checkMateWhite || board.checkMateBlack || board.staleMate || uipanel.isBlackFinished() || uipanel.isWhiteFinished()) {
						
						//if ended, reset original values
						
						piecespanel.reset = true;
						
						timerStart = false;
						board.setFirstMove(false);
						
						//reset timers
						uipanel.runBlackTimer(false);
						uipanel.runWhiteTimer(false);
						uipanel.blackTimer = null;
						uipanel.whiteTimer = null;
						
						uipanel.whiteLabel.setText("00:00");
						uipanel.blackLabel.setText("00:00");
						
						uipanel.setGameStart(false);
						gameStart = false;
						gameOver = true;
						
						//return the code of the flag
						
						if(uipanel.isBlackFinished()) {
							gameEndCode = 0;
						} else if(uipanel.isWhiteFinished()) {
							gameEndCode = 1;
						} else if(board.checkMateWhite) {
							gameEndCode = 2;
						} else if(board.checkMateBlack) {
							gameEndCode = 3;
						} else {
							gameEndCode = 4;
						}
						
						uipanel.setBlackFinished(false);
						uipanel.setWhiteFinished(false);
						board.checkMateWhite = false;
						board.checkMateBlack = false;
					}
					
					//if the first move has been done, start the timers
					if(board.isFirstMove()) {
						timerStart = true;
					}
					
					//when the turns change, pause one timer and resume the other one
					if(board.isWhiteTurn() && timerStart) {
						uipanel.runWhiteTimer(true);
						uipanel.runBlackTimer(false);
					}else if(!board.isWhiteTurn() && timerStart){
						uipanel.runBlackTimer(true);
						uipanel.runWhiteTimer(false);
					}
				}
				delta--;														// decrease delta
			}
		}
	}

	// getters and setters
	
	public int getPanelWidth() {
		return PANEL_WIDTH;
	}
	
	public int getPanelHeight() {
		return PANEL_HEIGHT;
	}

	public boolean isGameStart() {
		return gameStart;
	}

	public void setGameStart(boolean gameStart) {
		this.gameStart = gameStart;
	}

}
