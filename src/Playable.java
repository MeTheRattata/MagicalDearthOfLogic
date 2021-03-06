import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * A class that represents a playable character
 * @author Metherat
 *
 * Playable: an Activateable Entity that has enemySelect and moveSelect menus
 */
public abstract class Playable extends Entity implements Activateable
{
	//Protected so its easy to use their isActive, activate and deActivate methods in subclasses
	protected TargetSelectMenu enemySelect; //menu for selecting enemy target
	protected FourOptionMenu moveSelect; //menu for selecting move to use
	/**
	 * Constructor for a playable object
	 * @param xPos: x coordinate
	 * @param yPos: y coordinate
	 * @param entHealth: starting health
	 * @param entName: name
	 * @param newAttackPower: attack power
	 */
	public Playable(double xPos, double yPos, int entHealth, String entName) {
		//attackPower is instantiated as zero since it is always set by a menu
		super(xPos, yPos, entName, entHealth, 0, true);
		enemySelect = new TargetSelectMenu("Enemies");
	}
	/**
	 * Set Playable character's moveset
	 * @param labels: names of moves
	 * @param attackPowers: attack powers of moves
	 */
	public void setMoves(String[] labels, int[] attackPowers)
	{
		//Transforms int array of attack powers into strings to make fouroptionmenu instantiation work
		String[] strAttackPowers = new String[attackPowers.length];
		for(int i = 0; i < attackPowers.length; i++)
			strAttackPowers[i] = attackPowers[i] + "";
		moveSelect = new FourOptionMenu(0, 352, 672, 480, strAttackPowers, labels);
	}
	/**
	 * Set attack target
	 * @param e: MouseClick either on an enemy or nowhere
	 * If nowhere, attackTarget is -1 and this method still waits
	 */
	public void setAttackTarget(MouseEvent e)
	{
		super.setAttackTarget(enemySelect.intSelected(e) + 2);
		if(enemySelect.intSelected(e) != -1)
			enemySelect.deActivate();
	}
	/**
	 * Sets the Playable character's target
	 */
	public void setAttackTarget(int target)
	{
		super.setAttackTarget(target);
	}
	/**
	 * Set attack power based on attack selected.
	 * @param e
	 */
	public void setAttackPower(MouseEvent e)
	{
		//If this mouse click fails, attack power is set to -1 through the moveSelect menu
		setAttackPower(moveSelect.getAttackPower(e));
		if(getAttackPower() != -1)
		{
			moveSelect.deActivate();
			enemySelect.activate();
		}
	}
	/**
	 * Paints the Playable character and its active menus
	 * @ param g: the Graphics panel to paint the Playable character onto
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(moveSelect.isActive())
			moveSelect.paintComponent(g);
		else if(enemySelect.isActive())
			enemySelect.paintComponent(g);
	}
}