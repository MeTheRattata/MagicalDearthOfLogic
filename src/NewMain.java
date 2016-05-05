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
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NewMain extends JPanel
{
	private static final long serialVersionUID = 2396845939365144090L;
	final static int WIDTH = 672;
	final static int HEIGHT = 480;
	private int[] xEntityPos = {32, 160, 512, 384};
	private int[] yEntityPos = {192, 32, 192, 32};
	double[][] fullScreenMenuBounds = {{0, 0, 336, 240}, {336, 0, 672, 240}, {0, 240, 336, 480}, {336, 240, 672, 480}};
	private FourOptionMenu playerSelect, companionSelect;
	private Player player;
	private Companion companion;
	private int menuNum = 1;
	private boolean attackReady = false, outOfInitialMenus = false, gameOver = false;
	private Slime[] enemies = new Slime[2];
	private ArrayList<Entity> entities = new ArrayList<Entity>(4);
	
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Magical Dearth of Logic");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		NewMain panel = new NewMain();
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
		playerSelect.activate();
		
		enemies[0] = new Slime(xEntityPos[2],yEntityPos[2],(int)(Math.random()*3) + 1);
		enemies[1] = new Slime(xEntityPos[3],yEntityPos[3],(int)(Math.random()*3) + 1);
		//Allow enemies to be constructed deactivated
		enemies[0].deActivate();
		enemies[1].deActivate();
		
		this.addMouseListener(new MouseAdapter()
		{
			//TODO: Fix or reinvent this mess of if statements
			public void mouseClicked(MouseEvent e)
			{
				//Menus stay active until they receive acceptable input.
				//
				switch (menuNum)
				{
				//Player Select menu
				case 1: player = new Player(xEntityPos[0], yEntityPos[0], playerSelect.optionSelected(e));
						entities.add(player);
						playerSelect.deActivate(); 
						companionSelect.activate();
						menuNum = 2;
						break;
				
				//Companion select menu
				case 2: companion = new Companion(xEntityPos[1], yEntityPos[1], companionSelect.optionSelected(e));
						entities.add(companion);
						entities.add(enemies[0]);
						entities.add(enemies[1]);
						companionSelect.deActivate();
						player.moveSelect.activate();
						enemies[0].activate();
						enemies[1].activate();
						menuNum = 3;
						outOfInitialMenus = true;
						break;
					
				//Attack select menu for player
				case 3: player.setAttackPower(e);
						if(!player.moveSelect.isActive() && player.healSelect.isActive())
							menuNum = 4;
						else if(!player.moveSelect.isActive() && player.enemySelect.isActive())
							menuNum = 5;
						break;
				
				//Heal target select for player
				case 4: player.setAttackTarget(e);
						if(!player.healSelect.isActive())
						{
							menuNum = 6;
							companion.moveSelect.activate();
						}
						break;
						
				//Combat target select for player
				case 5: player.setAttackTarget(e);
						if(!player.enemySelect.isActive())
						{
							menuNum = 6;
							companion.moveSelect.activate();
						}	
						break;
				
				//Move select for companion
				case 6: companion.setAttackPower(e);
						if(!companion.moveSelect.isActive())
							menuNum = 7;
						break;
				
				//Combat target select for companion
				case 7: companion.setAttackTarget(e);
						if(!companion.enemySelect.isActive())
						{
							menuNum = 3;
							player.moveSelect.activate();
							attackReady = true;
						}
						break;	
				}
				//End Switch case
				
				if(attackReady)
				{
					for(int i = 0; i < entities.size(); i++)
					{
						if(i < 2) //Id of player and companion
						{
							if(entities.get(i).getAttackTarget() == 4)
							{
								entities.get(0).takeDamage(entities.get(i).getAttackPower());
								entities.get(1).takeDamage(entities.get(i).getAttackPower());
							}
							else
							{
								if(entities.get(entities.get(i).getAttackTarget()).takeDamage(entities.get(i).getAttackPower()))
									entities.set(entities.get(i).getAttackTarget(), new Slime(xEntityPos[entities.get(i).getAttackTarget()],
										yEntityPos[entities.get(i).getAttackTarget()],(int)(Math.random()*3) + 1));
							}
						}
						else //Enemy ids
						{
							entities.get(i).setRandomAttackTarget();
							entities.get(entities.get(i).getAttackTarget()).takeDamage(entities.get(i).getAttackPower());
						}
					}
					
					if(player.getHealth() <= 0)
						gameOver = true;
					else if(companion.getHealth() <= 0)
						gameOver = true;
					
					attackReady = false;
				}
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
		
		//Main paint loop
		if(gameOver)
		{
			drawCenteredString(g, "Game Over", new Rectangle(0, 0, 672, 480), font);
		}
		else if(outOfInitialMenus)
		{
			for(int i = 0; i < entities.size(); i++)
				entities.get(i).paintComponent(g);
		}
		else //Not out of initial menus
		{
			if(playerSelect.isActive())
				playerSelect.paintComponent(g);
			else if(companionSelect.isActive())
				companionSelect.paintComponent(g);
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