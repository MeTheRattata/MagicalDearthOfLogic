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
public class Entity implements Activateable
{
	private double x;
	private double y;
	private int health;
	private int maxHealth;
	private int attackPower;
	private String name; //path of image used as sprite, change to change sprite
	private int damageFrames = 0;
	private int attackTarget = -1;
	private boolean active = true;
	private Sprite sprite;
	
	/**
	 * Constructor for Entity class.
	 * @param xPos: x position of Entity
	 * @param yPos: y position of Entity
	 * @param entHealth: health of the Entity (when full)
	 * @param entName: name of the Entity, used to determine which image its sprite uses
	 * @param newAttackPower: attack power of the Entity
	 */
	public Entity(double xPos, double yPos,  String entName, int entHealth, int newAttackPower, boolean isItMob)
	{
		x = xPos;
		y = yPos;
		health = entHealth;
		maxHealth = entHealth;
		name = entName;
		attackPower = newAttackPower;
		sprite = new Sprite(x, y, name);
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
	 * Update the position of the Entity
	 * @param newX: new x coordinate
	 * @param newY: new y coordinate
	 */
	public void updatePosition(double newX, double newY) {
		x = newX;
		y = newY;
		sprite.updatePosition(newX, newY);
	}
	/**
	 * Updates the position of the Entity's Sprite, used when sprite position is different than Entity position
	 * @param newX: the Sprite's new x
	 * @param newY: the Sprite's new y
	 */
	public void updateSpritePosition(int newX, int newY)
	{
		sprite.updatePosition(newX, newY);
	}
	/**
	 * Decrements health based on the damage taken, then returns a boolean of whether the Entity has died or not
	 * @param damage: damage dealt to the Entity, negative if damage, positive if healing
	 * @return: true if dead, false if not dead
	 */
	public boolean takeDamage(int damage)
	{
		health += damage;
		if(damage < 0) //If is damage taken and not healing
		{
			damageFrames += 30;
			sprite.updateImage(name.replaceAll("Dmg", "") + "Dmg");
		}
		if(health <= 0)
			return true;
		else
			return false;
	}
	/**
	 * Get attack
	 * @return attack power
	 */
	public int getAttackPower() {
		return attackPower;
	}
	/**
	 * Set attack power
	 * @param attack: new attack power
	 */
	public void setAttackPower(int attack) {
		attackPower = attack;
	}
	/**
	 * Get health
	 * @return return entity's current health
	 */
	public int getHealth() {
		return health;
	}
	/**
	 * Set health
	 * @param newHealth: new health 
	 */
	public void setHealth(int newHealth)
	{
		health = newHealth;
		if(health > maxHealth)
			health = maxHealth;
	}
	/**
	 * Get max health
	 * @return maxHealth
	 */
	public int getMaxHealth() {
		return maxHealth;
	}
	/**
	 * Return Entity name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns remaining damage frame number.
	 * @return damageFrames
	 */
	public int getDamageFrames() {
		return damageFrames;
	}
	/**
	 * Set damage frames to newDamageFrames
	 * @param newDamageFrames
	 */
	public void setDamageFrames(int newDamageFrames) {
		damageFrames = newDamageFrames;
	}
	/**
	 * Decrements damage frame counter.
	 */
	public void decrementDamageFrames() {
		damageFrames--;
	}
	/**
	 * Set the Entity's target 
	 * @param newTarget: Entity's new target
	 */
	public void setAttackTarget(int newTarget) {
		attackTarget = newTarget;
	}
	/**
	 * Set the Entity's attack target to either 1 or 0 semi-randomly
	 */
	public void setRandomAttackTarget() 
	{
		attackTarget = (int) Math.random() ; 
	}
	/**
	 * Return the Entity's current target
	 * @return: target
	 */
	public int getAttackTarget() {
		return attackTarget;
	}
	/**
	 * Updates the Entity's BufferedImage
	 */
	public void updateImage(String newName){
		sprite.updateImage(newName);
	}
	/**
	 * Activate Entity
	 */
	public void activate() {
		active = true;
	}	
	/**
	 * Deactivate Entity
	 */
	public void deActivate() {
		active = false;	
	}
	/**
	 * Return if the Entity is active or not
	 * @return: boolean value active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * Paints the Entity's sprite
	 * @param g: the Graphics object the sprite is painted onto
	 */
	public void paintSprite(Graphics g)
	{
		sprite.paintComponent(g);
	}
	/**
	 * Paints the Entity's sprite with the specified resize amount
	 * @param g: the Graphics object the Entity is painted onto
	 * @param resize: the amount to resize the sprite by
	 */
	public void paintSprite(Graphics g, int resize)
	{
		sprite.paintComponent(g, resize);
	}
	/**
	 * Paints the Entity
	 * Paints sprite and then health bar based on health percentage
	 * @param g: the Graphics object the Entity is painted onto
	 */
	public void paintComponent(Graphics g)
	{	
		//If taking damage, draw sprite to damaged form
		if(damageFrames > 0)
		{
			damageFrames--;
			if(damageFrames == 0)
			sprite.updateImage(name.replaceAll("Dmg", ""));
		}
		
		//multiplied by 256 to make image 4 times larger
		paintSprite(g);
		
		//Draw Health Bar
		g.setColor(Color.RED);
		g.fillRect((int)x, (int) y + 132, 128, 8);
		g.setColor(Color.GREEN);
		double barLength =  ((double) health / maxHealth) * 128;
		g.fillRect((int)x, (int) y + 132, (int) barLength, 8);
	}
}