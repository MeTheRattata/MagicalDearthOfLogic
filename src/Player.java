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
	private int healTarget = -1;
	protected TargetSelectMenu healSelect;
	
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
		setMoves(new String[]{"Strike", type + " Attack", "Team Heal", "Target Heal"}, new int[]{20, 40, 0, 0});
		healSelect = new TargetSelectMenu("Playable");
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
	public int getAttackPower() {
		return super.getAttackPower();
	}
	/**
	 * Return attack power based on MouseEvent click in 4 option menu.
	 */
	public void setAttackPower(MouseEvent e)
	{
		setAttackPower(moveSelect.getAttackPower(e));
		if(getAttackPower() != -1)
		{
			moveSelect.deActivate();
			if(getAttackPower() == 0) //If healing move, activates heal select to set heal target
			{
				healSelect.activate();
				System.out.println("heal select active");
			}
				
			else //If not a healing move, activates enemy select to set attack target
			{
				enemySelect.activate();
				System.out.println("Enemy select active");
			}
				
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
	/**
	 * Set heal target
	 * @param e: MouseClick that is possibly on a target.
	 * If MouseClick is on a target, exits with a legitimate target, if not, stays and waits for a MouseClick
	 */
	public void setHealTarget(MouseEvent e) 
	{
		healTarget = healSelect.intSelected(e);
		if(healTarget != -1)
			healSelect.deActivate();
	}
	/**
	 * Get Heal Target
	 * @return: healTarget
	 */
	public int getHealTarget() {
		return healTarget;
	}
	
	/**
	 * Paints the sprite associated with a Player object onto its current coordinates, including health and mana bar
	 * @param g: the graphics object on which to paint the sprite on
	 */
	public void paintComponent(Graphics g)
	{ 
		//Change to changing name to wizard dmg
		super.paintComponent(g);
		
		//draw mana bar
		g.setColor(Color.WHITE);
		g.fillRect((int)getX(), (int)getY() + 140, 128, 8);
		g.setColor(Color.BLUE);
		double manaBarLength =  ((double) mana / maxMana) * 128;
		g.fillRect((int)getX(), (int)getY() + 140, (int) manaBarLength, 8);
		
		//Paint active menu (should only be one)
		if(healSelect.isActive())
			healSelect.paintComponent(g);

	}
}   