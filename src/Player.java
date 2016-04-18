import java.awt.Color;
import java.awt.Graphics;

public class Player extends Entity
{
	int magicAttack = 40;
	int mana;
	int maxMana;
	boolean isMagicAttack = false;
	
	public Player(double xPos, double yPos, String type) 
	{
		//Players start with a health of 100 and an attack power of 20
		//Name is specified upon player select, is "wizard" + "Rock", "Life", "Light" or "Water"
		super(xPos, yPos, 100, "wizard" + type, 20);
		mana = 50;
		maxMana = mana;
	}
	public int getAttack()
	{
		if(isMagicAttack)
		{
			isMagicAttack = false;
			return super.getAttack() * 2;
		}
		return super.getAttack();
	}
	public void setMagicAttack(boolean bool)
	{
		isMagicAttack = bool;
	}
	public void paintComponent(Graphics g)
	{
		//multiplied by 256 to make image 4 times larger
		g.drawImage(getImage(), (int)getX(), (int)getY(), 128, 128, null);
		
		//Draw Health Bar
		g.setColor(Color.RED);
		g.fillRect((int)getX(), (int)getY() + 132, 128, 8);
		g.setColor(Color.GREEN);
		double barLength =  ((double) getHealth() / getMaxHealth()) * 128;
		g.fillRect((int)getX(), (int)getY() + 132, (int) barLength, 8);
		
		//draw mana bar
		g.setColor(Color.WHITE);
		g.fillRect((int)getX(), (int)getY() + 140, 128, 8);
		g.setColor(Color.BLUE);
		double manaBarLength =  ((double) mana / maxMana) * 128;
		g.fillRect((int)getX(), (int)getY() + 140, (int) manaBarLength, 8);
	}
	public int getMana() 
	{
		return mana;
	}
	public void refillMana()
	{
		mana = maxMana;
	}
}