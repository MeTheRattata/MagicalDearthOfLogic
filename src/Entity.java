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
 * Used to create subclasses representing objects that have a position, a name and a BufferedImage.
 * So, anything that can be seen (mobs, animations)
 */
public class Entity implements Activateable
{
	private double x;
	private double y;
	private String name; //path of image used as sprite, change to change sprite
	private BufferedImage image = null;
	private boolean active = true;
	
	/**
	 * Constructor for Entity class.
	 * @param xPos: x position of entity
	 * @param yPos: y position of entity
	 * @param entHealth: health of the entity (when full)
	 * @param entName: name of the entity, used to determine which sprite image it uses
	 * @param newAttackPower: attack power of the entity
	 */
	public Entity(double xPos, double yPos, String entName)
	{
		x = xPos;
		y = yPos;
		name = entName;

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
	public double getX() {
		return x;
	}
	/**
	 * Get y
	 * @return y coordinate
	 */
	public double getY() {
		return y;
	}
	/**
	 * Update the position of the entity
	 * @param newX: new x coordinate
	 * @param newY: new y coordinate
	 */
	public void updatePosition(double newX, double newY) {
		x = newX;
		y = newY;
	}
	
	/**
	 * Sets name of entity and updates its BufferedImage
	 * @param newName: new name
	 */
	public void setName(String newName) {
		name = newName;
		updateImage();
	}
	/**
	 * Return entity name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get image
	 * @return entity's BufferedImage
	 */
	public BufferedImage getImage() {
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
	 * Activate entity
	 */
	public void activate() {
		active = true;
	}	
	/**
	 * Deactivate entity
	 */
	public void deActivate() {
		active = false;	
	}
	/**
	 * Return if the entity is active or not
	 * @return: boolean value active
	 */
	public boolean isActive() {
		return active;
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
	}
}