package BackGround;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Game_Settings.GamePanel;

public class Tile {
	
	GamePanel gp;
	public Single_Tile[] tile;
	public int mapTileNum[][][]; //[Room Number][Col][Row]
	public int tileCount, spawnCount, roomCount, room2Count, room3Count;
	
	public Tile(GamePanel gp) {
		this.gp = gp;
		tile = new Single_Tile[50];
		mapTileNum = new int[gp.numRooms][gp.maxMapCol][gp.maxMapRow];
		tileCount = 0;
		spawnCount = 0;
		roomCount = 0;
		room2Count = 0;
		room3Count = 0;
		getTileImage();
		
		loadRoom("/Rooms/Earth.txt", 0);
		loadRoom("/Rooms/Mercury.txt", 1);
		loadRoom("/Rooms/Jupiter.txt", 2);
		loadRoom("/Rooms/Neptune.txt", 3);
	}

	
	public void getTileImage() {
		// true if block acts like a wall, otherwise false
		setUpTile(0, "/Tiles/0creepwall.png", false);
		setUpTile(1, "/Tiles/1blue.png", true);
		setUpTile(2, "/Tiles/2grate.png", true);
		setUpTile(3, "/Tiles/3box.png", true);
		setUpTile(4, "/Tiles/4green.png", true);
		setUpTile(5, "/Tiles/5grey.png", false);
		setUpTile(6, "/Tiles/6purplecreep.png", true);
		setUpTile(9, "/Tiles/9galaxy.png", true);
		
		
		//Estevahns tiles
		setUpTile(10, "/Estevahn_Tiles/galaxy.png", false);
		setUpTile(11, "/Estevahn_Tiles/blue.png", false);
		setUpTile(12, "/Estevahn_Tiles/green.png", true);
		setUpTile(13, "/Estevahn_Tiles/white.png", true);
		
		setUpTile(14, "/Tiles/14jupiter.png", true);
		setUpTile(15, "/Tiles/15jupiter.png", false);
		setUpTile(16, "/Tiles/16jupiter.png", true);
		setUpTile(17, "/Tiles/17jupiter.png", true);

		// Do not edit these tiles, they are the spawn tiles	
		setUpTile(35, "/teleTiles/Left1.png", false);
		setUpTile(36, "/teleTiles/Left2.png", false);
		setUpTile(37, "/teleTiles/Left3.png", false);
		
		setUpTile(38, "/teleTiles/Top1.png", false);
		setUpTile(39, "/teleTiles/Top2.png", false);
		setUpTile(40, "/teleTiles/Top3.png", false);
		
		setUpTile(41, "/teleTiles/Mid1.png", false);
		setUpTile(42, "/teleTiles/Mid2.png", false);
		setUpTile(43, "/teleTiles/Mid3.png", false);
		
		// Do not edit these tiles, they are the teleport tiles		
		setUpTile(45, "/teleTiles/TP1.png", false);
		setUpTile(46, "/teleTiles/TP2.png", false);
		setUpTile(47, "/teleTiles/TP3.png", false);
		setUpTile(48, "/teleTiles/TP4.png", false);
		setUpTile(49, "/teleTiles/TP5.png", false);
		
		//Fateh Tiles
		setUpTile(00,"/Tiles/Neptune.png",true);
		setUpTile(22,"/Tiles/Galaxy 1.png",false);
		
	}
	
	
	/*Initalize the tiles*/
	public void setUpTile(int index, String image, boolean collide) {
		try {
			tile[index] = new Single_Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream(image));
			tile[index].collision = collide;
			
			BufferedImage scale = new BufferedImage(gp.tileSize, gp.tileSize, tile[index].image.getType());
			Graphics2D g2 = scale.createGraphics();
			g2.drawImage(tile[index].image, 0, 0, gp.tileSize, gp.tileSize, null);
	
		}  catch(IOException e) { e.printStackTrace(); }
		
	}
	
	
	/* Load room based on the text file layout*/
	public void loadRoom(String filePath, int room) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxMapCol && row < gp.maxMapRow) {
				String line = br.readLine();
				
				while(col < gp.maxMapCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[room][col][row] = num;
					col++;
				}
				if (col == gp.maxMapCol) {
					col = 0;
					row++;
				}
			}	
			br.close();
			
		}  catch(Exception e) { e.printStackTrace(); }
	}
	
	
	public void draw(Graphics2D g2) {
		int mapCol = 0;
		int mapRow = 0;
		
		while (mapCol < gp.maxMapCol && mapRow < gp.maxMapRow) {
			int tileNum = mapTileNum[gp.currentRoom][mapCol][mapRow];
			
			int mapX = mapCol * gp.tileSize;
			int mapY = mapRow * gp.tileSize;
			int screenX = mapX - gp.player.mapX + gp.player.screenX;
			int screenY = mapY - gp.player.mapY + gp.player.screenY;

			//Only draw the tiles around the player to improve efficiency
			if (mapX + gp.tileSize > gp.player.mapX - gp.player.screenX &&
				mapX - gp.tileSize < gp.player.mapX + gp.player.screenX && 
				mapY + gp.tileSize > gp.player.mapY - gp.player.screenY &&
				mapY - gp.tileSize < gp.player.mapY + gp.player.screenY
				) { 
				
				//Teleport Animation
				if (tileNum == 45  || tileNum == 46 || tileNum == 47) {
					roomCount++;
					if (roomCount < 12) tileNum = 45;
					else if (roomCount < 24) tileNum = 46;
					else if (roomCount < 36) tileNum = 47;
					else if (roomCount < 48) tileNum = 48;
					else if (roomCount < 60) tileNum = 49;
					else roomCount = 0;
				}
				
				//Spawn Animation
				if (tileNum == 35 || tileNum == 38 || tileNum == 41) {
					spawnCount++;
				
					if (spawnCount < 60) {
						//do nothing
					} 
					else if (spawnCount < 80) tileNum++;
					else if (spawnCount < 100)tileNum += 2;
					else spawnCount = 0;
				}		
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			
			mapCol++;
			if (mapCol == gp.maxMapCol) {
				mapCol = 0;
				mapRow++;
			}
		}

	}
}
