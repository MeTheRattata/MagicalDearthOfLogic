import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private int[] xMenuPos = {84, 396, 84, 396};
	private int[] yMenuPos = {120, 120, 360, 360};
	private int[] xEntityPos = {32, 160, 512, 384};
	private int[] yEntityPos = {192, 32, 192, 32};
	private ArrayList<Entity> entities = new ArrayList<Entity>();
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
	boolean isManaAttack = false;
	boolean past4 = false;
	int[] hurting = {0,0,0,0};
	int kills = 0;
	
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
		entities.add(new Player(xEntityPos[0],yEntityPos[0],"Life")); //starts with default name
		entities.add(new Companion(xEntityPos[1],yEntityPos[1],"cat")); //starts with default name
		entities.add(new Slime(xEntityPos[2],yEntityPos[2],(int)(Math.random()*3) + 1));
		entities.add(new Slime(xEntityPos[3],yEntityPos[3],(int)(Math.random()*3) + 1));
		
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e)
			{
				if(!outOfInitialMenus)
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
						menuNum = 4;
						outOfInitialMenus = true;
					}	
				}
				else if(menuNum == 4 && !past4)
				{
					System.out.print("inFour");
					if(e.getX() > 0 && e.getX() < 336 && e.getY() > 352 && e.getY() < 480)
					{
						isManaAttack = true;
						past4 = true;
						menuNum = 2;
					}
						
					else if(e.getX() > 336 && e.getX() < 672 && e.getY() > 352 && e.getY() < 480)
					{
						isManaAttack = false;
						past4 = true;
						menuNum = 2;
					}	
				}
				//Used for Getting location of targets for player and companion
				else if(past4 && menuNum == 2)
				{
					System.out.println("In main");
					
					if(gameMenuNum == 0)
					{
						System.out.println("In wizard");
						for(int i = 2; i < 4; i++)
							if(e.getX() > xEntityPos[i] && e.getX() < xEntityPos[i] + 128 && 
							   e.getY() > yEntityPos[i] && e.getY() < yEntityPos[i] + 128)
								selectedMobPlayer = i;
						
						gameMenuNum = 1;
						playerTurn = false;
					}
					else if(gameMenuNum == 1)
					{
						System.out.println("In companion");
						for(int i = 2; i < 4; i++)
							if(e.getX() > xEntityPos[i] && e.getX() < xEntityPos[i] + 128 && 
							   e.getY() > yEntityPos[i] && e.getY() < yEntityPos[i] + 128)
								selectedMobCompanion = i;
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
			System.out.println("In ticks");
			if(selectedMobPlayer != -1 && selectedMobCompanion != -1)
			{
				//do damage to enemies with both player and companion
				if(isManaAttack)
				{
					//hopefully overridden by player
					if(((Player) entities.get(0)).getMana() > 20)
						hurting[selectedMobPlayer] += 30;
					if(entities.get(selectedMobPlayer).takeDamage(entities.get(0).getMagicAttack()))
					{
						kills++;
						((Player) entities.get(0)).refillMana();
						entities.set(selectedMobPlayer, new Slime(xEntityPos[selectedMobPlayer],yEntityPos[selectedMobPlayer],(int)(Math.random()*3) + 1));
					}
						
				}
				else
				{
					hurting[selectedMobPlayer] += 30;
					if(entities.get(selectedMobPlayer).takeDamage(entities.get(0).getAttack()))
					{
						kills++;
						((Player) entities.get(0)).refillMana();
						entities.set(selectedMobPlayer, new Slime(xEntityPos[selectedMobPlayer],yEntityPos[selectedMobPlayer],(int)(Math.random()*3) + 1));
					}
						
				}
				
				hurting[selectedMobCompanion] += 30;
				if(entities.get(selectedMobCompanion).takeDamage(entities.get(1).getAttack()))
				{
					kills++;
					((Player) entities.get(0)).refillMana();
					entities.set(selectedMobCompanion, new Slime(xEntityPos[selectedMobCompanion],yEntityPos[selectedMobCompanion],(int)(Math.random()*3) + 1));
				}
					
			
				System.out.println("Mob1: " + entities.get(selectedMobPlayer).getHealth());
				System.out.println("Mob2: " + entities.get(selectedMobCompanion).getHealth());
				
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
			//draw entities
			for(int i = 0; i < entities.size(); i++)
				entities.get(i).paintComponent(g);
			if(hurting[0] > 0)
			{
				try {
					image = ImageIO.read(new File("res/wizardDmg.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, xEntityPos[0], yEntityPos[0], 128, 128, null);
				hurting[0]--;
			}	
			if(hurting[1] > 0)
			{
				try {
					image = ImageIO.read(new File("res/" + entities.get(1) + "Dmg.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, xEntityPos[1], yEntityPos[1], 128, 128, null);
				hurting[1]--;
			}
			if(hurting[2] > 0)
			{
				try {
					image = ImageIO.read(new File("res/slime/slime" + ((Slime) entities.get(2)).getSize() + "Dmg.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, xEntityPos[2] + ((Slime) entities.get(2)).getDisplacement(), yEntityPos[2] + ((Slime) entities.get(2)).getDisplacement(), 
						((Slime) entities.get(2)).getResize(), ((Slime) entities.get(2)).getResize(), null);
				hurting[2]--;
			}
			if(hurting[3] > 0)
			{
				try {
					image = ImageIO.read(new File("res/slime/slime" + ((Slime) entities.get(3)).getSize() + "Dmg.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, xEntityPos[3] + ((Slime) entities.get(3)).getDisplacement(), yEntityPos[3] + ((Slime) entities.get(3)).getDisplacement(), 
						((Slime) entities.get(3)).getResize(), ((Slime) entities.get(3)).getResize(), null);
				hurting[3]--;
			}
				
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