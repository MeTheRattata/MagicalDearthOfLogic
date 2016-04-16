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
	private int attackPower;
	String imagePath; //path of image used as sprite, change to change sprite
	
	public Entity(double xPos, double yPos, int entHealth, int entPower, String entImagePath)
	{
		x = xPos;
		y = yPos;
		health = entHealth;
		attackPower = entPower;
		imagePath = entImagePath;
	}
	
	//Methods using position
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public void updatePosition(double newX, double newY)
	{
		x = newX;
		y = newY;
	}
	
	//Methods using health
	
	//Returns whether the entity is dead (true = dead, false = still alive)
	public boolean takeDamage(int damage)
	{
		health -= damage;
		if(health <= 0)
			return true;
		else
			return false;
	}

	public int getDamage()
	{
		return attackPower;
	}
	
	
	public void paintComponent(Graphics g)
	{
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/" + imagePath + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//multiplied by 256 to make image 4 times larger
		g.drawImage(image, (int) x, (int) y, 128, 128, null);
	}
}