import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite implements Activateable
{
	private double x;
	private double y;
	private String name; //path of image used as sprite, change to change sprite
	private BufferedImage image = null;
	private boolean active;
	
	public Sprite(double newX, double newY, String newName)
	{
		x = newX;
		y = newY;
		name = newName;
		System.out.println(name);
		try {
			image = ImageIO.read(new File("res/" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePosition(double newX, double newY)
	{
		x = newX;
		y = newY;
	}
	public void updateImage(String newName)
	{
		name = newName;
		try {
			image = ImageIO.read(new File("res/" + newName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void activate() {
		active = true;
	}
	public void deActivate() {
		active = false;
	}
	public boolean isActive() {
		return active;
	}
	
	public void paintComponent(Graphics g)
	{
		//multiplied by 128 to make image 4 times larger
		g.drawImage(image, (int) x, (int) y, 128, 128, null);
	}
	public void paintComponent(Graphics g, int resize)
	{
		g.drawImage(image, (int) x, (int) y, resize, resize, null);
	}
}