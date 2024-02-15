package GameObject;

import java.io.IOException;

import javax.imageio.ImageIO;

public class G_Key extends SuperObject {
	
	
	public G_Key() {
		
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Key/Key.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		collision = false;
		
				
	}

}
