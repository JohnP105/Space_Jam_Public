package Game_Settings;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		soundURL[0]= getClass().getResource("/Music/orp.wav");
		soundURL[1]= getClass().getResource("/Sound/shot.wav");
		soundURL[2]= getClass().getResource("/Sound/swing.wav");
		soundURL[3]= getClass().getResource("/Sound/bassdown.wav");
		soundURL[4]= getClass().getResource("/Sound/resbass.wav");
		soundURL[5]= getClass().getResource("/Sound/echo.wav");
		soundURL[6]= getClass().getResource("/Sound/owie1.wav");
		soundURL[7]= getClass().getResource("/Sound/owie2.wav");
		soundURL[8]= getClass().getResource("/Sound/8down.wav");
		soundURL[9]= getClass().getResource("/Sound/revbling.wav");
		soundURL[10]= getClass().getResource("/Sound/celebrate.wav");
		//soundURL[11]= getClass().getResource("/Sound/Ahh Yeah.mp3");
	}
	
	public void setFile(int i){
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}catch(Exception e){
			
		}
	}
	
	public void play() {
		//clip.stop();
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}
