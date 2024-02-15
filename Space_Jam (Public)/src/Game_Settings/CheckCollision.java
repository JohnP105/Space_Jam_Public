package Game_Settings;

import java.util.ArrayList;

import Characters.CharacterProperties;

public class CheckCollision {
	GamePanel gp;
	public int tpCooldown;
	
	public CheckCollision(GamePanel gp) {
		this.gp = gp;
		tpCooldown = 0;
	}
	
	public void checkTile(CharacterProperties character) {
		int leftX = (character.mapX + character.hitArea.x);
		int rightX = (character.mapX + character.hitArea.x + character.hitArea.width);
		int topY = (character.mapY + character.hitArea.y);
		int bottomY = (character.mapY + character.hitArea.y + character.hitArea.height);
		
		int leftCol = leftX/gp.tileSize;
		int rightCol = rightX/gp.tileSize;
		int topRow = topY/gp.tileSize;
		int bottomRow = bottomY/gp.tileSize;
		
		int tileNum1 = 0, tileNum2 = 0;
		
		switch (character.direction) {
		case "up":
			topRow = (topY - character.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentRoom][leftCol][topRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentRoom][rightCol][topRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				character.collisionOn = true;
			}
			break;
		
		case "down":
			bottomRow = (bottomY + character.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentRoom][leftCol][bottomRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentRoom][rightCol][bottomRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				character.collisionOn = true;
			}
			break;
			
		case "left":
			leftCol = (leftX - character.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentRoom][leftCol][topRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentRoom][leftCol][bottomRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				character.collisionOn = true;
			}
			break;

		case "right":
			rightCol = (rightX + character.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentRoom][rightCol][topRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentRoom][rightCol][bottomRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				character.collisionOn = true;
			}
			break;
		}
		if (character == gp.player) {
			checkTptiles(character, tileNum1, tileNum2);
		}
	}
	
	//Check Player to Monster Collision
	public int check_Player_Collision(CharacterProperties PLAY, ArrayList<CharacterProperties> MON) {
		
		int idx = 9999;
		for (int i = 0; i < MON.size(); i++) {
			if (MON.get(i) != null) {
				//Store default values
				int hitArea_origX_PLAY = PLAY.hitArea.x;
				int hitArea_origY_PLAY = PLAY.hitArea.y;
				
				int hitArea_origX_MON = MON.get(i).hitArea.x;
				int hitArea_origY_MON = MON.get(i).hitArea.y;
				
				//Get Player Position
				PLAY.hitArea.x = PLAY.mapX + PLAY.hitArea.x;
				PLAY.hitArea.y = PLAY.mapY + PLAY.hitArea.y;
				
				//Get Monster Position
				MON.get(i).hitArea.x = MON.get(i).mapX + MON.get(i).hitArea.x;
				MON.get(i).hitArea.y = MON.get(i).mapY + MON.get(i).hitArea.y;
				
				switch(PLAY.direction) {
				case "up":
					PLAY.hitArea.y -= PLAY.speed;
					if (PLAY.hitArea.intersects(MON.get(i).hitArea)) {
						PLAY.collisionOn = true;
						idx = i;
					}
					break;
					
				case "down":
					PLAY.hitArea.y += PLAY.speed;
					if (PLAY.hitArea.intersects(MON.get(i).hitArea)) {
						PLAY.collisionOn = true;
						idx = i;
					}
					break;
					
				case "left":
					PLAY.hitArea.x -= PLAY.speed;
					if (PLAY.hitArea.intersects(MON.get(i).hitArea)) {
						PLAY.collisionOn = true;
						idx = i;
					}
					break;
					
				case "right":
					PLAY.hitArea.x += PLAY.speed;
					if (PLAY.hitArea.intersects(MON.get(i).hitArea)) {
						PLAY.collisionOn = true;
						idx = i;
					}
					break;		
				}
				
				PLAY.hitArea.x = hitArea_origX_PLAY;
				PLAY.hitArea.y = hitArea_origY_PLAY;
				
				MON.get(i).hitArea.x = hitArea_origX_MON;
				MON.get(i).hitArea.y = hitArea_origY_MON;	
			}
		}
		return idx;
	}
	
