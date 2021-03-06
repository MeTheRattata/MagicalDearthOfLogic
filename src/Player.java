import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Player extends Playable implements Activateable
{
	private int mana;
	private int maxMana;
	private String type;
	int manaUsed;
	//target for a healing spell, if it is -1 then the heal is an all heal, if not, target is companion
	protected TargetSelectMenu healSelect;
	private boolean isHeal = false;
	
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
		super(xPos, yPos, 100, "wizard" + newType);
		type = newType;
		mana = 50;
		maxMana = mana;
		manaUsed = 0;
		//Strings for options useless here, since intSelected will be used to determine attack to use
		setMoves(new String[]{"Strike", type + " Attack", "Team Heal", "Target Heal"}, new int[]{-20, -40, 25, 50});
		healSelect = new TargetSelectMenu("Playable");
	}
	/**
	 * Take damage, add 30 damage frames and change sprite to hurt version
	 */
	public boolean takeDamage(int damage)
	{
		setHealth(getHealth() + damage);
		if(damage < 0)
		{
			setDamageFrames(30);
			updateImage("wizardDmg");
		}
		if(getHealth() <= 0)
			return true;
		else
			return false;
	}
	/**
	 * Do damage to an Entity and use mana required
	 * @param entity: the Entity being targeted
	 */
	public boolean doDamage(Entity entity)
	{
		if(manaUsed == 0 || canUseMana())
			return entity.takeDamage(getAttackPower());
		return false;
	}
	/**
	 * Return attack power based on MouseEvent click in 4 option menu.
	 */
	public void setAttackPower(MouseEvent e)
	{
		setAttackPower(moveSelect.getAttackPower(e));
		int attack = getAttackPower();
		
		//TODO: Change switch case to using attack names instead of attack power
		if(attack != -1)
		{
			switch(attack)
			{
			case -20: //Strike
				manaUsed = 0;
				enemySelect.activate();
				break;
			case -40: //Magic attack
				manaUsed = 30;
				enemySelect.activate();
				break;
			case 25: //Team heal
				isHeal = true;
				super.setAttackTarget(4); //Healtarget is past playable entities, therefore this is a team heal
				healSelect.activate();
				manaUsed = 20;
				break;
			case 50: //Target heal
				manaUsed = 15;
				isHeal = true;
				healSelect.activate();
				break;
			}
			moveSelect.deActivate();
		}
	}
	/**
	 * Get mana
	 * @return current amount of mana
	 */
	public int getMana() {
		return mana;
	}
	/**
	 * Refills mana back to maximum amount.
	 */
	public void refillMana() {
		mana = maxMana;
	}
	//TODO: Figure out how to fangle this with activating and deactivating all de menus
	public void setAttackTarget(MouseEvent e)
	{
		if(enemySelect.isActive())
			super.setAttackTarget(e);
		else if(healSelect.isActive())
		{
			if(getAttackTarget() != 4)
				super.setAttackTarget(healSelect.intSelected(e));
			if(getAttackTarget() != -1)
				healSelect.deActivate();
		}		
	}
	public boolean isHealing(){
		return isHeal;
	}
	public boolean canUseMana()
	{
		mana -= manaUsed;
		if(mana <= 0)
		{
			mana = 0;
			return false;
		}
		return true;
	}
	
	/**
	 * Paints the sprite associated with a Player object onto its current coordinates, including health and mana bar
	 * @param g: the graphics object on which to paint the sprite on
	 */
	public void paintComponent(Graphics g)
	{ 
		if(getDamageFrames() > 0)
		{
			decrementDamageFrames();
			if(getDamageFrames() == 0)
				updateImage("wizard" + type);
		}
		
		paintSprite(g);
		
		//Draw Health Bar
		g.setColor(Color.RED);
		g.fillRect((int)getX(), (int) getY() + 132, 128, 8);
		g.setColor(Color.GREEN);
		double barLength =  ((double) getHealth() / getMaxHealth()) * 128;
		g.fillRect((int)getX(), (int) getY() + 132, (int) barLength, 8);
		
		//draw mana bar
		g.setColor(Color.WHITE);
		g.fillRect((int)getX(), (int)getY() + 140, 128, 8);
		g.setColor(Color.BLUE);
		double manaBarLength =  ((double) mana / maxMana) * 128;
		g.fillRect((int)getX(), (int)getY() + 140, (int) manaBarLength, 8);
		
		//Paint active menu (should only be one)
		if(moveSelect.isActive())
			moveSelect.paintComponent(g);
		else if(enemySelect.isActive())
			enemySelect.paintComponent(g);
		else if(healSelect.isActive())
			healSelect.paintComponent(g);

	}
}   