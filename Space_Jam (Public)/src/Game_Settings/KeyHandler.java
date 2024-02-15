package Game_Settings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	/* Key Codes ~ http://www.foreui.com/articles/Key_Code_Table.htm */
	public boolean upKey = false, downKey = false, leftKey = false, rightKey = false;
	public boolean shotKey = false;
	//Prevent players from spamming or holding down attack
	public boolean attackHold = false;
	
	GamePanel gp;
	public boolean titleInstruction = true;
	public boolean titleButtonLayout = true;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (gp.gameState == gp.titleState) { titleState(code);}
		
		else if (gp.gameState == gp.gameOver) {gameOver(code);}
		
		else if(gp.gameState == gp.winnerState) {winnerState(code);}
		
		//playState
		else {
			//Movement KEYS
			if (code == KeyEvent.VK_W) upKey = true;	
			if (code == KeyEvent.VK_S) downKey = true;
			if (code == KeyEvent.VK_A) leftKey = true;
			if (code == KeyEvent.VK_D) rightKey = true;
			
			
			//Attack Keys
			if (!attackHold && code == KeyEvent.VK_ENTER) {
				gp.player.attack = true; 
				attackHold = true;
				shotKey = true;
				if(gp.gameState == gp.playState) {
					if (gp.player.weapon) {
						gp.playSE(2);
					}
						
					if (!gp.player.weapon) {
						//gp.playSE(3);
					}
				}
				
			}

			//Options
				if(gp.gameState == gp.playState && code == KeyEvent.VK_ESCAPE) {
					gp.gameState = gp.optionState;
				}
				
				if(gp.gameState == gp.optionState) {
					optionState(code);
				}
			
			//Switching Weapons
			if (gp.gameState == gp.playState && code == KeyEvent.VK_SPACE) {
				gp.player.weapon = (gp.player.weapon) ? false : true;
			}
			
			
			//Pause State
			if (code == KeyEvent.VK_P) {
				gp.gameState = (gp.gameState == gp.playState) ? gp.pauseState : gp.playState;
			}
						
					
			//Instruction State
				if (gp.gameState == gp.instructionScreen && !titleInstruction) {					
					if(gp.gameState == gp.instructionScreen && code == KeyEvent.VK_B) {
						gp.gameState = gp.optionState;
						gp.playSE(9);
						}
					}
					
				
				
				else if (gp.gameState == gp.instructionScreen && titleInstruction) {	
					if(gp.gameState == gp.instructionScreen && code == KeyEvent.VK_B) {
						gp.gameState = gp.titleState;
						gp.playSE(9);
					}
				}
			
			//ButtonLayout State
				if (gp.gameState == gp.buttonLayoutScreen && !titleButtonLayout) {					
					if(gp.gameState == gp.buttonLayoutScreen && code == KeyEvent.VK_B) {
						gp.gameState = gp.optionState;
						gp.playSE(9);
						}
					}
					
				
				
				else if (gp.gameState == gp.buttonLayoutScreen && titleButtonLayout) {	
					if(gp.gameState == gp.buttonLayoutScreen && code == KeyEvent.VK_B) {
						gp.gameState = gp.titleState;
						gp.playSE(9);
					}
				}
				
			//LeaderBoard state
				if(gp.gameState == gp.leaderBoardScreen && code == KeyEvent.VK_B) {
					gp.gameState = gp.titleState;
					gp.playSE(9);
				}
			
			//Map State
			if (code == KeyEvent.VK_M) {
				gp.gameState = (gp.gameState == gp.playState) ? gp.mapState : gp.playState;
			}

		} 
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
		int code = e.getKeyCode();
		
		//Movement Keys
		if (code == KeyEvent.VK_W) upKey = false;
		if (code == KeyEvent.VK_S) downKey = false;
		if (code  == KeyEvent.VK_A) leftKey = false;	
		if (code == KeyEvent.VK_D) rightKey = false;;
		
		//Attack KEY
		if (code == KeyEvent.VK_ENTER) {gp.player.attack = false; shotKey = false; attackHold = false;} 		

		
	}
	
	public void titleState(int code) {
		gp.screen.command = (gp.screen.command != 0 && code == KeyEvent.VK_UP) ? gp.screen.command-1 : gp.screen.command;
		gp.screen.command = (gp.screen.command != 4 && code == KeyEvent.VK_DOWN) ? gp.screen.command+1 : gp.screen.command;
		
		// Play sound effect 1 when up or down key is pressed
	    if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN) {
	        gp.playSE(1);
	    }
		
		if (code == KeyEvent.VK_ENTER) {
			if (gp.screen.command == 0) {
				gp.retry();
			}
			if (gp.screen.command == 1) {
				gp.gameState = gp.instructionScreen;
				titleInstruction = true;
			}
			if (gp.screen.command == 2) {
				gp.gameState = gp.buttonLayoutScreen;
				titleButtonLayout = true;
			}
			if (gp.screen.command == 3) {
				gp.gameState = gp.leaderBoardScreen;
			}
			if (gp.screen.command == 4) {
				System.exit(0);
			}
			gp.screen.command = 0;
			gp.playSE(9);
		}
	}
	
	public void optionState(int code){
		gp.screen.command = (gp.screen.command != 0 && code == KeyEvent.VK_UP) ? gp.screen.command-1 : gp.screen.command;
		gp.screen.command = (gp.screen.command != 3 && code == KeyEvent.VK_DOWN) ? gp.screen.command+1 : gp.screen.command;
		
	    if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN) {
	        gp.playSE(1);
	    }
		
		if (code == KeyEvent.VK_ENTER) {
			if (gp.screen.command == 0) {
				gp.gameState = gp.instructionScreen;
				titleInstruction = false;
			}
			if (gp.screen.command == 1) {
				gp.gameState = gp.buttonLayoutScreen;
				titleButtonLayout = false;
			}
			if (gp.screen.command == 2) {
				gp.gameState = gp.playState;
			}
			if (gp.screen.command == 3) {
				gp.gameState = gp.titleState;
			}
			
			gp.playSE(9);
			gp.screen.command = 0;
		}
	}
	public void gameOver(int code) {
		gp.screen.command = (gp.screen.command != 0 && code == KeyEvent.VK_UP) ? gp.screen.command-1 : gp.screen.command;
		gp.screen.command = (gp.screen.command != 1 && code == KeyEvent.VK_DOWN) ? gp.screen.command+1 : gp.screen.command;
		
		if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN) {
	        gp.playSE(1);
	    }
		
		if (code == KeyEvent.VK_ENTER) {
			if (gp.screen.command == 0) {
				gp.retry();
			}
			if (gp.screen.command == 1) {
				gp.gameState = gp.titleState;
			}
			gp.playSE(9);
			gp.screen.command = 0;
		}
	}
	
	public void winnerState(int code) {
		gp.screen.command = (gp.screen.command != 0 && code == KeyEvent.VK_UP) ? gp.screen.command-1 : gp.screen.command;
		gp.screen.command = (gp.screen.command != 0 && code == KeyEvent.VK_DOWN) ? gp.screen.command+1 : gp.screen.command;
		
		if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN) {
	        gp.playSE(1);
	    }
		
		if (code == KeyEvent.VK_ENTER) {
			if (gp.screen.command == 0) {
				gp.gameState = gp.titleState;
			}
			gp.playSE(9);
			gp.screen.command = 0;
		}
	}
	
	
}