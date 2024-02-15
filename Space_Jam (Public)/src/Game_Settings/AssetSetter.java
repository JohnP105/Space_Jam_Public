package Game_Settings;

import java.util.Random;

import GameObject.G_Key;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		Random random = new Random();
		
		for (int i = 0; i < 4; i++) {
			int idx = (gp.currentRoom * 4) + i;
			
			//If Key has been collected, skip
			if (gp.collectKeys[idx]) {
				continue;
			}
			
			//Generate random Location for Key Spawn
			int x = random.nextInt(36) + 1;
			int y = random.nextInt(30) + 1;
			int tileNum = gp.tileM.mapTileNum[gp.currentRoom][x][y];
			
			//Key must not spawn in an unreachable state
			while (gp.tileM.tile[tileNum].collision == true) {
				x = random.nextInt(36) + 1;
				y = random.nextInt(30) + 1;
				tileNum = gp.tileM.mapTileNum[gp.currentRoom][x][y];
			}
			
			gp.obj[idx]=new G_Key();
			gp.obj[idx].mapX = gp.tileSize * x;
			gp.obj[idx].mapY = gp.tileSize * y;
			
		}
	}
}
