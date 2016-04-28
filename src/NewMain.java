import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class NewMain 
{
	final static int WIDTH = 672;
	final static int HEIGHT = 480;
	
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Magical Dearth of Logic");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Main panel = new Main();
		frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run(){panel.tick(); panel.repaint();}
		}, 0, 1000/60);
	}
	
	public Main()
	{
	
	}
	public void tick() //happens 60 times a second, things happen
	{
		
	}
	
	//TODO: Fix random errors with companion setImage happening somewhere in here
	
	//Draws images 4 times larger, factor this into the other classes draw methods
	public void paintComponent(Graphics g)//already happens forever and ever
	{
		g.setColor(Color.WHITE);
		g.fillRect(0,0,672,480);
		g.setColor(Color.BLACK);
		Font font = new Font("Arial", Font.BOLD, 32);
		g.setFont(font);
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