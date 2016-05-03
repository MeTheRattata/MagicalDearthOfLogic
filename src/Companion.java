/**
 * Companion class for MagicalDearthOfLogic
 * @author Metherat
 * @parent Playable
 * 
 * Class that represents the player's animal companion. Subclass of Entity.
 */
public class Companion extends Playable
{
	/**
	 * Constructor for Companion class.
	 * @param xPos: x coordinate of the companion's current position
	 * @param yPos: y coordinate of the companion's current position
	 * @param name: the name of the companion, used to determine which sprite is used for its image
	 */
	public Companion(double xPos, double yPos, String name) 
	{
		//Companions start with an hp of 100 and an attack power of 20
		super(xPos, yPos, 100, name);
		//TODO: Change default moves based on name
		setMoves(new String[]{"Tackle",  "Bite", "Swipe", "Tail Slap"}, new int[]{-20, -40, -15, -5});
	}
}