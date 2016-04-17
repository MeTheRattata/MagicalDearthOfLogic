import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel
{
	//0 = pos of menu string 1, etc, etc, etc
	private int[] xPositions = {84, 396, 84, 396};
	private int[] yPositions = {120, 120, 360, 360};
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private boolean outOfInitialMenus = false;
	//MenuNum : 0 = player select, 1 = companion select, 2 = game menu
	int menuNum = 0;
	boolean playerTurn = true;
	String[] menuTexts = {"Life Wizard", "Light Wizard", "Earth Wizard", "Water Wizard", "Cat", "Dog", "Lizard", "Emu"};
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Magical Dearth of Logic");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Main panel = new Main();
		frame.getContentPane().setPreferredSize(new Dimension(672, 480));
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
		entities = new ArrayList<Entity>();
		entities.add(new Player(32,192,"Life")); //starts with default name
		entities.add(new Companion(160,32,"cat")); //starts with default name
		entities.add(new Slime(512,192,2));
		entities.add(new Slime(384,32,3));
		entities.get(0).takeDamage(50);
		
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e)
			{
				
				if(menuNum == 0)
				{
					if(e.getX() < 336 && e.getY() < 240) //top left
						entities.get(0).setName("wizardLife");
					else if(e.getX() > 336 && e.getY() < 240) //top right
						entities.get(0).setName("wizardLight");
					else if(e.getX() < 336 && e.getY() > 240) //bottom left
						entities.get(0).setName("wizardRock");
					else if(e.getX() > 336 && e.getY() > 240) //bottom right
						entities.get(0).setName("wizardWater");
					menuNum = 1;
				}
				else if(menuNum == 1)
				{
					if(e.getX() < 336 && e.getY() < 240) //top left
						entities.get(1).setName("cat");
					else if(e.getX() > 336 && e.getY() < 240) //top right
						entities.get(1).setName("dog");
					else if(e.getX() < 336 && e.getY() > 240) //bottom left
						entities.get(1).setName("lizard");
					else if(e.getX() > 336 && e.getY() > 240) //bottom right
						entities.get(1).setName("emu");
					menuNum = 2;
					outOfInitialMenus = true;
				}	
			}
		});
	}
	
	public void tick() //happens 60 times a second, things happen
	{
		
	}
	public void paintComponent(Graphics g)//already happens forever and ever
	{
		if(!outOfInitialMenus)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0,0,672,480);
			g.setColor(Color.BLACK);
			java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
			g2.setStroke(new java.awt.BasicStroke(4)); // thickness of 3.0f
			g2.setColor(Color.BLACK);
			g2.drawLine(0,240,672,240);
			g2.drawLine(336, 0, 336, 480);
			
			String str;
			Font font = new Font("Arial", Font.BOLD, 32);
			g.setFont(font);
			
			if(menuNum == 0)
			{
				for(int i = 0; i < 4; i++)
				{
					str = menuTexts[i];
					g.drawString(str, xPositions[i], yPositions[i]);
				}
			}
			else
			{
				for(int i = 4; i < 8; i++)
				{
					str = menuTexts[i];
					g.drawString(str, xPositions[i - 4], yPositions[i - 4]);
				}
			}
		}
		else
		{
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("res/background.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Draws images 4 times larger, factor this into the other classes draw methods
			g.drawImage(image, 0, 0, 672, 352, null);
			
			for(int i = 0; i < entities.size(); i++)
				entities.get(i).paintComponent(g);
		}
	}
}