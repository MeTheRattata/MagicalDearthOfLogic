import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Player extends Entity
{
	private int mana;
	private int maxMana;
	private String type;
	private FourOptionMenu moveSelect;
	int manaUsed;
	private boolean isInCombatMenu = false;
	//target for a healing spell, if it is -1 then the heal is an all heal, if not, target is companion
	private int healTarget = -1; 
	
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
		manaUsed = 0;
		//Strings for options useless here, since intSelected will be used to determine attack to use
		moveSelect = new FourOptionMenu(0, 352, 672, 480, new String[]{"", "", "", ""}, 
							  new String[]{"Strike", type + " Attack", "Team Heal", "Target Heal"});
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
	public int getAttackPower()
	{
		return super.getAttackPower();
	}
	/**
	 * Return attack power based on MouseEvent click in 4 option menu.
	 */
	public void setAttackPower(MouseEvent e)
	{
		int attackSelected = moveSelect.intSelected(e);
		setAttackPower(-1);
		switch(attackSelected)
		{
		//Strike
		case 0: 
			setAttackPower(20);
			manaUsed = 0;
			break;
		//Magic Attack
		case 1: 
			setAttackPower(40);
			manaUsed = 30;
			break;
		//Team Heal
		case 2:
			setAttackPower(0);
			manaUsed = 20;
			//no heal target, so this is a team heal
			healTarget = -1;
			break;
		//One Target Heal
		case 3: 
			setAttackPower(0);
			manaUsed = 20;
			//TODO: make healing selectable between player and companion
			healTarget = 1; //Target = companion
			break;
		}
		//TODO: prevent healing if mana is below required amount
		if(mana <= manaUsed)
			setAttackPower(0);
		else
			mana -= manaUsed;
		
		isInCombatMenu = false;
	}
	/**
	 * Sets isInCombatMenu to the passed boolean
	 * @param bool: boolean that isInCombatMenu is set to
	 */
	public void setInCombatMenu(boolean bool)
	{
		isInCombatMenu = bool;
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
	 * Get Heal Target
	 * @return: healTarget
	 */
	public int getHealTarget()
	{
		return healTarget;
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
	/**
	 * Paint the players move select menu
	 * @param g: the graphics object to paint with
	 */
	public void paintMoveSelect(Graphics g)
	{
		moveSelect.paintComponent(g);
	}
}   