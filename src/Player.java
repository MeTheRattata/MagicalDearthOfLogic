public class Player extends Entity
{
	int attackPower = 20;
	public Player(double xPos, double yPos, String type) 
	{
		//Players start with a health of 100 and an attack power of 20
		//Type is specified upon player select, is "Rock", "Life", "Light" or "Water"
		super(xPos, yPos, 100, "wizard" + type);
	}
}