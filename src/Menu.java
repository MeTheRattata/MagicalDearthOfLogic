import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * Menu class for MagicalDearthOfLogic
 * @author Metherat
 *
 * A class that represents a menu. Stores the menu options in an array of strings
 * and the menu button boundaries in an array of integers.
 */
public class Menu 
{
	//Array that stores 2 opposite corners of the menu
	//Corresponds with array of menu options: texts[0] is the text for the button 
	//[0][0] = x coordinate of top left corner, [0][1] = y coordinate of top left corner,
	//[0][2] = x coordinate of bottom right corner, [0][3] = y coordinate of bottom right corner
	private double[][] bounds;
	private String[] options;
	private String[] labels;
	
	/**
	 * Constructor for a Menu object
	 * @param newOptions: array of menu options
	 * @param newBounds: array that stores the four corners of the square that makes up the menu button
	 * @param newTexts: array of Strings to be printed as the menu option labels
	 */
	public Menu(double[][] newBounds, String[] newOptions, String[] newLabels)
	{
		bounds = newBounds;
		options = newOptions;
		labels = newLabels;
	}
	
	/**
	 * Determines which menu option was selected and returns its corresponding integer
	 * @param e: MouseClick event with coordinates
	 * @return the integer corresponding to which menu option was clicked
	 */
	public int intSelected(MouseEvent e)
	{
		for(int i = 0; i < options.length; i++)
			if(isWithinRectangle(e, i))
				return i;
		return -1;
	}
	/**
	 * Determines which menu option was selected and returns its corresponding string
	 * @param e: MouseClick event with coordinates
	 * @return the string corresponding to which menu option was clicked
	 */
	public String optionSelected(MouseEvent e)
	{
		for(int i = 0; i < options.length; i++)
			if(isWithinRectangle(e, i))
				return options[i];
		return "";
	}
	/**
	 * 
	 * @param e
	 * @param recLoc: row in bounds that the rectangle being checked is stored in
	 * @return true if the mouseEvent's coordinates are within the rectangle, false if not
	 */
	private boolean isWithinRectangle(MouseEvent e, int recLoc)
	{
		return (e.getX() > bounds[recLoc][0] && e.getY() > bounds[recLoc][1] && 
				e.getX() < bounds[recLoc][2] && e.getY() < bounds[recLoc][3]);
	}
	
	public void paintComponent(Graphics g)
	{
		
	}
}