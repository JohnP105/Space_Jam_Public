package GameObject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Game_Settings.GamePanel;

public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision=false;
	public int mapX, mapY;
	public Rectangle solidArea=new Rectangle(0,0,48,48);
	public int solidAreaDefaultX=0;
	public int solidAreaDefaultY=0;
	
	public void draw(Graphics2D g2, GamePanel gp) {
		
		int screenX = mapX - gp.player.mapX + gp.player.screenX;
		int screenY = mapY - gp.player.mapY + gp.player.screenY;

		//Only draw the tiles around the player to improve efficiency
		if (mapX + gp.tileSize > gp.player.mapX - gp.player.screenX &&
			mapX - gp.tileSize < gp.player.mapX + gp.player.screenX && 
			mapY + gp.tileSize > gp.player.mapY - gp.player.screenY &&
			mapY - gp.tileSize < gp.player.mapY + gp.player.screenY
			) { g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);}
	}
	
	
	

}