package Game_Settings;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.Timer;

import BackGround.Map;
import BackGround.Tile;
import Characters.CharacterProperties;
import Characters.Player;
import Characters.Projectile;
import Characters.Set_Enemies;
import GameObject.SuperObject;
//import Collision.CheckCollision;
import GameObject.SuperObject;

public class GamePanel extends JPanel implements Runnable{
	
	//SCREEN SETTINGS
	final int originalTileSize = 20; //20x20 tile size ~ default size of the characters, npc and objects of the game 
	final int scale = 4;  //scale the tile size...
	
	public final int tileSize = originalTileSize * scale; //80 x 80 tile size displayed on game screen
	public final int maxScreenRow = 9;
	public final int maxScreenCol = 14;
	public final int numRooms = 4;
	public int currentRoom = 0;
	
	public final int screenWidth = tileSize * maxScreenCol; //1,120 pixels
	public final int screenHeight = tileSize * maxScreenRow; //720 pixels
	
	//MAP SETTINGS
	public final int maxMapRow = 40;
	public final int maxMapCol = 50;
	public final int mapWidth = tileSize * maxMapCol;
	public final int mapHeight = tileSize * maxMapRow;
			
	//FPS
	public final int FPS = 60;
	
	//Key Handler
	public KeyHandler keyH = new KeyHandler(this);
	
	//Background and Tile
	public Tile tileM = new Tile(this);
	
	//Characters
	public Player player = new Player(this, keyH);
	
	//ENEMIES
	public ArrayList<CharacterProperties> enemies = new ArrayList<>();
	
	//Projectiles
	public ArrayList<Projectile> projectileList = new ArrayList<>();
	
