public class HealthBar extends Entity
{
	//position is entity position + 130 y, entHealth = entity health, paint/repaint method is custom so image is actually a front
	public HealthBar(double xPos, double yPos, int entHealth, int entPower, String entImagePath) 
	{
		super(xPos, yPos +130, entHealth, "redBar");
	}
}