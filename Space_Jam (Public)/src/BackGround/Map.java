package BackGround;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game_Settings.GamePanel;

public class Map extends Tile{
	GamePanel gp;
	BufferedImage worldMap[];
	public boolean miniMapOn = true;
	
	public Map(GamePanel gp) {
		super(gp);
		this.gp = gp;	
		createWorldMap();
	}
	
	
	public void createWorldMap() {
		worldMap = new BufferedImage[gp.numRooms];
		int mapWidth = gp.tileSize * gp.maxMapCol;
		int mapHeight = gp.tileSize * gp.maxMapRow;
				
		for (int i = 0; i < gp.numRooms; i++) {
			worldMap[i] = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D)worldMap[i].createGraphics();
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxMapCol && row < gp.maxMapRow) {
				int tileNum = mapTileNum[i][col][row];
				int x = gp.tileSize * col;
				int y = gp.tileSize * row;
				g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
				
				col++; 
				if (col == gp.maxMapCol) {
					col = 0;
					row++;
				}
			}
			
		}
	}	

	public void drawMapScreen(Graphics2D g2) {
		//Background Color
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		//Draw Map
		int width = gp.screenWidth;
		int height = gp.screenHeight;
		g2.drawImage(worldMap[gp.currentRoom], 0, 0, width, height, null);
		
		//Draw Player
		double scaleX = (double)(gp.tileSize * gp.maxMapCol)/width;
		double scaleY = (double)(gp.tileSize * gp.maxMapRow)/height;
		int playerX = (int)(gp.player.mapX/(scaleX));
		int playerY = (int)(gp.player.mapY/scaleY);
		int playerSize = (int)(gp.tileSize/scaleX-1);
		
		BufferedImage image = (gp.player.weapon) ? gp.player.downSword1 : gp.player.downShoot1;
		g2.drawImage(image, playerX, playerY, playerSize, playerSize, null);
		
		//Draw Enemies
		for (int i = 0; i < gp.enemies.size(); i++) {
			int enemyX = (int)(gp.enemies.get(i).mapX/(scaleX));
			int enemyY = (int)(gp.enemies.get(i).mapY/scaleY);
			image = gp.enemies.get(i).walk1;
			g2.drawImage(image, enemyX, enemyY, playerSize, playerSize, null);
		}
		
		//Draw Keys
		for (int i = 0; i < gp.obj.length; i++) {
			if (gp.obj[i] != null) {
				int x = (int)(gp.obj[i].mapX/(scaleX));
				int y = (int)(gp.obj[i].mapY/scaleY);
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/Key/Key.png"));
				} catch (IOException e) { e.printStackTrace();}
				g2.drawImage(image, x, y, playerSize, playerSize, null);
			}
		}
	}
	
	
	public void drawMiniMap(Graphics2D g2) {
		if (miniMapOn) {
			//Draw Map
			int width = 300;
			int height = 225;
			int x = gp.screenWidth - width - gp.tileSize/3;
			int y = gp.tileSize/3;
			
			//Make it transparent
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g2.drawImage(worldMap[gp.currentRoom], x, y, width, height, null);
			
			//Draw Player
			double scaleX = (double)(gp.tileSize * gp.maxMapCol)/width;
			double scaleY = (double)(gp.tileSize * gp.maxMapRow)/height;
			int playerX = (int)(x - 2 + gp.player.mapX/scaleX);
			int playerY = (int)(y + gp.player.mapY/scaleY);
			int playerSize = (int)(gp.tileSize/((scaleX + scaleY)/4));
			
			BufferedImage image = (gp.player.weapon) ? gp.player.downSword1 : gp.player.downShoot1;
			g2.drawImage(image, playerX, playerY, playerSize, playerSize, null);
			
			//Draw Enemies
			for (int i = 0; i < gp.enemies.size(); i++) {
				int enemyX = (int)(x - 2 + gp.enemies.get(i).mapX/(scaleX));
				int enemyY = (int)(y + gp.enemies.get(i).mapY/scaleY);
				image = gp.enemies.get(i).walk1;
				g2.drawImage(image, enemyX, enemyY, playerSize, playerSize, null);
				
			}
			
			//Make it transparent
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			
		}
	}
}
