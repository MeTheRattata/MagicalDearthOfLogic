import java.awt.event.MouseEvent;

/**
 * Companion class for MagicalDearthOfLogic
 * @author Metherat
 * @parent Entity
 * 
 * Class that represents the player's animal companion. Subclass of Entity.
 */
public class Companion extends Entity
{
	TargetSelectMenu enemySelect;
	/**
	 * Constructor for Companion class.
	 * @param xPos: x coordinate of the companion's current position
	 * @param yPos: y coordinate of the companion's current position
	 * @param name: the name of the companion, used to determine which sprite is used for its image
	 */
	public Companion(double xPos, double yPos, String name) {
		//Companions start with an hp of 100 and an attack power of 20
		super(xPos, yPos, 100, name, 15);
		enemySelect = new TargetSelectMenu("Enemies");
	}
	/**
	 * Set attack target
	 * @param e: MouseClick either on an enemy or nowhere
	 * If nowhere, attackTarget is -1 and this method still waits
	 */
	public void setAttackTarget(MouseEvent e)
	{
		super.setAttackTarget(enemySelect.intSelected(e));
	}
}