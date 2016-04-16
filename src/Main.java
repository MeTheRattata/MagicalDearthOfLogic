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
	private ArrayList<Entity> entities;
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Magical Dearth of Logic");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Main panel = new Main();
		frame.getContentPane().setPreferredSize(new Dimension(512, 512));
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
		entities.add(new Companion(0,0,"dog"));
		entities.add(new Player(0,256,"Rock"));
	}
	
	public void tick() //happens 60 times a second, things happen
	{
		
	}
	public void paintComponent(Graphics g)//already happens forever and ever
	{
		/*BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/cat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Draws images 8 times larger, factor this into your shit
		g.drawImage(image, 0, 0, 256, 256, null); */
		for(int i = 0; i < entities.size(); i++)
			entities.get(i).paintComponent(g);
	}
}