	//SYSTEM
	public Screen screen = new Screen(this);
	public CheckCollision checkCol = new CheckCollision(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public Set_Enemies monster = new Set_Enemies(this);
	Map map = new Map(this);
	Thread gameThread;
	Sound sound = new Sound();
	Sound sound2 = new Sound();
	
	//Key //How much objects you want in the game 
	public SuperObject obj[]= new SuperObject[16];
	
	//Game State
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int mapState = 3;
	public final int instructionScreen = 4;
	public final int buttonLayoutScreen = 5;
	public final int gameOver = 6;
	public final int optionState = 7;
	public final int winnerState = 8;
	public final int leaderBoardScreen = 9;
	
	//Wave
	public int waveCount = 0;
	public int Wave = 0;
	public boolean waveState = false;
	
	//Show How to win the game
	public boolean winCondition = false;
	public int winCcount = 0;
	
	public int Score = 0 ;
	public int Keys = 0;
	public boolean[] collectKeys = new boolean[16];
	
	//leaderBoard
	private ArrayList<String> leaderboardTimes = new ArrayList<>();
	
	//Timer
	int elapsedTime = 0;
	int seconds = 0;
	int minutes = 0;
	int hours = 0;
	boolean started = false;
	String s = String.format("%02d", seconds);
	String m = String.format("%02d", minutes);
	
	Timer timer = new Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			timerStat();
			elapsedTime=elapsedTime+1000;
			hours = (elapsedTime/3600000);
			minutes = (elapsedTime/60000) % 60;
			seconds = (elapsedTime/1000) % 60;
			s = String.format("%02d", seconds);
			m = String.format("%02d", minutes);
		}
	});
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true); //Game Panel can focus to receive key inputs
		this.leaderboardTimes = new ArrayList<>();
		setUpGame();
		readTimesFromFile();
		playMusic(0);
	}
	
	
	public void setUpGame() {
		gameState = titleState;
		//Call setObject
		for (int i = 0; i < 16; i++) {collectKeys[i] = false;}
		aSetter.setObject();
		
		screen.setLeaderboardTimes(leaderboardTimes);
	}
	
	public void retry() {
		gameState = playState;
		Wave = 0;
		currentRoom = 0;
		Score = 0;
		Keys = 0;
		enemies.clear();
		for (int i = 0; i < 16; i++) {collectKeys[i] = false;}
		aSetter.setObject();

		timer.stop();
		elapsedTime=0;
		seconds =0;
		minutes=0;
		hours=0;
		s = String.format("%02d", seconds);
		m = String.format("%02d", minutes);     
		
		player.playerRetry();
		stopMusic();
		playMusic(0);
	}
	
	public void run() {
		double drawInterval = 1000000000 / FPS; // Draw screen 0.01666 per seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currTime;
			
		while(gameThread != null) {
			currTime = System.nanoTime();
			
			delta += (currTime - lastTime)/ drawInterval;
			lastTime = currTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	
	public void timerStat() {
		if(gameState == playState && !timer.isRunning()) {
			timer.start();
			System.out.print("Game Started!!! \n");
		}
		if(gameState == winnerState && timer.isRunning()) {
			timer.stop();
			System.out.print("You Wonnnn!!!! \n");
			System.out.print(m+":"+s);
			
			writeTimeToFile();
		}
		if(gameState == pauseState || gameState == optionState) {
			timer.stop();
			System.out.print("You Paused the game :(");
			//writeTimeToFile();
		}
	}
	
	private void writeTimeToFile() {
		 String directoryPath = "res/LeaderBoard";
		   
		    String fileName = "time.txt";

		    File directory = new File(directoryPath);
		    File file = new File(directory, fileName);

		    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
		        writer.write(m + ":" + s + "\n");
		        System.out.println("Time successfully written to file at: " + file.getAbsolutePath());
		    } catch (IOException e) {
		        e.printStackTrace();
		        System.err.println("Error writing time to file: " + e.getMessage());
		    }
		    System.out.println("Read times from file: " + leaderboardTimes);
	}
	
	private void readTimesFromFile() {
		 String directoryPath = "res/LeaderBoard";
		   
		    String fileName = "time.txt";
		    File file = new File(directoryPath, fileName);
		    
		    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                leaderboardTimes.add(line);
	            }

	           
	            Collections.sort(leaderboardTimes);
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.err.println("Error reading times from file: " + e.getMessage());
	        }
	}
	
	public void update() {
		
		if (gameState == playState) {
			timerStat();
			waveCount++;
			if (waveState) {
				return;
			}
				
			if ( (waveCount == 1200) || Wave == 0) {
				Wave++;
				waveCount = 0;
				waveState = true;
				monster.setMonsters();
				return;
			}
			
			//Player
			player.update();
			
			//Enemies
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).update();
			}
			
			//Projectile List
			for (int i = 0; i < projectileList.size(); i++) {
		
				if (projectileList.get(i).alive == true) {
					
					if (projectileList.get(i).health == 80) {
						projectileList.get(i).blueBullet();
						player.blueBullet = true;
					}
					
					//Bullets are stronger when there are more distance covered
					if (projectileList.get(i).health == 50) {
						projectileList.get(i).redBullet();
						player.blueBullet = false;
					}
					projectileList.get(i).update();
				}
				else {
					projectileList.remove(i);
				}
			}
		}


	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
	
		
		//Title Screen or Instruction Screen or ButtonLayout Screen
		if (gameState == titleState || gameState == instructionScreen || 
				gameState == buttonLayoutScreen || gameState == gameOver || gameState == optionState) {
			screen.draw(g2);
		}
		
		//Map Screen
		else if(gameState == mapState) {
			map.drawMapScreen(g2);
		}
	
		//Play State // Wave State
		else {
			//Tile
			tileM.draw(g2);
			

			//Object Key
			for(int i = 0; i < 4; i++) {
				int idx = (currentRoom * 4) + i;
				if(obj[idx]!=null) {
					obj[idx].draw(g2, this);
				}
			}
			
					
			//Player
			player.draw(g2);
			
			//MiniMap
			map.drawMiniMap(g2);
			
			//Enemies
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).draw(g2);
			}
			
			//Projectile List
			for (int i = 0; i < projectileList.size(); i++) {
				if (projectileList.get(i).alive == true) {
					projectileList.get(i).draw(g2);
				}
			}
			
			//Screen
			screen.draw(g2);
			
			
		}
		g2.dispose(); //Good for saving memory
}
	
	
	public void startGameThread() {
		gameThread = new Thread(this); //Passing Game Panel as the parameter
		gameThread.start();
		
	}
	
	
	
	public void playMusic(int i) {
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	
	public void stopMusic() {
		sound.stop();
	}
	
	public void playSE(int i) {
		sound2.setFile(i);
		//sound.stop();
		sound2.play();
	}


	
}
