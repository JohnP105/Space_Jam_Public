package Characters;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game_Settings.GamePanel;

public class Projectile extends CharacterProperties{
	GamePanel gp;

	public Projectile(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Laser";
		speed = 9;
		attackDamage = 1;
		alive = false;
		
		//So bullets won't appear at the screen forever
		//Meaning after certain amount of seconds, bullet will be gone if it didn't hit any objects 
		fullHealth = 80;
		health = fullHealth;
		
		blueBullet();
		
		hitArea = new Rectangle();
		hitArea.x = 15;
		hitArea.y = 15;
		hitArea.width = 50;
		hitArea.height = 50;
	}
	
	public void set(int mapX, int mapY, String direction, boolean alive) {
		this.mapX = mapX;
		this.mapY = mapY;
		this.direction = direction;
		this.health = this.fullHealth;
		this.alive = alive;
		gp.playSE(3);
	}
	

	public void update() {
		int idx = gp.checkCol.check_Player_Collision(this, gp.enemies);
		if (idx != 9999) {
			gp.playSE(6);
			gp.player.damageMonster(idx);
		}
		gp.checkCol.checkTile(this);
		
		if (collisionOn) {
			collisionOn = false;
			alive = false;
			return;
		}
		
		switch (direction) {
			case "up": mapY -= speed; break;
			case "down": mapY += speed; break;					
			case "left": mapX -= speed; break;
			case "right": mapX += speed; break;}
		
		//Remove bullet from screen once it reaches 0 health
		health--;
		if (health <= 0) {
			alive = false;
		}
		
		spriteCounter++;
		if (spriteCounter > 1) {
			if (spriteNum == 1) spriteNum = 2;
			else if (spriteNum == 2) spriteNum = 1;
			spriteCounter = 0;
		}
	}
	
	//Draw Projectiles
	public void draw(Graphics2D g2) {
		
		int screenX = mapX - gp.player.mapX + gp.player.screenX;
		int screenY = mapY - gp.player.mapY + gp.player.screenY;

		//Only draw the tiles around the player to improve efficiency
		if (mapX + gp.tileSize > gp.player.mapX - gp.player.screenX &&
			mapX - gp.tileSize < gp.player.mapX + gp.player.screenX && 
			mapY + gp.tileSize > gp.player.mapY - gp.player.screenY &&
			mapY - gp.tileSize < gp.player.mapY + gp.player.screenY
			) { 
			
			BufferedImage image = null;
			//Walk Image
			switch (direction) {
			case "up": image = (spriteNum == 1) ? upShoot1 : upShoot2; break;
			case "down": image = (spriteNum == 1) ? downShoot1 : downShoot2; break;
			case "left": image = (spriteNum == 1) ? leftShoot1 : leftShoot2; break;
			case "right": image = (spriteNum == 1) ? rightShoot1 : rightShoot2; break;} 
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}

	public void blueBullet() {
		try {
			upShoot1 = ImageIO.read(getClass().getResourceAsStream("/Blue_Bullets/upshoot1.png"));
			upShoot2 = ImageIO.read(getClass().getResourceAsStream("/Blue_Bullets/upshoot2.png"));	

			downShoot1 = ImageIO.read(getClass().getResourceAsStream("/Blue_Bullets/downshoot1.png"));
			downShoot2 = ImageIO.read(getClass().getResourceAsStream("/Blue_Bullets/downshoot2.png"));

			leftShoot1 = ImageIO.read(getClass().getResourceAsStream("/Blue_Bullets/leftshoot1.png"));
			leftShoot2 = ImageIO.read(getClass().getResourceAsStream("/Blue_Bullets/leftshoot2.png"));

			rightShoot1 = ImageIO.read(getClass().getResourceAsStream("/Blue_Bullets/rightshoot1.png"));
			rightShoot2 = ImageIO.read(getClass().getResourceAsStream("/Blue_Bullets/rightshoot2.png"));
			
		}catch(IOException e) { e.printStackTrace();}
	}

	public void redBullet() {
		try {
			upShoot1 = ImageIO.read(getClass().getResourceAsStream("/Red_Bullets/upshoot1.png"));
			upShoot2 = ImageIO.read(getClass().getResourceAsStream("/Red_Bullets/upshoot2.png"));	

			downShoot1 = ImageIO.read(getClass().getResourceAsStream("/Red_Bullets/downshoot1.png"));
			downShoot2 = ImageIO.read(getClass().getResourceAsStream("/Red_Bullets/downshoot2.png"));

			leftShoot1 = ImageIO.read(getClass().getResourceAsStream("/Red_Bullets/leftshoot1.png"));
			leftShoot2 = ImageIO.read(getClass().getResourceAsStream("/Red_Bullets/leftshoot2.png"));

			rightShoot1 = ImageIO.read(getClass().getResourceAsStream("/Red_Bullets/rightshoot1.png"));
			rightShoot2 = ImageIO.read(getClass().getResourceAsStream("/Red_Bullets/rightshoot2.png"));
			
		}catch(IOException e) { e.printStackTrace();}
		
	}

}
