import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel
{
	//0 = pos of menu string 1, etc, etc, etc
	private int[] xMenuPos = {84, 396, 84, 396};
	private int[] yMenuPos = {120, 120, 360, 360};
	private int[] xEntityPos = {32, 160, 512, 384};
	private int[] yEntityPos = {192, 32, 192, 32};
	private Player player;
	private Companion companion;
	private Slime[] enemies = new Slime[2];
	private boolean outOfInitialMenus = false;
	int menuNum = 0; //which menu you're in
	//0 = select player, 1 = select companion, 2 = select attack target, 3 = temp to let tick method do damage and things,
	//4 = select attack
	boolean playerTurn = true;
	String[] menuTexts = {"Life Wizard", "Light Wizard", "Earth Wizard", "Water Wizard", "Cat", "Dog", "Lizard", "Emu"};
	int gameMenuNum = 0; //Which game menu you're in, 0 = select enemy to attack with player, 1 = select
	//an enemy to attack with companion
	String[] gameMenuTexts = {"Select an enemy to target with wizard:", "Select an enemy to target with companion:", 
			"Magic Attack", "Melee Attack"};
	boolean past4 = false;
	int kills = 0;
	//Boundaries for menus that take up the entire screen, used to create Menu objects for such menus
	double[][] fullScreenMenuBounds = new double[][]{{0, 0, 336, 240}, {336, 0, 672, 240}, {0, 240, 336, 480}, {336, 240, 672, 480}};
	Menu playerSelect = new Menu(fullScreenMenuBounds, new String[]{"Life", "Light", "Rock", "Water"}, 
			new String[] {"Life Wizard", "Light Wizard", "Rock Wizard", "Water Wizard"});
	Menu companionSelect = new Menu(fullScreenMenuBounds, new String[]{"cat", "dog", "lizard", "emu"}, 
			new String[]{"Cat", "Dog", "Lizard", "Emu"});
	Menu isMagicSelect = new Menu(new double[][] {{0, 352, 336, 480}, {336, 352, 672, 480}}, 
			new String[]{"true", "false"}, new String[]{"Magic Attack", "Melee Attack"});
	Menu enemySelected = new Menu(new double[][]{{xEntityPos[2], yEntityPos[2], xEntityPos[2] + 128, yEntityPos[2] + 128},
		{xEntityPos[3], yEntityPos[3], xEntityPos[3] + 128, yEntityPos[3] +128}}, new String[]{"Enemy1", "Enemy2"}, new String[]{"Enemy1", "Enemy2"});
	BufferedImage selectionBorder, background;
	
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
		enemies[0] = new Slime(xEntityPos[2],yEntityPos[2],(int)(Math.random()*3) + 1);
		enemies[1] = new Slime(xEntityPos[3],yEntityPos[3],(int)(Math.random()*3) + 1);
		try {
			background = ImageIO.read(new File("res/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			selectionBorder = ImageIO.read(new File("res/redSelectionBorder.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
				else if(menuNum == 4)
				{
					//TODO: Change Menu to be able to accept booleans as options for 2 slot menus
					int intSelected = isMagicSelect.intSelected(e);
					if(intSelected == 0)
					{
						player.setMagicAttack(true);
						menuNum = 2;
					}
					else if(intSelected == 1)
					{
						menuNum = 2;
					}
				}
				//Used for Getting location of targets for player and companion
				else if(menuNum == 2)
				{
					//TODO: Change selected mob to target, let the player and companion classes handle it
					if(gameMenuNum == 0)
					{
						player.setTarget(enemySelected.intSelected(e));
						gameMenuNum = 1;
						playerTurn = false;
					} 
					else if(gameMenuNum == 1)
					{
						companion.setTarget(enemySelected.intSelected(e));
						menuNum = 3;
						past4 = false;
						playerTurn = true;
					}
				}		
			}
		});
	}
	public void tick() //happens 60 times a second, things happen
	{
		//if both player and mob have selected an enemy to fight
		if(menuNum == 3)
		{
			//If both the target of the player and the target of the companion are valid
			//do damage to enemies with both player and companion
			if(player.getTarget() != -1 && companion.getTarget() != -1)
			{
				//Do damage to enemy targeted by player
				if(player.doDamage(enemies[player.getTarget()]))
				{
					kills++;
					player.refillMana();
					enemies[player.getTarget()] = new Slime(xEntityPos[player.getTarget() + 2],yEntityPos[player.getTarget() + 2],(int)(Math.random()*3) + 1);
				}
				
				//Do damage to enemy targeted by companion
				if(companion.doDamage(enemies[companion.getTarget()]))
				{
					kills++;
					player.refillMana();
					enemies[companion.getTarget()] = new Slime(xEntityPos[companion.getTarget() + 2],yEntityPos[companion.getTarget() + 2],(int)(Math.random()*3) + 1);
				}
				
				Random generator = new Random();
				player.setTarget(-1);
				companion.setTarget(-1);
				for(int i = 0; i < 2; i++)
				{
					if(generator.nextDouble() > 0.5)
						enemies[i].doDamage(player);
					else
						enemies[i].doDamage(companion);
				}
			}
			
			menuNum = 4; //Go back into selecting wizards attack
			gameMenuNum = 0; //Go back to player attacking
		}
	}
	
	//Draws images 4 times larger, factor this into the other classes draw methods
	public void paintComponent(Graphics g)//already happens forever and ever
	{
		if(!outOfInitialMenus)
		{
			//Prep to draw menus
			g.setColor(Color.WHITE);
			g.fillRect(0,0,672,480);
			g.setColor(Color.BLACK);
			
			if(menuNum == 0)
				playerSelect.paintComponent(g);
			else if(menuNum == 1)
				companionSelect.paintComponent(g);
		}
		else
		{
			//Clean background, set up for game menu printing
			g.setColor(Color.BLACK);
			Font font = new Font("Arial", Font.BOLD, 32);
			g.setFont(font);
			
			//draw background
			g.drawImage(background, 0, 0, 672, 480, null);
			g.drawString("Kills: " + kills, 32, 32);
			//Draw player and companion
			player.paintComponent(g);
			companion.paintComponent(g);
			for(int i = 0; i < enemies.length; i++)
				enemies[i].paintComponent(g);
				
			if(menuNum == 4)
				isMagicSelect.paintComponent(g);
			else if(menuNum == 2) //Enemy select for attacks
			{
				g.setColor(Color.BLACK);
				g.setFont(font);
				String str = "";
				//select with both wizard and companion
				
				//Draw menu string for selection
				if(player.getTarget() == -1)
					str = gameMenuTexts[0];
				else if(companion.getTarget() == -1)
					str = gameMenuTexts[1];
				g.drawString(str, 16, 424);
				
				//draw selection squares around monsters
				for(int j = 2; j < 4; j++)
					g.drawImage(selectionBorder, xEntityPos[j], yEntityPos[j], 128, 128, null);
			}
			
		}
	}
}