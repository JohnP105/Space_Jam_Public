package Characters;

import java.util.Random;

import Game_Settings.GamePanel;

public class Set_Enemies {
	
	GamePanel gp;
	
	public Set_Enemies(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setMonsters() {
		if (gp.enemies.size() >= 150) {
			return;
		}
		
		Random random = new Random();
		int numEnemies = (gp.Wave == 1) ? 20 : 15;
		if (gp.Wave >= 4) numEnemies = 15;
		
		
		for (int i = 0; i < numEnemies; i++) {
			
			//Generate random Location for Enemy Spawn
			int x = random.nextInt(36) + 1;
			int y = random.nextInt(30) + 1;
			int tileNum = gp.tileM.mapTileNum[gp.currentRoom][x][y];
			
			//If the enemy spawns at a tile where it is set to a collision, it will get stuck there
			//Therefore, new random location will be generate until enemy can spawn at a location that it can move
			while (gp.tileM.tile[tileNum].collision == true) {
				x = random.nextInt(36) + 1;
				y = random.nextInt(30) + 1;
				tileNum = gp.tileM.mapTileNum[gp.currentRoom][x][y];
			}
			
			//Octopus 
			if (i < (numEnemies/3)) {
				MONSTER_Octopus enemy = new MONSTER_Octopus(gp);
				enemy.mapX = gp.tileSize * x;
				enemy.mapY = gp.tileSize * y;
				gp.enemies.add(enemy);
			}
			
			//DocOc
			else if(i < (2 * numEnemies/3)) {
				DocOc enemy = new DocOc(gp);
				enemy.mapX = gp.tileSize * x;
				enemy.mapY = gp.tileSize * y;
				gp.enemies.add(enemy);
			}
			
			//Aliens
			else {
				Alien enemy = new Alien(gp);
				enemy.mapX = gp.tileSize * x;
				enemy.mapY = gp.tileSize * y;
				gp.enemies.add(enemy);	
			}
		}
	}
	
	
	//Reset monster locations after changing room
	public void resetMonsters() {
		int numEnemies = gp.enemies.size();
		Random random = new Random();

		for (int i = 0; i < numEnemies; i++) {
			gp.enemies.remove(0);
			
			//Generate random Location for Enemy Spawn
			int x = random.nextInt(36) + 1;
			int y = random.nextInt(30) + 1;
			int tileNum = gp.tileM.mapTileNum[gp.currentRoom][x][y];
			
			//If the enemy spawns at a tile where it is set to a collision, it will get stuck there
			//Therefore, new random location will be generate until enemy can spawn at a location that it can move
			while (gp.tileM.tile[tileNum].collision == true) {
				x = random.nextInt(36) + 1;
				y = random.nextInt(30) + 1;
				tileNum = gp.tileM.mapTileNum[gp.currentRoom][x][y];
			}
			
			//Octopus 
			if (i < (numEnemies/3)) {
				MONSTER_Octopus enemy = new MONSTER_Octopus(gp);
				enemy.mapX = gp.tileSize * x;
				enemy.mapY = gp.tileSize * y;
				gp.enemies.add(enemy);
			}
			
			//DocOc
			else if(i < (2 * numEnemies/3)) {
				DocOc enemy = new DocOc(gp);
				enemy.mapX = gp.tileSize * x;
				enemy.mapY = gp.tileSize * y;
				gp.enemies.add(enemy);
			}
			
			//Aliens
			else {
				Alien enemy = new Alien(gp);
				enemy.mapX = gp.tileSize * x;
				enemy.mapY = gp.tileSize * y;
				gp.enemies.add(enemy);	
			}

		}
		
	}

}
