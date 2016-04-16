import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Slime extends Entity
{
	int size; //size of slime
	int resize; //size of sprite
	public Slime(double xPos, double yPos, int newSize) 
	{
		super(xPos, yPos, assignHealth(newSize), assignDamage(newSize), "slime" + assignName(newSize));
		size = newSize;
		//If slimes are smaller than 32 by 32, puts them in the center of the 32 by 32 square where they are
		//Also updates size the sprite is resized to to keep proportions consistent
		if(size == 2)
		{
			//64 = displacement needed to get medium slime in center
			updatePosition(getX() + 64, getY() + 64);
			resize = 128;
		}
		else if(size == 1)
		{
			//64 = displacement needed to get small slime in center
			updatePosition(getX() + 96, getY() + 96);
			resize = 64;
		}
		else
			resize = 256;
	}
	
	//Health, attack power, and image name based on size
	private static int assignHealth(int size)
	{
		if(size == 1)
			return 50;
		else if(size == 2)
			return 75;
		else
			return 100;
	}
	private static int assignDamage(int size)
	{
		if(size == 1)
			return 5;
		else if(size == 2)
			return 10;
		else
			return 15;
	}
	private static String assignName(int size)
	{
		if(size == 1)
			return "Small";
		else if(size == 2)
			return "Medium";
		else
			return "Large";
	}
	
	//Overridden from parent to resize slimes properly
	public void paintComponent(Graphics g)
	{
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/" + imagePath + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//multiplied by 256 to make image 8 times larger
		g.drawImage(image, (int) getX(), (int) getY(), resize, resize, null);
	}
}
