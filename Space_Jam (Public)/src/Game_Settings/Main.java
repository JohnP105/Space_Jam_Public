package Game_Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame{

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false); //FIXED WINDOW SIZE
		window.setTitle("SpaceJam");
		
		GamePanel run = new GamePanel();
		window.add(run);
		window.pack(); //Causes this Window to be sized to fit the preferred size and layouts of its subcomponents
		
		window.setLocationRelativeTo(null);	//Window displayed at the center of the screen
		window.setVisible(true);
		
		run.setUpGame();
		
		run.startGameThread();
		
	}
}