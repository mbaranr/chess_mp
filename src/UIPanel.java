import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

//panel handling user interaction elements
public class UIPanel extends JPanel implements ActionListener {
	
	private static final int PANEL_WIDTH = 200;					// panel
	private static final int PANEL_HEIGHT = 240;
	
	private boolean gameStart;									//game starting flag
	
	private boolean whiteFinished, blackFinished;				//timer finishing flags
	
	//labels for the contents of the timers
	JLabel whiteLabel;	
	JLabel blackLabel;
	Font font1 = new Font("Arial", Font.PLAIN, 70);	
	
	//creating the timers
	Timer whiteTimer;
	
	Timer blackTimer;
	
	//separate values for minutes and seconds
	private int whiteSecond, whiteMinute, blackSecond, blackMinute;
	
	//string variables for the decimal format of those values
	String whiteDdSecond, whiteDdMinute, blackDdSecond, blackDdMinute;
	
	DecimalFormat dFormat = new DecimalFormat("00");
	
	//options of my combobox
	String[] timemode = {"Bullet", "Blitz", "Rapid"};
	
	String selectedMode;
	
	JComboBox comboBox;
	
	JButton start;
	
	//constructor
	public UIPanel() {
		
		//initializing flags to false
		whiteFinished = false;
		blackFinished = false;
		
		//creating a combo box for the time modes and start button
		
		comboBox = new JComboBox(timemode);

		start = new JButton();
		
		comboBox.addActionListener(this);
		start.addActionListener(this);
		
		comboBox.setToolTipText("Select time mode");
		start.setText("Start!");
		
		//Creating labels that will display the timers
		
		whiteLabel = new JLabel("");
		blackLabel = new JLabel("");

		whiteLabel.setFont(font1);
		blackLabel.setFont(font1);
		
		whiteLabel.setText("00:00");
		blackLabel.setText("00:00");
		
		//adding every swing element to the panel
		this.add(whiteLabel);
		this.add(blackLabel);
		
		this.add(comboBox);
	    this.add(start);
	    
		setSize(PANEL_WIDTH, PANEL_HEIGHT);												// setting the size of the panel
	    setFocusable(true);
	     
	}
	
	//timer for white's turn
	public void whiteCountDownTimer() {
		
		whiteSecond = 0;
		
		switch (selectedMode) {
		
		//depending on the selected mode from the combo box, the starting time will change
		
		case "Bullet":
			
			whiteMinute = 3;
			
			break;
			
		case "Blitz":
		
			whiteMinute = 5;
			
			break;
			
		case "Rapid":
			
			whiteMinute = 15;
			
			break;
		}
		
		//creating the timer
		whiteTimer = new Timer(1000, new ActionListener() {
			
			//for each tick
			public void actionPerformed(ActionEvent e) {
				
				//decrease the seconds
				whiteSecond--;
				
				whiteDdSecond = dFormat.format(whiteSecond);
				whiteDdMinute = dFormat.format(whiteMinute);
				whiteLabel.setText(whiteDdMinute + ":" + whiteDdSecond);
				
				//if the seconds reach -1, change it back to 59 but decrease 1 minute
				if(whiteSecond==-1) {
					whiteSecond = 59;
					//decrease the minutes
					whiteMinute--;
					whiteDdSecond = dFormat.format(whiteSecond);
					whiteDdMinute = dFormat.format(whiteMinute);	
					whiteLabel.setText(whiteDdMinute + ":" + whiteDdSecond);
				}
				//if both minutes and seconds are 0, timer finished
				if(whiteMinute==0 && whiteSecond==0) {
					whiteLabel.setText("00:00");
					//raise flag
					whiteFinished = true;
				}
			}
			
		});
				
	}
	
	public void blackCountDownTimer() {
		
		blackSecond = 0;
		
		switch (selectedMode) {
		//depending on the selected mode from the combo box, the starting time will change
		case "Bullet":
						
			blackMinute = 3;
			
			break;
			
		case "Blitz":
		
			blackMinute = 5;
			
			break;
			
		case "Rapid":
			
			blackMinute = 15;
			
			break;
		}
		//creating the timer
		blackTimer = new Timer(1000, new ActionListener() {
			
			//for each tick
			public void actionPerformed(ActionEvent e) {
				//decrease the seconds
				blackSecond--;
				
				blackDdSecond = dFormat.format(blackSecond);
				blackDdMinute = dFormat.format(blackMinute);
				
				blackLabel.setText(blackDdMinute + ":" + blackDdSecond);
				//if the seconds reach -1, change it back to 59 but decrease 1 minute
				if(blackSecond==-1) {
					blackSecond = 59;
					//decrease the minutes
					blackMinute--;
					blackDdSecond = dFormat.format(blackSecond);
					blackDdMinute = dFormat.format(blackMinute);	
					blackLabel.setText(blackDdMinute + ":" + blackDdSecond);
				}
				//if both minutes and seconds are 0, timer finished
				if(blackMinute==0 && blackSecond==0) {
					//raise flag
					blackFinished = true;
				}
			}
			
		});
		
	}
	
	//resume or stop white timer
	public void runWhiteTimer(boolean run) {
		
		if(run) {
			whiteTimer.start();		//start the timer
		}else {
			whiteTimer.stop();		//stop the timer
		}
		
	}
	
	//resume or stop black timer
	public void runBlackTimer(boolean run) {
		
		if(run) {
			blackTimer.start();
		}else {
			blackTimer.stop();
		}
		
	}


	public int getPanelWidth() {
		return PANEL_WIDTH;
	}


	public int getPanelHeight() {
		return PANEL_HEIGHT;
	}

	
	//action listener for the combo boxes and buttons
	public void actionPerformed(ActionEvent e) {
		
		//get the selected mode from the combo box options
		if(e.getSource() == comboBox) {
			selectedMode = (String) comboBox.getSelectedItem();
		}
		
		//if start button is pressed and the game didn't start already
		if(e.getSource() == start) {
			if(selectedMode == null || gameStart) {
				return;
			}else {
				//create timers and raise the flag
				whiteCountDownTimer();
				blackCountDownTimer();
				gameStart = true;	
			}
		}
	}
	
	//getters and setters
	public boolean isGameStart() {
		return gameStart;
	}


	public void setGameStart(boolean gameStart) {
		this.gameStart = gameStart;
	}


	public boolean isWhiteFinished() {
		return whiteFinished;
	}


	public void setWhiteFinished(boolean whiteFinished) {
		this.whiteFinished = whiteFinished;
	}


	public boolean isBlackFinished() {
		return blackFinished;
	}


	public void setBlackFinished(boolean blackFinished) {
		this.blackFinished = blackFinished;
	}
	
	
	
	
	
}
