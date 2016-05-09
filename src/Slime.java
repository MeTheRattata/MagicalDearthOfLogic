import java.awt.Color;
import java.awt.Graphics;

/**
 * A class that represents a Slime enemy
 * @author Metherat
 * 
 * Slime: an Activateable Entity with a size, resize amount, and changing sprite based on damage percentile.
 */
public class Slime extends Entity
{
	private int size; //size of slime
	private int resize; //amount to resize sprite by
	/**
	 * Constructor for a slime object
	 * @param xPos: x coordinate
	 * @param yPos: y coordinate
	 */
	public Slime(double xPos, double yPos, int newSize) 
	{
		super(xPos, yPos, "slime/slimeLarge100", assignHealth(newSize), 0, true);
		size = newSize;
		resize = (int) (16 * Math.pow(2, size));
		updateSpritePosition((int) getX() + getDisplacement(), (int) getY() + getDisplacement());
		//If slimes are smaller than 32 by 32, puts them in the center of the 32 by 32 square where they are
		//Also updates size the sprite is resized to to keep proportions consistent
		updateImage();
		switch (size)
		{
			case 1:
				setAttackPower(-5);
			case 2:
				setAttackPower(-10);
			case 3:
				setAttackPower(-15);	
		}
	}
	/**
	 * Assign max health based on size
	 * @param size: the size of the slime
	 * @return: max health
	 */
	private static int assignHealth(int size)
	{
		if(size == 1)
			return 50;
		else if(size == 2)
			return 75;
		else if(size == 3)
			return 100;
		return -1;
	}
	/**
	 * Set a slime's name based on size and health percentage
	 */
	private void updateImage()
	{
		//TODO: Fix health percentages not working properly
		String restOfName = "slime/slime";
		if(size == 1)
			restOfName += "Small";
		else if(size == 2)
			restOfName += "Medium";
		else if(size == 3)
			restOfName += "Large";
		
		if(getDamageFrames() == 0)
		{
			if((((double) getHealth() / (double) getMaxHealth()) * 100) > 75) //health is above 75%
			restOfName += "100";
			else if((((double) getHealth() / (double) getMaxHealth()) * 100) > 50)//health is above 50%
			restOfName += "75";
			else if((((double) getHealth() / (double) getMaxHealth()) * 100) > 25)//health is above 25%
			restOfName += "50";
			else if(((((double) getHealth() / (double) getMaxHealth()) * 100) <= 25))//health is below 25%
			restOfName += "25";
		}
		updateImage(restOfName);
	}
	/**
	 * Decrement 
	 * @return: if the slime is dead- true, if not- false
	 */
	public boolean takeDamage(int damage)
	{
		setHealth(getHealth() + damage);
		if(damage < 0)
		{
			setDamageFrames(30);
			updateImage("slime/slime" + getSize() + "Dmg");
		}
		
		if(getHealth() <= 0)
			return true;
		else
			return false;
	}
	/**
	 * Returns a string based on the slime's size
	 * @return: size
	 */
	public String getSize()
	{
		if(size == 1)
			return "Small";
		else if(size == 2)
			return "Medium";
		else if(size == 3)
			return "Large";
		return "";
	}
	/**
	 * Paint a slime's sprite, centered and with health bar + damage frames
	 */
	public void paintComponent(Graphics g)
	{
		if(getDamageFrames() > 0)
		{
			decrementDamageFrames();
			if(getDamageFrames() == 0)
				updateImage();
			//TODO: look over slime class, make sure it works right with sprites updatename
		}
		
		paintSprite(g, resize);
		
		//Draw Health Bar
		g.setColor(Color.RED);
		g.fillRect((int)getX(), (int) getY() + 132, 128, 8);
		g.setColor(Color.GREEN);
		double barLength =  ((double) getHealth() / getMaxHealth()) * 128;
		g.fillRect((int)getX(), (int) getY() + 132, (int) barLength, 8);
	}
	/**
	 * Get displacement for centering all sizes of slimes
	 */
	public int getDisplacement()
	{
		if(size == 1)
			return 48;
		else if(size == 2)
			return 32;
		else if(size == 3)
			return 0;
		else
			return -1;
	}
}
