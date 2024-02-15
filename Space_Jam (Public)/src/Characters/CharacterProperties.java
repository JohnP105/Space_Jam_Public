package Characters;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import Game_Settings.GamePanel;

/* Parent class that stores info for player characters, NPC's, enemies, etc...*/
public class CharacterProperties {
	GamePanel gp;
	
	//Character w/Sword Image
	public BufferedImage upSword1, upSword2, downSword1, downSword2, leftSword1, 
		leftSword2, rightSword1, rightSword2, upSwordAttack1, upSwordAttack2, 
		downSwordAttack1, downSwordAttack2, leftSwordAttack1, leftSwordAttack2, rightSwordAttack1, rightSwordAttack2;
	
	//Character w/Shoot Image
	public BufferedImage upShoot1, upShoot2, downShoot1, downShoot2, leftShoot1, 
		leftShoot2, rightShoot1, rightShoot2, upShootAttack1, upShootAttack2, 
		downShootAttack1, downShootAttack2, leftShootAttack1, leftShootAttack2, rightShootAttack1, rightShootAttack2;
	
	//Check to see if bullet should still be on the screen
	public boolean alive;
	
	//Location, speed, name, health for characters, hit status
	public int mapX, mapY;
	public int speed;
	public String name;
	public int fullHealth;
	public int health;
	public boolean hit;
	public int attackDamage;
	
	//Enemies Image, Settings
	public BufferedImage walk1, walk2;
	public int randomDirection = 0;
	
	//Animation for the characters
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public String direction;
	public int delayCounter = 0;
	
	//Collision Detection
	public Rectangle hitArea;
	public Rectangle attackArea;
	public int solidAreaDefaultX,solidAreaDefaultY;
	public boolean collisionOn = false;
	
	public Projectile projectile;
	
	
	public CharacterProperties(GamePanel gp) {
		this.gp = gp;
	}
	
	//Enemies Action
	public void setAction() {
		randomDirection++;
		if (randomDirection == 120) {
			Random random = new Random();
			int num = random.nextInt(100)+1; //pick number between 1 and 100
			
			if (num < 25) direction = "up";
			else if (num < 50) direction ="down";
			else if (num < 75) direction = "left";
			else direction = "right";
			randomDirection = 0;
		}
	}
	
	public void attackPlayer() {
		if (collisionOn && gp.player.hit == false) {
			gp.player.hit = true;
			gp.player.health = gp.player.health - attackDamage;
			gp.playSE(7);
			if (gp.player.health <= 0) {
				gp.player.death = true;
			}
		}
		
	}
	
	
	//Update Enemies
	public void update() {
		setAction();
		collisionOn = false;
		
		//Check Monster to Player Collision, if so Attack Player
		gp.checkCol.check_Monster_Collision(this);
		attackPlayer();
		
		//Check Tile Collision
		gp.checkCol.checkTile(this);
		
		//If collisionOn is false, character can move
		if (!collisionOn) {
			switch (direction) {
			case "up": mapY -= speed; break;
			case "down": mapY += speed; break;					
			case "left": mapX -= speed; break;	
			case "right": mapX += speed; break;
			}
		}
		spriteCounter++;
		if (spriteCounter > 12) {
			if (spriteNum == 1) spriteNum = 2;
			else if (spriteNum == 2) spriteNum = 1;
			spriteCounter = 0;
		}
		
		//Attack delay for Monster so player cannot fully spam attack
		if (hit) { delayCounter++;
			if (delayCounter > 20) {hit = false; delayCounter = 0;System.out.println("monster");}
			
		}
		
	}
	
	
	//Draw Enemies
	public void draw(Graphics2D g2) {
		
		int screenX = mapX - gp.player.mapX + gp.player.screenX;
		int screenY = mapY - gp.player.mapY + gp.player.screenY;

		//Only draw the tiles around the player to improve efficiency
		if (mapX + gp.tileSize > gp.player.mapX - gp.player.screenX &&
			mapX - gp.tileSize < gp.player.mapX + gp.player.screenX && 
			mapY + gp.tileSize > gp.player.mapY - gp.player.screenY &&
			mapY - gp.tileSize < gp.player.mapY + gp.player.screenY
			) { 
			
			//Walk Image
			BufferedImage image = null;
			image = (spriteNum == 1) ? walk1 : walk2; 
			
			if (hit) {g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));}
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			
			//Monster HP Bar
			double scale = (double)(gp.tileSize/fullHealth);
			double hpBar = scale * health;
			
			g2.setColor(new Color(35,35,35));
			g2.fillRect(screenX-1, screenY-16,gp.tileSize+2,12);
			
			g2.setColor(new Color(255,0,30));
			g2.fillRect(screenX, screenY-15, (int)hpBar, 10);
		}
	}
}
