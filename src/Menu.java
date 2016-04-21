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
	private String[] texts;
	//Array that stores the four corners of the square that makes up the menu button
	//Corresponds with array of menu options (texts[0] is the text for the button 
	//which has corners at bounds[0][0], bounds[0][1], bounds[0][2], and bounds[0][3])
	private int[][] bounds;
	
	/**
	 * Constructor for a Menu object
	 * @param newTexts: array of menu options
	 * @param newBounds: array that stores the four corners of the square that makes up the menu button
	 */
	public Menu(String[] newTexts, int[][] newBounds)
	{
		texts = newTexts;
		bounds = newBounds;
	}
	
	/**
	 * Determines which menu option was selected and returns its corresponding string
	 * @param e: MouseClick event with coordinates
	 * @return the string corresponding to which menu option was clicked
	 */
	public String optionSelected(MouseEvent e)
	{
		
	}
	/**
	 * 
	 * @param e
	 * @param recLoc: row in bounds that the rectangle being checked is stored in
	 * @return true if the mouseEvent's coordinates are within the rectangle, false if not
	 */
	private boolean isWithinRectangle(MouseEvent e, int recLoc)
	{
		
	}
}