package Game_Settings;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;


public class Screen {
	GamePanel gp;
	Graphics2D g2;
	
	Font arial_40;
	int command = 0;
	JLabel timeLabel = new JLabel();
	
	private ArrayList<String> leaderboardTimes;
	
	public Screen(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Comic Sans MS", Font.PLAIN, 40);
	}
	


	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(arial_40);
		g2.setColor(Color.pink);
		
		//Title
		if (gp.gameState == gp.titleState) {titleScreen();} 
		//Pause
		if (gp.gameState == gp.pauseState) {pauseScreen();}
		//Instruction
		if (gp.gameState == gp.instructionScreen) {instructionScreen();}
		//More Instruction
		if(gp.gameState == gp.buttonLayoutScreen) {buttonLayoutScreen();}
		//Game Over
		if(gp.gameState == gp.gameOver) {gameOverScreen();}
		//In game Options
		if(gp.gameState == gp.optionState) {optionScreen();}
		if(gp.gameState == gp.leaderBoardScreen) {leaderBoardScreen();}
		if(gp.gameState == gp.playState) {
			//Wave Screen
			if(gp.waveState) {waveScreen(); return;}
			if(gp.winCondition) {displayInstruction(); return;}
			//Kill Counter
			drawKillCounter(); 
			//Key Counter
			drawKeys();
			
			//timer
			drawTimer();
		}
		//Winner Screen
		if(gp.gameState == gp.winnerState) {winnerScreen();}
		//leaderBoard
		
	}
	
	public int centerText(String text) {
		int length = (int)(g2.getFontMetrics().getStringBounds(text, g2).getWidth());		
		return (gp.screenWidth/2 - length/2);
	}
	
	public void displayInstruction() {
		gp.winCcount++;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g2.drawImage(gp.tileM.tile[9].image, 0, 0, gp.screenWidth, gp.screenHeight, null);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		g2.setColor(Color.white);
		String text = "Congratualions!!!";
		int x = centerText(text);
		int y = gp.tileSize * 3;		
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
		text = "You've successfully gathered all 16 keys.";
		x = centerText(text);
		y += gp.tileSize;	
		g2.drawString(text, x, y);
		
		text = "Return to Earth and the spawn point to seize your";
		x = centerText(text);
		y += gp.tileSize;	
		g2.drawString(text, x, y);		
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		text = "well-deserved VICTORY";
		x = centerText(text);
		y += gp.tileSize;	
		g2.drawString(text, x, y);		
		
        if ( (gp.winCcount == 180)) {
        	gp.winCcount = 0;
        	gp.winCondition = false;
        }
        
        
	}
	
	
	public void drawKeys() {
		String text = "Keys: " + gp.Keys;
		int x = gp.screenWidth - 200; 
	    
	    // Set y coordinate to place the text at the top of the screen
	    int y = g2.getFontMetrics().getAscent(); // Use ascent to get the height above the baseline
	    
		
	    g2.setColor(Color.white);
	    g2.drawString(text, x, y);
	}
	
	
	public void drawKillCounter() {
		String text = "kill count: " + gp.Score;
		int x = centerText(text);
	    
	    // Set y coordinate to place the text at the top of the screen
	    int y = g2.getFontMetrics().getAscent(); // Use ascent to get the height above the baseline
	    
	    g2.setColor(Color.white);
	    g2.drawString(text, x, y);
	}
	
	public void drawTimer() {
		String text = gp.m+":"+gp.s;
		int x = 10;
		int y = g2.getFontMetrics().getAscent();
		
		g2.setColor(Color.white);
		g2.drawString(text,x,y);
		}
	
	
	public void waveScreen() {		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g2.drawImage(gp.tileM.tile[9].image, 0, 0, gp.screenWidth, gp.screenHeight, null);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		g2.setColor(Color.white);
		String text = "Wave " + gp.Wave;
				
		int x = centerText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
		
        if ( (gp.waveCount == 120)) {
        	gp.waveCount = 0;
        	gp.waveState = false;
        }
	}
	
	public void optionScreen() {
		int x = 0;
		int y = gp.screenHeight/4;
		String text ="";
		
		g2.setColor(Color.white);
		
		
		//Options
		text = "OPTIONS";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 200F));
		//Shadow
		g2.setColor(Color.gray);
			
		x = centerText(text);
		g2.drawString(text, x, y+5);
		
		g2.setColor(Color.white);
		g2.drawString(text, x+5, y);
		
		g2.setFont(g2.getFont().deriveFont(80F));
				
		//INSTRUCTIONS
		text = "INSTRUCTIONS";
		x = centerText(text);
		y += 110;
		g2.drawString(text, x, y);
		if (command == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
			
		////Button Layout
		text = "BUTTON LAYOUT"; 
		x = centerText(text);
		y += 110;
		g2.drawString(text, x, y);
		if (command == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
			
		//Resume
		text = "RESUME GAME"; 
		x = centerText(text);
		y += 110;
		g2.drawString(text, x, y);
		if (command == 2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		//Quit
		text = "MAINMENU"; 
		x = centerText(text);
		y += 110;
		g2.drawString(text, x, y);
		if (command == 3) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
	}
	
	public void gameOverScreen() {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 120F));
		
		text = "GAMEOVER";
		
		//Shadow
		g2.setColor(Color.black);
		int x = centerText(text);
		int y = gp.tileSize * 2 - 10 ;
		g2.drawString(text, x, y);
		
		//Main
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		//Image
		x = gp.screenWidth/2 - 225;
		y = gp.screenHeight/2 - 350;
		g2.drawImage(gp.player.death4, x, y, gp.tileSize * 6, gp.tileSize * 6, null);
		
		
		//Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "RETRY";
		x = centerText(text);
		y += gp.tileSize * 5 + 150;
		g2.drawString(text, x, y);
		if (command == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		//Back to TitleScreen
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "QUIT";
		x = centerText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (command == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
	}
	
	public void winnerScreen() {
		int x = 0;
		int y = gp.screenHeight/4;
		String text = "";
		
		int length = (int)(g2.getFontMetrics().getStringBounds(text, g2).getWidth());
		g2.setColor(new Color(0, 0, 0, 150));
		g2.drawImage(gp.tileM.tile[9].image, 0, 0, gp.screenWidth, gp.screenHeight, null);
		
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 170F));
		
		text = "CONGRATS!";
		
		//Shadow
		g2.setColor(Color.gray);			
		x = centerText(text);
		g2.drawString(text, x, y+5);
		
		g2.setColor(Color.white);
		g2.drawString(text, x+5, y);
		
		//Main
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		//Image
		x = gp.screenWidth/2 - 225;
		y = gp.screenHeight/2 - 150;
		g2.drawImage(gp.player.downSword1, x, y, gp.tileSize * 5, gp.tileSize * 5, null);
		
		
		
		//Retry
		g2.setFont(g2.getFont().deriveFont(80f));
		text = "MAINMENU";
		x = centerText(text);
		y += gp.tileSize * 5.5;
		g2.drawString(text, x, y);
		if (command == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
	}
	
	
	
	
	public void instructionScreen() {
		//Instruction 
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
		g2.setColor(Color.white);
		String text = "INSTRUCTIONS";
		
		int x = centerText(text);
		int y = gp.screenHeight/6;	
		
		//Shadow
		g2.setColor(Color.gray);
		x = centerText(text);
		g2.drawString(text, x, y+5);
		
		g2.setColor(Color.white);
		g2.drawString(text, x+5, y);
		

	
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));			
		text = "Embark on a thrilling quest to gather 16 keys";			 
		x = centerText(text);
		y += 100;
		g2.drawString(text, x, y);
		
		text = "randomly placed across four unique rooms.";			 
		x = centerText(text);
		y += 50;
		g2.drawString(text, x, y);
		
		text = "Conquer each room, unlocking mysteries, and";			 
		x = centerText(text);
		y += 50;
		g2.drawString(text, x, y);
		
		text = "once all keys are in hand, return to Earth's";			 
		x = centerText(text);
		y += 50;
		g2.drawString(text, x, y);
		
		text = "starting point for the ultimate triumph.";			 
		x = centerText(text);
		y += 50;
		g2.drawString(text, x, y);
		
		text = "Good Look!";			 
		x = centerText(text);
		y += 50;	
		g2.drawString(text, x, y);
		
		
		//Back			
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
		text = "Press B to go Back";			
		x = gp.screenWidth - 220; 
		y = gp.screenHeight - 15;	
		g2.drawString(text, x, y);
		
			
		
	}
	
	public void leaderBoardScreen() {
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
		g2.setColor(Color.white);
		String text = "LEADERBOARD";
		
		int x = centerText(text);
		int y = gp.screenHeight/6;
		
		//Shadow
		g2.setColor(Color.gray);
		x = centerText(text);
		g2.drawString(text, x, y+5);
		
		g2.setColor(Color.white);
		g2.drawString(text, x+5, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
		
		y += 40;
		for (String time : leaderboardTimes) {
            g2.drawString(time, x, y);
            y += 40;
        }
		
		//Back
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
		text = "Press B to go Back";			
		x = gp.screenWidth - 220; 
		y = gp.screenHeight - 15;	
		g2.drawString(text, x, y);
	}
	

	public void setLeaderboardTimes(ArrayList<String> leaderboardTimes) {
        this.leaderboardTimes = leaderboardTimes;
    }
	
	public void buttonLayoutScreen() {
		//Button Layout
		
		//Title
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
		g2.setColor(Color.white);
		String text = "HERE IS THE BUTTON LAYOUT";
		int x = centerText(text);
		int y = gp.screenHeight/16;	
		g2.drawString(text, x, y);
		
		//Movement
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));			
		text = "MOVEMENT:";		
		x = gp.screenWidth/2 - 100; 
		y = gp.screenHeight/2 - 250;
		g2.drawString(text, x, y);
				
				//Up
				g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
				text = "Press W to move Upwards";
				x = gp.screenWidth/2 - 300;
				y = gp.screenHeight/2 - 50;
				g2.drawString(text, x, y);
				//Down
				text = "Press S to move Downwards";
				y -= 50;
				g2.drawString(text, x, y);
				//Left
				text= "Press A to move to the Left";
				y -= 50;
				g2.drawString(text, x, y);
				//Right
				text = "Press D to move to the Right";
				y -= 50;
				g2.drawString(text, x, y);
		
		//In Game
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));			
		text = "IN GAME:";		
		x = gp.screenWidth/2 - 100; 
		y = gp.screenHeight/2;
		g2.drawString(text, x, y);
				//shoot
				g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));			
				text = "Press ENTER to shoot";		
				x = gp.screenWidth/2 - 300; 
				y = gp.screenHeight/2 + 50;
				g2.drawString(text, x, y);
				//map
				text = "Press M to bring up the Mini Map";
				y += 50;
				g2.drawString(text, x, y);
				//Pause
				text = "Press P to Pause the game";
				y += 50;
				g2.drawString(text, x, y);
				//Switch Weapon
				text = "Press the SPACEBAR to switch weapon";
				y += 50;
				g2.drawString(text, x, y);
				//options menu
				text = "Press ESCAPE to bring up the Options Menu";
				y += 50;
				g2.drawString(text, x, y);
		
				
		//Back
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));			
		text = "Press B to go Back";		
		x = gp.screenWidth - 220; 
		y = gp.screenHeight - 15;
		g2.drawString(text, x, y);
		
	}
	
	
	public void titleScreen() {
		String text = "";
		int x = 0;
		int y = gp.screenHeight/5;
		
		//Title
		text = "SPACE JAM";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 140F));
		//Shadow
		g2.setColor(Color.gray);
		x = centerText(text);
		g2.drawString(text, x, y+5);
		
		g2.setColor(Color.white);
		g2.drawString(text, x+5, y);
		
		//Character image
		
		x = gp.screenWidth/2 - (gp.tileSize*2);
		y += gp.tileSize*2 - 120;
		g2.drawImage(gp.player.leftSword1, x, y, gp.tileSize * 4, gp.tileSize * 4, null);
			
		
		
		//Menu
		text = "NEW GAME";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
		x = centerText(text);
		y = gp.screenHeight/2 + 150;
		g2.drawString(text, x, y);
		if (command == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		//INSTRUCTIONS
		text = "INSTRUCTIONS";
		x = centerText(text);
		y += 50;
		g2.drawString(text, x, y);
		if (command == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		////Button Layout
		text = "BUTTON LAYOUT"; 
		x = centerText(text);
		y += 50;
		g2.drawString(text, x, y);
		if (command == 2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		//LeaderBoard
		text = "LEADERBOARD"; 
		x = centerText(text);
		y += 50;
		g2.drawString(text, x, y);
		if (command == 3) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		//QUIT
		text = "QUIT"; 
		x = centerText(text);
		y += 50;
		g2.drawString(text, x, y);
		if (command == 4) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
	}
	
	
	public void pauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		g2.setColor(Color.red);
		String text = "PAUSED";
				
		int x = centerText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
}
