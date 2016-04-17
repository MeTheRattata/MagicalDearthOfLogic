import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Slime extends Entity
{
	int size;
	int resize; //size of sprite
	int attackPower;
	public Slime(double xPos, double yPos, int newSize) 
	{
		super(xPos, yPos, assignHealth(newSize), "");
		size = newSize;
		attackPower = getPower(size);
		setName(getName(size, getHealth(), getMaxHealth()));
		//If slimes are smaller than 32 by 32, puts them in the center of the 32 by 32 square where they are
		//Also updates size the sprite is resized to to keep proportions consistent
		if(size == 2)
			resize = 64;
		else if(size == 1)
			resize = 32;
		else
			resize = 128;
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
	private int getPower(int size)
	{
		if(size == 1)
			return 5;
		else if(size == 2)
			return 10;
		else
			return 15;
	}
	private String getName(int size, int health, int maxHealth)
	{
		String restOfName = "slime/slime";
		if(size == 1)
			restOfName += "Small";
		else if(size == 2)
			restOfName += "Medium";
		else
			restOfName += "Large";
		
		if((((double) health / (double) maxHealth) * 100) > 75) //health is above 75%
			restOfName += "100";
		else if((((double) health / (double) maxHealth) * 100) > 50)//health is above 50%
			restOfName += "75";
		else if((((double) health / (double) maxHealth) * 100) > 25)//health is above 25%
			restOfName += "50";
		else //health is below 25%
			restOfName += "25";
		return restOfName;
	}
	
	public boolean takeDamage(int damage)
	{
		setHealth(getHealth() - damage);
		this.setName(getName(size, getHealth(), getMaxHealth()));
		if(getHealth() <= 0)
			return true;
		else
			return false;
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
		if(size == 2)
			//32 = displacement needed to get medium slime in center
			g.drawImage(image, (int) getX() + 32, (int) getY() + 32, resize, resize, null);
		else if(size == 1)
			//48 = displacement needed to get small slime in center
			g.drawImage(image, (int) getX() + 48, (int) getY() + 48, resize, resize, null);
		else
			g.drawImage(image, (int) getX(), (int) getY(), resize, resize, null);
		
		//Draw Health Bar
		
		g.setColor(Color.RED);
		g.fillRect((int)getX(), (int) getY() + 132, 128, 8);
		g.setColor(Color.GREEN);
		double barLength =  ((double) getHealth() / getMaxHealth()) * 128;
		g.fillRect((int)getX(), (int) getY() + 132, (int) barLength, 8);
	}
}
