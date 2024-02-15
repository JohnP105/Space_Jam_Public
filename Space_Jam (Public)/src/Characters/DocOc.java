package Characters;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game_Settings.GamePanel;

public class DocOc extends CharacterProperties{

	public DocOc(GamePanel gp) {
		super(gp);
		
		name = "Doc Oc";
		speed = 3;
		fullHealth = 15;
		attackDamage = 2;
		health = fullHealth;
		hit = false;
		direction = "down";
		
		hitArea = new Rectangle();
		hitArea.x = 10;
		hitArea.y = 10;
		hitArea.width = 60;
		hitArea.height = 60;
		
		getImage();
	}
	
	public void getImage() {
		try {
			walk1 = ImageIO.read(getClass().getResourceAsStream("/Enemies/DO1.png"));
			walk2 = ImageIO.read(getClass().getResourceAsStream("/Enemies/DO2.png"));
		}catch(IOException e) { e.printStackTrace();}
	}
	
	@Override
    public void update() {
		// Call the update method from the parent class
        super.update(); 
    }
}
