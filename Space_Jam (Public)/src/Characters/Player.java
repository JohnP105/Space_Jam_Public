package Characters;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game_Settings.GamePanel;
import Game_Settings.KeyHandler;

public class Player extends CharacterProperties{
	
	GamePanel gp;
	KeyHandler keyH;
	
	//Character's death
	public BufferedImage death1, death2, death3, death4;
	public boolean death;
	
	//true if player is holding a sword, false if a gun
	public boolean weapon; 
	public boolean blueBullet;
	
	//Action, false if player is just walking, true if player is attacking
	public boolean attack;

	//The coordinates that will be displayed in the game
	public final int screenX;
	public final int screenY;

	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		
		hitArea = new Rectangle();
		hitArea.x = 13;
		hitArea.y = 13;
		hitArea.width = 54;
		hitArea.height = 54;
		
		//Area of the player for key interaction
		
		solidAreaDefaultX=hitArea.x;
		solidAreaDefaultY=hitArea.y;
		
		attackArea = new Rectangle();
		attackArea.width = 60;
		attackArea.height = 60;
		
		blueBullet = true;
		death = false;
		
		setDefaultValues();
		Player_Sword_Images();	
		Player_Shoot_Images();
		Player_Death_Images();
	}
	
	public void playerRetry() {
		//Restore Position
		mapX = gp.mapWidth - (gp.tileSize * 9) - (gp.tileSize/2);
		mapY =  gp.mapHeight - (gp.tileSize * 7) - (gp.tileSize/2);
		direction = "down";
		
		//Restore Life
		health = fullHealth;
		hit = false;
		
		//Restore Weapon
		weapon = true;
	}
	
	public void setDefaultValues() {
		mapX = gp.mapWidth - (gp.tileSize * 9) - (gp.tileSize/2);
		mapY =  gp.mapHeight - (gp.tileSize * 7) - (gp.tileSize/2);
		speed = 8;
		fullHealth = 60;
		health = fullHealth;
		weapon = true;
		attack = false;
		hit = false;
		direction = "down"; //Any direction is fine
		name = "MC";
		projectile = new Projectile(gp);
	}
	
	public void Player_Death_Images() {
		try {
			death1 = ImageIO.read(getClass().getResourceAsStream("/playerDeath/deathanimation0.png"));
			death2 = ImageIO.read(getClass().getResourceAsStream("/playerDeath/deathanimation1.png"));
			death3 = ImageIO.read(getClass().getResourceAsStream("/playerDeath/deathanimation2.png"));
			death4 = ImageIO.read(getClass().getResourceAsStream("/playerDeath/deathanimation3.png"));
		}catch(IOException e) { e.printStackTrace();}
	}
	
	public void Player_Sword_Images() {
		try {
			upSword1 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/up1.png"));
			upSword2 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/up2.png"));
			upSwordAttack1 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/upSwing1.png"));
			upSwordAttack2 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/upSwing2.png"));	
	
			downSword1 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/down1.png"));
			downSword2 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/down2.png"));
			downSwordAttack1 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/downSwing1.png"));
			downSwordAttack2 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/downSwing2.png"));
	
			leftSword1 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/left1.png"));
			leftSword2 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/left2.png"));
			leftSwordAttack1 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/leftSwing1.png"));
			leftSwordAttack2 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/leftSwing2.png"));
	
			rightSword1 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/right1.png"));
			rightSword2 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/right2.png"));
			rightSwordAttack1 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/rightSwing1.png"));
			rightSwordAttack2 = ImageIO.read(getClass().getResourceAsStream("/Player_Sword/rightSwing2.png"));
			
		}catch(IOException e) { e.printStackTrace();}
	}
	
	
	public void Player_Shoot_Images() {
		try {
			upShoot1 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/up1.png"));
			upShoot2 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/up2.png"));
			upShootAttack1 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/upshoot1.png"));
			upShootAttack2 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/upshoot2.png"));	

			downShoot1 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/down1.png"));
			downShoot2 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/down2.png"));
			downShootAttack1 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/downshoot1.png"));
			downShootAttack2 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/downshoot2.png"));

			leftShoot1 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/left1.png"));
			leftShoot2 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/left2.png"));
			leftShootAttack1 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/leftshoot1.png"));
			leftShootAttack2 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/leftshoot2.png"));

			rightShoot1 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/right1.png"));
			rightShoot2 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/right2.png"));
			rightShootAttack1 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/rightshoot1.png"));
			rightShootAttack2 = ImageIO.read(getClass().getResourceAsStream("/Player_Shoot/rightshoot2.png"));
			
		}catch(IOException e) { e.printStackTrace();}
	}
	
	
	//Player attack Monster
	public void damageMonster(int idx) {
		//Damage with sword ~ does 4 health damage
		if (idx >= 0 && idx < gp.enemies.size()) {
			int damage = 8;

			
			//Damage with Gun ~ blue bullet does 36health damage, red bullet does 20 health damage
			if (!weapon && blueBullet){ damage = 6;}
			else if (!weapon && !blueBullet){ damage = 20;}
			
			if (gp.enemies.get(idx).hit == false) {
				gp.enemies.get(idx).health -= damage;
				gp.playSE(6);
				gp.enemies.get(idx).hit = true;
				gp.enemies.get(idx).randomDirection = 119;
				
				if (gp.enemies.get(idx).health <= 0) {
					gp.enemies.remove(idx);
					gp.playSE(8);
					gp.Score++;
				}

			}
		}
	}
	
	public void attacking() {
		spriteCounter++;
		
		if (spriteCounter < 5) spriteNum = 1;
		
		else if (spriteCounter < 25) {
			spriteNum = 2;
						
			int curMapX = mapX;
			int curMapY = mapY;
			int hitX = hitArea.width; 
			int hitY = hitArea.height;
			
			//Attack
			switch (direction) {
			case "up": mapY -= attackArea.height; break;
			case "down": mapY += attackArea.height; break;
			case "left": mapX -= attackArea.width; break;
			case "right": mapX += attackArea.width; break;}
			
			hitArea.width = attackArea.width;
			hitArea.height = attackArea.height;
			
			//Check if sword hit enemy
			if (weapon) {
				int monsterIndex = gp.checkCol.check_Player_Collision(this, gp.enemies);
				if (monsterIndex != 9999) damageMonster(monsterIndex);
			}
			
			mapX = curMapX;
			mapY = curMapY;
			hitArea.width = hitX;
			hitArea.height = hitY;
		}
		
		else if (spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attack = false;
		}
	}
	
	
	public void update() {
		if (death) {
			//death Animation
			spriteCounter++;
			if (spriteCounter == 120) {
				gp.playSE(5);
				gp.Score=0;
				death = false;
				gp.gameState = gp.gameOver;
			}
			return;
		}
		
		if (attack) attacking();
		
		else if(keyH.upKey == true || keyH.downKey == true || keyH.leftKey == true || keyH.rightKey == true) {
			if (keyH.upKey == true) direction = "up";				
			else if (keyH.downKey == true) direction = "down";	
			else if (keyH.leftKey == true) direction = "left";
			else if (keyH.rightKey == true) direction = "right";
			
			//Check Tile Collision
			collisionOn = false;
 			gp.checkCol.checkTile(this);
 			
 			//Check Player to Monster Collision
 			gp.checkCol.check_Player_Collision(this, gp.enemies);
 			
 			// CHECK OBJECT
 			int objIndex= gp.checkCol.checkObject(this,true);
 			if (objIndex != 9999) {
 				gp.obj[objIndex] = null;
 				gp.collectKeys[objIndex] = true;
 				gp.Keys++;
 				gp.playSE(9);
 
 				if (gp.Keys==16) {gp.stopMusic();gp.playSE(10);gp.winCondition = true;}
 			}
 		
 			//If collisionOn is false, player can move
 			if (!collisionOn) {
 				switch (direction) {
 				case "up": mapY -= speed; break;
 				case "down": mapY += speed; break;					
 				case "left": mapX -= speed; break;
 				case "right": mapX += speed; break;}
 			}
 			attack = false;
 			
			//Simulate walking animation by alternating 2 image every 12 frames
			spriteCounter++;
			if (spriteCounter > 12) {
				if (spriteNum == 1) spriteNum = 2;
				else if (spriteNum == 2) spriteNum = 1;
				spriteCounter = 0;
			}
			
			//Everytime Player gets damaged, he becomes invincible for a few seconds
			if (hit) { delayCounter++;
				if (delayCounter > 80) {hit = false; delayCounter = 0;System.out.println("player");}
			}
		}
		
		if (weapon == false && gp.keyH.shotKey && projectile.alive == false) {
			//Set location of bullets
			projectile.set(mapX, mapY, direction, true);
			
			//Add it to projectile list
			gp.projectileList.add(projectile);	
			gp.keyH.shotKey = false;
		}
	}
	

	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		//Player is walking
		if (!attack) {
			//Use sword image
			if (weapon) {
				switch (direction) {
				case "up": image = (spriteNum == 1) ? upSword1 : upSword2; break;
				case "down": image = (spriteNum == 1) ? downSword1 : downSword2; break;
				case "left": image = (spriteNum == 1) ? leftSword1 : leftSword2; break;
				case "right": image = (spriteNum == 1) ? rightSword1 : rightSword2; break;}
			}
			
			//Use shoot image
			else {
				switch (direction) {
				case "up": image = (spriteNum == 1) ? upShoot1 : upShoot2; break;
				case "down": image = (spriteNum == 1) ? downShoot1 : downShoot2; break;
				case "left": image = (spriteNum == 1) ? leftShoot1 : leftShoot2; break;
				case "right": image = (spriteNum == 1) ? rightShoot1 : rightShoot2; break;}
			}
		}
		
		//Player is Attacking
		else {
			//Sword Attack Image
			if (weapon) {
				switch (direction) {
				case "up": image = (spriteNum == 1) ? upSwordAttack1 : upSwordAttack2; break;
				case "down": image = (spriteNum == 1) ? downSwordAttack1 : downSwordAttack2; break;
				case "left": image = (spriteNum == 1) ? leftSwordAttack1 : leftSwordAttack2; break;
				case "right": image = (spriteNum == 1) ? rightSwordAttack1 : rightSwordAttack2; break;}
			}
			
			//Shoot Attack Image
			else {
				switch (direction) {
				case "up": image = (spriteNum == 1) ? upShootAttack1 : upShootAttack2; break;
				case "down": image = (spriteNum == 1) ? downShootAttack1 : downShootAttack2; break;
				case "left": image = (spriteNum == 1) ? leftShootAttack1 : leftShootAttack2; break;
				case "right": image = (spriteNum == 1) ? rightShootAttack1 : rightShootAttack2; break;}
			}
		}
		if (hit) {g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));}
		
		//Death Animation
		if (death) {
			if (spriteCounter < 30) {image = death1;}
			else if (spriteCounter < 60) {image = death2;}
			else if (spriteCounter < 90) {image = death3;}
			else if (spriteCounter < 120) {image = death4;}
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		//Player HP Bar
		double scale = ((double)gp.tileSize) / ((double)fullHealth);
		double hpBar = scale * health;
		
		g2.setColor(Color.white);
		g2.fillRect(screenX-2, screenY-16,gp.tileSize+2,12);
		
		g2.setColor(Color.cyan);
		g2.fillRect(screenX, screenY-15, (int)(hpBar), 10);
	}
}








