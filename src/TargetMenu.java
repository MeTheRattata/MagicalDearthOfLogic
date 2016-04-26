/**
 * Target Menu: a class that defines a menu designed for targeting the 4 possible entities on screen.
 * @author Metherat
 */
public class TargetMenu extends Menu 
{
	/**
	 * Default constructor for a TargetMenu object
	 * @param newBounds: menu boundaries
	 * @param newOptions
	 * @param newLabels
	 */
	public TargetMenu(double[][] newBounds, String[] newOptions, String[] newLabels) 
	{
		super(newBounds, newOptions, newLabels);
	}
	/**
	 * 
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param newOptions
	 * @param newLabels
	 */
	public TargetMenu(int startX, int startY, int endX, int endY, String[] newOptions, String[] newLabels) 
	{
		super(new double[][]{}, newOptions, newLabels);
		//Sets boundaries of menu to the square selected divided by 4
		int width = endX - startX;
		int height = endY - startY;
		setBounds(new double[][]{{startX, startY, startX + width / 2, startY + height / 2}, 
								{startX + width / 2, startY, startX + width, startY + height / 2},
								{startX, startY + height / 2, startX + width / 2, startY + height}, 
								{startX + width / 2, startY + height / 2, startX + width, startY + height}});
	}
	
}
