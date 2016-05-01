import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TargetSelectMenu extends Menu
{
	private BufferedImage image;
	private String type;
	//Bounds doesn't work like it does in Menu of FourOptionMenu here.
	//Since the only entities available to be selected are in specific locations and are specific sizes,
	//TargetMenu can only check for targets in these four locations:
	private int[] xEntityPos = {32, 160, 512, 384};
	private int[] yEntityPos = {192, 32, 192, 32};
	private String selectLabel;
	//So, bounds is just {x1, y1, x2, y2, x3, y3, x4, y4}, with smaller denominations possible
	/**
	 * Constructor for a TargetSelectMenu
	 * @param type: The type of TargetSelectMenu,
	 * accepted values: All, Enemies and Playable
	 */
	public TargetSelectMenu(String type) 
	{
		super(new double[][]{});
		switch(type)
		{
			case "All":
				setBounds(new double[][]{{xEntityPos[0], yEntityPos[0]},{xEntityPos[1], yEntityPos[1]},
										 {xEntityPos[2], yEntityPos[2]},{xEntityPos[3], yEntityPos[3]}});
				selectLabel = "Select a target: ";
				break;
			case "Enemies":
				setBounds(new double[][]{{xEntityPos[2], yEntityPos[2]},{xEntityPos[3], yEntityPos[3]}});
				selectLabel = "Select an enemy: ";
				break;
			case "Playable":
				setBounds(new double[][]{{xEntityPos[0], yEntityPos[0]},{xEntityPos[1], yEntityPos[1]}});
				selectLabel = "Select a teammate: ";
				break;
		}
		//Set image as the red selection border
		try {
			image = ImageIO.read(new File("res/redSelectionBorder.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	public int entitySelected(MouseEvent e)
	{
		//Enemy IDs are 2 and 3, but intSelected would have returned 0 for 2 and 1 for 3
		if(type.equals("Enemies"))
			return intSelected(e) + 2;
		return intSelected(e);
	}
	public boolean isWithinRectangle(MouseEvent e, int recLoc)
	{
		return (e.getX() > bounds[recLoc][0] && e.getY() > bounds[recLoc][1] && 
				e.getX() < bounds[recLoc][0] + 128 && e.getY() < bounds[recLoc][1] + 128);
	}
	public void paintComponent(Graphics g)
	{
		//Paints red selection borders around entities in the selection menu
		for(int i = 0; i < bounds.length; i++)
		{
			g.drawImage(image, (int)bounds[i][0], (int)bounds[i][1], 128, 128, null);
		}
		g.setColor(Color.BLACK);
		Font font = new Font("Arial", Font.BOLD, 32);
		Rectangle rect = new Rectangle(0,352,672,128);
		Graphics2D g2 = (Graphics2D) g;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(4));
		g2.drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
		drawCenteredString(g, selectLabel, rect, font);
		
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