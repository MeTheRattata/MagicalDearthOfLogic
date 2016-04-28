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
	private int[] xEntityPos = {32, 160, 512, 384};
	private int[] yEntityPos = {192, 32, 192, 32};
	double[][] fullScreenMenuBounds = {{0, 0, 336, 240}, {336, 0, 672, 240}, {0, 240, 336, 480}, {336, 240, 672, 480}};
	FourOptionMenu playerSelect, companionSelect;
	Player player;
	Companion companion;
	
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
	
	public NewMain()
	{
		playerSelect = new FourOptionMenu(0, 0, WIDTH, HEIGHT, new String[]{"Life", "Light", "Rock", "Water"}, 
				new String[] {"Life Wizard", "Light Wizard", "Rock Wizard", "Water Wizard"});
		companionSelect = new FourOptionMenu(0, 0, WIDTH, HEIGHT, new String[]{"cat", "dog", "lizard", "emu"}, 
				new String[]{"Cat", "Dog", "Lizard", "Emu"});
		
		this.addMouseListener(new MouseAdapter(){
			//TODO: Fix or reinvent this mess of if statements
			public void mouseClicked(MouseEvent e)
			{
				if(!outOfInitialMenus)
				{
					if(menuNum == 0)
					{
						player = new Player(xEntityPos[0], yEntityPos[0], playerSelect.optionSelected(e));
						menuNum = 1;
					}
					else if(menuNum == 1)
					{
						companion = new Companion(xEntityPos[1], yEntityPos[1], companionSelect.optionSelected(e));
						menuNum = 4;
						outOfInitialMenus = true;
					}	
				}
				/*else if(menuNum == 4)
				{
					player.setInCombatMenu(true);
					player.setAttackPower(e);
					if(player.getAttackPower() != -1)
					{
						menuNum = 2;
					}
				}*/
				//Used for Getting location of targets for player and companion
				/*else if(menuNum == 2)
				{
					//TODO: Change selected mob to target, let the player and companion classes handle it
					if(gameMenuNum == 0)
					{
						player.setAttackTarget(enemySelected.intSelected(e));
						gameMenuNum = 1;
						playerTurn = false;
					} 
					else if(gameMenuNum == 1)
					{
						companion.setAttackTarget(enemySelected.intSelected(e));
						menuNum = 3;
						past4 = false;
						playerTurn = true;
					}
				}	*/	
			}
		});
	}
	public void tick() //happens 60 times a second, things happen
	{
		//DO NOT WAAANT
	} 
	
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