public class Companion extends Entity
{
	public Companion(double xPos, double yPos, String name) 
	{
		//Companions start with an hp of 100 and an attack power of 20
		//imagepath will be input at beginning as the entity name
		super(xPos, yPos, 100, 15, name);
	}
}