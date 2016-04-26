import java.awt.Graphics;
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
				break;
			case "Enemies":
				setBounds(new double[][]{{xEntityPos[2], yEntityPos[2]},{xEntityPos[3], yEntityPos[3]}});
				break;
			case "Playable":
				setBounds(new double[][]{{xEntityPos[0], yEntityPos[0]},{xEntityPos[1], yEntityPos[1]}});
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
	public void paintComponent(Graphics g)
	{
		//Paints red selection borders around entities in the selection menu
		for(int i = 0; i < bounds.length; i++)
		{
			g.drawImage(image, (int)bounds[i][0], (int)bounds[i][1], 128, 128, null);
		}
	}
}