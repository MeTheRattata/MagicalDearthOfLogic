public class Player extends Entity
{
	public Player(double xPos, double yPos, String type) 
	{
		//Players start with a health of 100 and an attack power of 20
		//Type is specified upon player select, is "Rock", "Life", "Light" or "Water"
		super(xPos, yPos, 100, 20, "wizard" + type);
	}
}