	//Check Monster to Player Collision
	public void check_Monster_Collision(CharacterProperties MON) {
		//Store default values
		int hitArea_origX_PLAY = gp.player.hitArea.x;
		int hitArea_origY_PLAY = gp.player.hitArea.y;
		
		int hitArea_origX_MON = MON.hitArea.x;
		int hitArea_origY_MON = MON.hitArea.y;
		
		//Get Monster Position
		MON.hitArea.x = MON.mapX + MON.hitArea.x;
		MON.hitArea.y = MON.mapY + MON.hitArea.y;
		
		//Get Monster Position
		gp.player.hitArea.x = gp.player.mapX + gp.player.hitArea.x;
		gp.player.hitArea.y = gp.player.mapY + gp.player.hitArea.y;
		
		switch(MON.direction) {
		case "up":
			MON.hitArea.y -= MON.speed;
			if (MON.hitArea.intersects(gp.player.hitArea)) {
				MON.collisionOn = true;
			}
			break;
			
		case "down":
			MON.hitArea.y += MON.speed;
			if (MON.hitArea.intersects(gp.player.hitArea)) {
				MON.collisionOn = true;
			}
			break;
			
		case "left":
			MON.hitArea.x -= MON.speed;
			if (MON.hitArea.intersects(gp.player.hitArea)) {
				MON.collisionOn = true;
			}
			break;
			
		case "right":
			MON.hitArea.x += MON.speed;
			if (MON.hitArea.intersects(gp.player.hitArea)) {
				MON.collisionOn = true;
			}
			break;	
		}
		
		gp.player.hitArea.x = hitArea_origX_PLAY;
		gp.player.hitArea.y = hitArea_origY_PLAY;
		
		MON.hitArea.x = hitArea_origX_MON;
		MON.hitArea.y = hitArea_origY_MON;
		
	}
	
	//Check for Teleport tiles
	public void checkTptiles(CharacterProperties character, int tile1, int tile2) {
		//5 seconds teleport cooldown
		if (tpCooldown > 1) {
			tpCooldown++;
			if (tpCooldown >= 300) tpCooldown = 0;
			return;
		}
		//Teleport to next room
		if (tile1 == 45 || tile2 == 45) {	
			gp.playSE(4);
			//Must collect all 4 keys in a room to move to the next one
			if (gp.Keys < (gp.currentRoom * 4) + 4) {
				return;
			}

			switch (gp.currentRoom) {
				case 0: gp.currentRoom = 1; break;
				case 1: gp.currentRoom = 2; break;
				case 2: gp.currentRoom = 3; break;
			}
			
			//Relocate player to new cords
			character.mapX = gp.mapWidth - (gp.tileSize * 9) - (gp.tileSize/2);
			character.mapY = gp.mapHeight - (gp.tileSize * 7) - (gp.tileSize/2);
			tpCooldown++;
			gp.monster.resetMonsters();
			gp.aSetter.setObject();
		}
		
		//Go back to previous room
		else if (tile1 == 41 || tile2 == 41) {
			//Must collect all 4 keys in a room to move to the next one
			if (gp.Keys < (gp.currentRoom * 4) + 4) {
				return;
			}

			switch (gp.currentRoom) {
				case 1: 
					gp.currentRoom = 0;
					//Relocate player to new cords
					character.mapX = 1999;
					character.mapY = 940;
					break;
					
				case 2: 
					gp.currentRoom = 1; 
					character.mapX = 3320;
					character.mapY =  1340;
					break;
				
				case 3: 
					gp.currentRoom = 2; 
					character.mapX = 2001;
					character.mapY = 619;
					break;
				case 0:
					if (gp.Keys == 16) {gp.gameState = gp.winnerState;}
					return;
			}
			tpCooldown++;
			gp.monster.resetMonsters();
			gp.aSetter.setObject();
		}
	}
	
	public int checkObject(CharacterProperties entity, boolean player) {
		int index= 9999;
		
		for(int j = 0; j < 4; j++) {
			int i = j + (gp.currentRoom * 4);
			
			if(gp.obj[i]!=null) {
				
				//Entity position
				entity.hitArea.x= entity.mapX + entity.hitArea.x;
				entity.hitArea.y=entity.mapY+entity.hitArea.y;
				//get object solid area position
				gp.obj[i].solidArea.x= gp.obj[i].mapX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y= gp.obj[i].mapY + gp.obj[i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					entity.hitArea.y-= entity.speed;
					if(entity.hitArea.intersects(gp.obj[i].solidArea)) {
						index = i;
					}
					break;
				case "down":
					entity.hitArea.y+=entity.speed;
					if(entity.hitArea.intersects(gp.obj[i].solidArea)) {
						index = i;
					}
					break;
				case "left":
					entity.hitArea.x-=entity.speed;
					if(entity.hitArea.intersects(gp.obj[i].solidArea)) {
						index = i;
					}
					break;
				case "right":
					entity.hitArea.x+=entity.speed;
					if(entity.hitArea.intersects(gp.obj[i].solidArea)) {
						index = i;
					}
					break;
				
				}
				entity.hitArea.x=entity.solidAreaDefaultX;
				entity.hitArea.y=entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x=gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y=gp.obj[i].solidAreaDefaultY;	
			}
		}
		
		return index;
	}
}
