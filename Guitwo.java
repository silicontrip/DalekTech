import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

// two for 2d
public class Guitwo extends Cli implements MouseListener, MouseMotionListener, KeyListener {

	JFrame frame;
	JPanel statusPanel;
	JPanel mapPanel;
	JLabel statusLabel;
	JLabel mapLabel;
	
	BufferedImage statusImage;
	BufferedImage mapImage;
	
	Graphics2D statusGraphic;
	Graphics2D mapGraphic;
	
	public Guitwo(Map m) {
		
		super(m);

		frame = new JFrame();
		frame.setTitle("Dalek Battle");

		// TODO: listener class

		
		statusImage = new BufferedImage (160,480,BufferedImage.TYPE_INT_ARGB);
		mapImage = new BufferedImage (640,480,BufferedImage.TYPE_INT_ARGB);

		statusLabel  = new JLabel(new ImageIcon(statusImage));
		mapLabel  = new JLabel(new ImageIcon(mapImage));

		statusLabel.setFocusable(true);
		mapLabel.setFocusable(true);

		statusLabel.setMinimumSize(new Dimension(160,480));
		statusLabel.setMinimumSize(new Dimension(640,480));

		
		statusPanel = new JPanel();
		mapPanel = new JPanel();

		
		statusLabel.addMouseListener(this);
		mapLabel.addMouseListener(this);
		statusLabel.addKeyListener(this);
		mapLabel.addKeyListener(this);
		//statusLabel.addMouseMotionListener(this);
		//mapLabel.addMouseMotionListener(this);

		statusPanel.add(statusLabel);
		mapPanel.add(mapLabel);

		frame.getContentPane().add(BorderLayout.EAST,mapPanel);
		frame.getContentPane().add(BorderLayout.WEST,statusPanel);
		
		frame.setSize(800,480); // phone resolution
		frame.setVisible(true);
		
	}
	
	ArrayList<Integer> selectFactoryDaleks (ArrayList<Dalek> dalekList) {
		while (true) { 	
			try {
				Thread.sleep(10000); 
			} catch (InterruptedException ie) { ; }
		}
		
	}
	
	public void mouseReleased(MouseEvent e) { 		
		System.out.println("Action: " + e);
	}
	
	public void mousePressed(MouseEvent e) { 		
		System.out.println("Action: " + e);
	} 

	public void mouseEntered(MouseEvent e)
	{
		System.out.println("Action: " + e);
		
		//e.getComponent().setFocusable(true);
	}
	
	public void mouseExited(MouseEvent e)
	{
		System.out.println("Action: " + e);
	}
	
	public void mouseClicked(MouseEvent e)
	{
		System.out.println("Action: " + e);
	}
	public void mouseDragged(MouseEvent e)  {
		System.out.println("Action: " + e);
	}
	
	public void mouseMoved(MouseEvent e) {
		System.out.println("Action: " + e);
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action: " + e);
	}
	
	public void keyPressed(KeyEvent e) {
		System.out.println("Action: " + e);
    }
	
	public void keyReleased(KeyEvent e) {
		System.out.println("Action: " + e);
    }
	public void keyTyped(KeyEvent e) {
		System.out.println("Action: " + e);
    }
	
	
}