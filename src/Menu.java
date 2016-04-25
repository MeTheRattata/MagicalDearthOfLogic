import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
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
	public Menu(int startX, int startY, int endX, int endY, String[] newOptions, String[] newLabels) 
	{
		options = newOptions;
		labels = newLabels;
		//Sets boundaries of menu to the square selected divided by 4
		int width = endX - startX;
		int height = endY - startY;
		bounds = new double[][]{{startX, startY, startX + width / 2, startY + height / 2}, 
								{startX + width / 2, startY, startX + width, startY + height / 2},
								{startX, startY + height / 2, startX + width / 2, startY + height}, 
								{startX + width / 2, startY + height / 2, startX + width, startY + height}};
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
	 * isWithinRectangle: checks if a mouseclick is within a rectangle
	 * @param e
	 * @param recLoc: row in bounds that the rectangle being checked is stored in
	 * @return true if the mouseEvent's coordinates are within the rectangle, false if not
	 */
	private boolean isWithinRectangle(MouseEvent e, int recLoc)
	{
		return (e.getX() > bounds[recLoc][0] && e.getY() > bounds[recLoc][1] && 
				e.getX() < bounds[recLoc][2] && e.getY() < bounds[recLoc][3]);
	}
	
	/**
	 * Paint method for a Menu object
	 * @param g: the graphics object to draw the menu on
	 */
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.BLACK);
		Font font = new Font("Arial", Font.BOLD, 32);
		for(int i = 0; i < bounds.length; i++)
		{
			Rectangle rect = new Rectangle((int) bounds[i][0], (int) bounds[i][1], (int) (bounds[i][2] - bounds[i][0]), (int) (bounds[i][3] - bounds[i][1]));
			Graphics2D g2 = (Graphics2D) g;
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(4));
			g2.drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
			drawCenteredString(g, labels[i], rect, font);
			g2.setStroke(oldStroke);
		}
	}
	/**
	 * Draw a string in the center of a rectangle
	 * @param g: the graphics object in which to draw the string
	 * @param text: string to be drawn
	 * @param rect: rectangle to draw the string in
	 * @param font: font of the string
	 */
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) 
	{
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = (rect.width - metrics.stringWidth(text)) / 2 + rect.x;
	    int y = ((rect.height) / 2) + rect.y + metrics.getAscent() / 2;
	    g.setFont(font);
	    g.drawString(text, x, y);
	}
}