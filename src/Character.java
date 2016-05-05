import java.awt.Color;
import java.awt.Graphics;

public class Character extends Entity
{
	private int health;
	private int maxHealth;
	private int attackPower;
	private int damageFrames;
	private int attackTarget;
	
	public Character(double xPos, double yPos, String entName, int newHealth, int newAttackPower)
	{
		super(xPos, yPos, entName);
		health = newHealth;
		maxHealth = newHealth;
		attackPower = newAttackPower;
		damageFrames = 0;
		attackTarget = -1;
	}
	
	/**
	 * Decrements health based on the damage taken, then returns a boolean of whether the entity has died or not
	 * @param damage: damage dealt to the entity
	 * @return: true if dead, false if not dead
	 */
	public boolean takeDamage(int damage)
	{
		health += damage;
		if(damage < 0) //If is damage taken and not healing
		{
			damageFrames += 30;
			setName(getName().replaceAll("Dmg", "") + "Dmg");
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
	 * Set the entity's target 
	 * @param newTarget: entity's new target
	 */
	public void setAttackTarget(int newTarget) {
		attackTarget = newTarget;
	}
	/**
	 * Return the entity's current target
	 * @return: target
	 */
	public int getAttackTarget() {
		return attackTarget;
	}

	public void setRandomAttackTarget() {
		attackTarget = (int) Math.random(); 
	}
	
	/**
	 * Paints the image with the selected Graphics object
	 * Paints sprite and then health bar based on health percentage
	 * @param g: what the image is painted with
	 */
	public void paintComponent(Graphics g)
	{
		//multiplied by 256 to make image 4 times larger
		super.paintComponent(g);
		if(damageFrames > 0)
		{
			damageFrames--;
			if(damageFrames == 0)
				setName(getName().replaceAll("Dmg", ""));
		}
		
		//Draw Health Bar
		g.setColor(Color.RED);
		g.fillRect((int)getX(), (int) getY() + 132, 128, 8);
		g.setColor(Color.GREEN);
		double barLength =  ((double) health / maxHealth) * 128;
		g.fillRect((int)getX(), (int) getY() + 132, (int) barLength, 8);
	}
}
