import java.awt.Dimension;
import java.awt.Graphics;
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
	//0 = player positions, 1 = companion, 2 = enemy 1, 3 = enemy 2
	private int[] xPositions = {32, 160, 512, 384};
	private int[] yPositions = {192, 32, 192, 32};
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Magical Dearth of Logic");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Main panel = new Main();
		frame.getContentPane().setPreferredSize(new Dimension(672, 352));
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
		entities.add(new Player(32,192,"Rock"));
		entities.add(new Companion(160,32,"dog"));
		entities.add(new Slime(512,192,2));
		entities.add(new Slime(384,32,3));
		entities.get(0).takeDamage(50);
		
	}
	
	public void tick() //happens 60 times a second, things happen
	{
		
	}
	public void paintComponent(Graphics g)//already happens forever and ever
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