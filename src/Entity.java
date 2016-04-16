import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entity 
{
	private double x;
	private double y;
	private int health;
	String imagePath;
	
	
	public Entity(double xPos, double yPos, int health, String imagePath)
	{
		
	}
	public void paintComponent(Graphics g)//already happens forever and ever
	{
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(image, 0, 0, null);
	}
}