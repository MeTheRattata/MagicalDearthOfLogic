import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Entity class for MagicalDearthOfLogic
 * @author Metherat
 *
 * Used to create subclasses representing objects that have a position, health, attack power, a name and a BufferedImage.
 */
public class Entity 
{
	private double x;
	private double y;
	private int health;
	private int maxHealth;
	private int attackPower;
	private String name; //path of image used as sprite, change to change sprite
	private BufferedImage image = null;
	private int damageFrames = 0;
	private int target = -1;
	
	/**
	 * Constructor for Entity class.
	 * @param xPos: x position of entity
	 * @param yPos: y position of entity
	 * @param entHealth: health of the entity (when full)
	 * @param entName: name of the entity, used to determine which sprite image it uses
	 * @param newAttackPower: attack power of the entity
	 */
	public Entity(double xPos, double yPos, int entHealth, String entName, int newAttackPower)
	{
		x = xPos;
		y = yPos;
		health = entHealth;
		maxHealth = entHealth;
		name = entName;
		attackPower = newAttackPower;

		//Create a new BufferedImage object using "res/" + name + ".png"
		try {
			image = ImageIO.read(new File("res/" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get x
	 * @return x coordinate
	 */
	public double getX()
	{
		return x;
	}
	/**
	 * Get y
	 * @return y coordinate
	 */
	public double getY()
	{
		return y;
	}
	/**
	 * Update the position of the entity
	 * @param newX: new x coordinate
	 * @param newY: new y coordinate
	 */
	public void updatePosition(double newX, double newY)
	{
		x = newX;
		y = newY;
	}
	/**
	 * Decrements health based on the damage taken, then returns a boolean of whether the entity has died or not
	 * @param damage: damage dealt to the entity
	 * @return: true if dead, false if not dead
	 */
	public boolean takeDamage(int damage)
	{
		health -= damage;
		damageFrames += 30;
		setName(name.replaceAll("Dmg", "") + "Dmg");
		if(health <= 0)
			return true;
		else
			return false;
	}
	/**
	 * Get attack
	 * @return attack power
	 */
	public int getAttackPower()
	{
		return attackPower;
	}
	/**
	 * Set attack power
	 * @param attack: new attack power
	 */
	public void setAttackPower(int attack)
	{
		attackPower = attack;
	}
	/**
	 * Get health
	 * @return return entity's current health
	 */
	public int getHealth()
	{
		return health;
	}
	/**
	 * Set health
	 * @param newHealth: new health 
	 */
	public void setHealth(int newHealth)
	{
		health = newHealth;
	}
	/**
	 * Get max health
	 * @return maxHealth
	 */
	public int getMaxHealth()
	{
		return maxHealth;
	}
	/**
	 * Sets name of entity and updates its BufferedImage
	 * @param newName: new name
	 */
	public void setName(String newName)
	{
		name = newName;
		updateImage();
	}
	/**
	 * Return entity name
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Returns remaining damage frame number.
	 * @return damageFrames
	 */
	public int getDamageFrames()
	{
		return damageFrames;
	}
	/**
	 * Set damage frames to newDamageFrames
	 * @param newDamageFrames
	 */
	public void setDamageFrames(int newDamageFrames)
	{
		damageFrames = newDamageFrames;
	}
	/**
	 * Decrements damage frame counter.
	 */
	public void decrementDamageFrames()
	{
		damageFrames--;
	}
	/**
	 * Set the entity's target 
	 * @param newTarget: entity's new target
	 */
	public void setTarget(int newTarget)
	{
		target = newTarget;
	}
	/**
	 * Return the entity's current target
	 * @return: target
	 */
	public int getTarget()
	{
		return target;
	}
	/**
	 * Get image
	 * @return entity's BufferedImage
	 */
	public BufferedImage getImage()
	{
		return image;
	}
	/**
	 * Updates the entity's BufferedImage
	 */
	public void updateImage()
	{
		try {
			image = ImageIO.read(new File("res/" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Paints the image with the selected Graphics object
	 * Paints sprite and then health bar based on health percentage
	 * @param g: what the image is painted with
	 */
	public void paintComponent(Graphics g)
	{
		//multiplied by 256 to make image 4 times larger
		g.drawImage(image, (int) x, (int) y, 128, 128, null);
		if(damageFrames > 0)
		{
			damageFrames--;
			if(damageFrames == 0)
				setName(name.replaceAll("Dmg", ""));
		}
		
		//Draw Health Bar
		g.setColor(Color.RED);
		g.fillRect((int)x, (int) y + 132, 128, 8);
		g.setColor(Color.GREEN);
		double barLength =  ((double) health / maxHealth) * 128;
		g.fillRect((int)x, (int) y + 132, (int) barLength, 8);
	}
}