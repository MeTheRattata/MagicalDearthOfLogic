import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
	//MenuNum : 0 = player select, 1 = companion select, 2 = game menu
	int menuNum = 0; //which menu you're in
	//0 = select player, 1 = select companion, 2 = select attack target, 3 = temp to let tick method do damage and things,
	//4 = select attack
	
	boolean playerTurn = true;
	String[] menuTexts = {"Life Wizard", "Light Wizard", "Earth Wizard", "Water Wizard", "Cat", "Dog", "Lizard", "Emu"};
	int gameMenuNum = 0; //Which game menu you're in, 0 = select enemy to attack with player, 1 = select
	//an enemy to attack with companion
	String[] gameMenuTexts = {"Select an enemy to target with wizard:", "Select an enemy to target with companion:", 
			"Magic Attack", "Melee Attack"};
	int selectedMobPlayer = -1;
	int selectedMobCompanion = -1;
	boolean past4 = false;
	int kills = 0;
	//Boundaries for menus that take up the entire screen, used to create Menu objects for such menus
	double[][] fullScreenMenuBounds = new double[][]{{0, 0, 336, 240}, {336, 0, 672, 240}, {0, 240, 336, 480}, {336, 240, 672, 480}};
	
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
		
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e)
			{
				if(!outOfInitialMenus)
				{
					
					Menu playerSelect = new Menu(fullScreenMenuBounds, new String[]{"Life", "Light", "Rock", "Water"}, 
										new String[] {"Life Wizard", "Light Wizard", "Rock Wizard", "Water Wizard"});
					player = new Player(xEntityPos[0], yEntityPos[0], playerSelect.optionSelected(e));
					Menu companionSelect = new Menu(fullScreenMenuBounds, new String[]{"cat", "dog", "lizard", "emu"}, 
										   new String[]{"Cat", "Dog", "Lizard", "Emu"});
					companion = new Companion(xEntityPos[1], yEntityPos[1], companionSelect.optionSelected(e));
					menuNum = 4;
					outOfInitialMenus = true;	
				}
				if(menuNum == 4 && !past4)
				{
					//TODO: Change Menu to be able to accept booleans as options for 2 slot menus
					Menu isMagicSelect = new Menu(new double[][] {{0, 352, 336, 480}, {336, 352, 672, 480}}, 
							new String[]{"true", "false"}, new String[]{"Magic Attack", "Melee Attack"});
					int intSelected = isMagicSelect.intSelected(e);
					//TODO: Fix player select selecting melee if the click wasn't inside the menu box
					if(intSelected == 0)
						player.setMagicAttack(true);
						
					menuNum = 2;
					past4 = true;
				}
				//Used for Getting location of targets for player and companion
				else if(past4 && menuNum == 2)
				{
					//TODO: Change selected mob to target, let the player and companion classes handle it
					Menu enemySelected = new Menu(new double[][]{{xEntityPos[2], yEntityPos[2], xEntityPos[2] + 128, yEntityPos[2] + 128},
					{xEntityPos[3], yEntityPos[3], xEntityPos[3] + 128, yEntityPos[3] +128}}, new String[]{"Enemy1", "Enemy2"}, new String[]{"Enemy1", "Enemy2"});
					if(gameMenuNum == 0)
					{
						selectedMobPlayer = enemySelected.intSelected(e);
						gameMenuNum = 1;
						playerTurn = false;
					} 
					else if(gameMenuNum == 1)
					{
						selectedMobCompanion = enemySelected.intSelected(e);
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
			if(selectedMobPlayer != -1 && selectedMobCompanion != -1)
			{
				//Do damage to enemy targeted by player
				if(enemies[selectedMobPlayer].takeDamage(player.getAttack()))
				{
					kills++;
					player.refillMana();
					enemies[selectedMobPlayer] = new Slime(xEntityPos[selectedMobPlayer + 2],yEntityPos[selectedMobPlayer + 2],(int)(Math.random()*3) + 1);
				}
				
				//Do damage to enemy targeted by companion
				if(enemies[selectedMobCompanion].takeDamage(companion.getAttack()))
				{
					kills++;
					player.refillMana();
					enemies[selectedMobCompanion] = new Slime(xEntityPos[selectedMobCompanion + 2],yEntityPos[selectedMobCompanion + 2],(int)(Math.random()*3) + 1);
				}
				
				selectedMobPlayer = -1;
				selectedMobCompanion = -1;
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
			java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
			g2.setStroke(new java.awt.BasicStroke(4)); // thickness of 3.0f
			g2.setColor(Color.BLACK);
			g2.drawLine(0,240,672,240);
			g2.drawLine(336, 0, 336, 480);
			
			String str;
			Font font = new Font("Arial", Font.BOLD, 32);
			g.setFont(font);
			
			//Draw Player Select
			if(menuNum == 0)
			{
				for(int i = 0; i < 4; i++)
				{
					str = menuTexts[i];
					g.drawString(str, xMenuPos[i], yMenuPos[i]);
				}
			}
			//Draw Companion Select
			else if(menuNum == 1)
			{
				for(int i = 4; i < 8; i++)
				{
					str = menuTexts[i];
					g.drawString(str, xMenuPos[i - 4], yMenuPos[i - 4]);
				}
			}
		}
		else
		{
			//Clean background, set up for game menu printing
			g.setColor(Color.BLACK);
			Font font = new Font("Arial", Font.BOLD, 32);
			g.setFont(font);
			
			//draw background
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("res/background.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 0, 0, 672, 480, null);
			g.drawString("Kills: " + kills, 32, 32);
			//Draw player and companion
			player.paintComponent(g);
			companion.paintComponent(g);
			for(int i = 0; i < enemies.length; i++)
				enemies[i].paintComponent(g);
				
			if(menuNum == 4)
			{
				g.setColor(Color.BLACK);
				g.setFont(font);
				java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
				g2.setStroke(new java.awt.BasicStroke(4)); // thickness of 3.0f
				g2.setColor(Color.BLACK);
				g2.drawLine(336, 354, 336, 480);
				g.drawString(gameMenuTexts[2], 64, 434);
				g.drawString(gameMenuTexts[3], 400, 432);
				
			}
			else if(menuNum == 2) //Enemy select for attacks
			{
				g.setColor(Color.BLACK);
				g.setFont(font);
				String str = "";
				//select with both wizard and companion
				
				//Draw menu string for selection
				if(selectedMobPlayer == -1)
					str = gameMenuTexts[0];
				else if(selectedMobCompanion == -1)
					str = gameMenuTexts[1];
				g.drawString(str, 16, 424);
				
				//draw selection squares around monsters
				try {
					image = ImageIO.read(new File("res/redSelectionBorder.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				for(int j = 2; j < 4; j++)
					g.drawImage(image, xEntityPos[j], yEntityPos[j], 128, 128, null);
			}
			
		}
	}
}