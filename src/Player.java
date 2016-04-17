import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity
{
	int magicAttack = 40;
	int mana;
	int maxMana;
	public Player(double xPos, double yPos, String type) 
	{
		//Players start with a health of 100 and an attack power of 20
		//Type is specified upon player select, is "Rock", "Life", "Light" or "Water"
		super(xPos, yPos, 100, "wizard" + type, 20);
		mana = 50;
		maxMana = mana;
	}
	public int getMagicAttack()
	{
		//magic attack needs 20 mana, if player doesnt have the mana, attack will do 0 damage
		if(mana < 20)
			return 0;
		else
		{
			mana -= 20;
			return magicAttack;
		}
			
	}
	public void paintComponent(Graphics g)
	{
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/" + imagePath + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//multiplied by 256 to make image 4 times larger
		g.drawImage(image, (int)getX(), (int)getY(), 128, 128, null);
		
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
}