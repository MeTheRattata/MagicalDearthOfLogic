import java.awt.Color;
import java.awt.Graphics;

public class Player extends Entity
{
	private int mana;
	private int maxMana;
	private int manaSpent;
	private boolean isMagicAttack = false;
	private String type;
	
	
	/**
	 * Constructor for player class
	 * @param xPos
	 * @param yPos
	 * @param newType
	 */
	public Player(double xPos, double yPos, String newType) 
	{
		//Players start with a health of 100 and an attack power of 20
		//Name is specified upon player select, is "wizard" + "Rock", "Life", "Light" or "Water"
		super(xPos, yPos, 100, "wizard" + newType, 20);
		type = newType;
		mana = 50;
		maxMana = mana;
		manaSpent = 20;
	}
	/**
	 * Take damage, add 30 damage frames and change sprite to hurt version
	 */
	public boolean takeDamage(int damage)
	{
		setHealth(getHealth() - damage);
		setDamageFrames(30);
		setName("wizardDmg");
		if(getHealth() <= 0)
			return true;
		else
			return false;
	}
	/**
	 * Return attack power. If next attack is to be a magic attack, doubles attack power and resets magic attack boolean.
	 */
	public int getAttack()
	{
		if(isMagicAttack)
		{
			isMagicAttack = false;
			mana -= manaSpent;
			return super.getAttack() * 2;
		}
		return super.getAttack();
	}
	/**
	 * Sets isMagicAttack to the passed value
	 * @param bool: the boolean value that isMagicAttack is set to
	 */
	public void setMagicAttack(boolean bool)
	{
		isMagicAttack = bool;
	}
	/**
	 * Get mana
	 * @return current amount of mana
	 */
	public int getMana() 
	{
		return mana;
	}
	/**
	 * Refills mana back to maximum amount.
	 */
	public void refillMana()
	{
		mana = maxMana;
	}
	/**
	 * Paints the sprite associated with a Player object onto its current coordinates, including health and mana bar
	 * @param g: the graphics object on which to paint the sprite on
	 */
	public void paintComponent(Graphics g)
	{
		//multiplied by 256 to make image 4 times larger
		g.drawImage(getImage(), (int)getX(), (int)getY(), 128, 128, null);
		
		if(getDamageFrames() > 0)
		{
			decrementDamageFrames();
			if(getDamageFrames() == 0)
				setName("wizard" + type);
		}
		
		//Draw Health Bar
		g.setColor(Color.RED);
		g.fillRect((int)getX(), (int)getY() + 132, 128, 8);
		g.setColor(Color.GREEN);
		double barLength =  ((double) getHealth() / getMaxHealth()) * 128;
		g.fillRect((int)getX(), (int)getY() + 132, (int) barLength, 8);
		
		//draw mana bar
		g.setColor(Color.WHITE);
		g.fillRect((int)getX(), (int)getY() + 140, 128, 8);
		g.setColor(Color.BLUE);
		double manaBarLength =  ((double) mana / maxMana) * 128;
		g.fillRect((int)getX(), (int)getY() + 140, (int) manaBarLength, 8);
	}
}   