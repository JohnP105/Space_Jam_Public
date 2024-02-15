package Characters;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game_Settings.GamePanel;

public class MONSTER_Octopus extends CharacterProperties{
	
	public MONSTER_Octopus(GamePanel gp) {
		super(gp);
		
		name = "Octopus";
		speed = 2;
		fullHealth = 15;
		attackDamage = 1;
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
			walk1 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Octopus.png"));
			walk2 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Octopus2.png"));
		}catch(IOException e) { e.printStackTrace();}
	}
	
	@Override
    public void update() {
		// Call the update method from the parent class
        super.update(); 
    }
	

}
