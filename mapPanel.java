
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class mapPanel extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener {

	
	double scale;
	int xpos, ypos;
	Image map;
	
	int startx, starty;
	
	public static final int mapPanelWidth = 640;
	public static final int mapPanelHeight = 480;

	
	public mapPanel (Image map) {
		super();
		this.map = map;
		
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addMouseListener(this);

		scale = 0.5;
		xpos = 0;
		ypos = 0;
		
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(mapPanelWidth,mapPanelHeight);
    }

	
	public void paintComponent (Graphics g) {
		
		super.paintComponent(g);       

		int h = (int)(map.getHeight(null) * scale);
		int w = (int)(map.getWidth(null) * scale);

		
		
		BufferedImage thumbImage = new BufferedImage(mapPanelWidth, mapPanelHeight, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(map, xpos, ypos, w, h, null);
		
		g.drawImage(thumbImage,0,0,null);
		
	}
	
	public void mouseReleased(MouseEvent e) { 		
		System.out.println("mouseReleased: " + e);
	}
	
	public void mousePressed(MouseEvent e) { 		
	//	System.out.println("mousePressed: " + e);
		
		startx = e.getX();
		starty = e.getY();
	} 
	
	public void mouseEntered(MouseEvent e)
	{
		System.out.println("mouseEntered: " + e);
		
		//e.getComponent().setFocusable(true);
	}
	
	public void mouseExited(MouseEvent e)
	{
		System.out.println("mouseExited: " + e);
	}
	
	public void mouseClicked(MouseEvent e)
	{
		System.out.println("mouseClicked: " + e);
	}
	public void mouseDragged(MouseEvent e)  {
		//System.out.println("mouseDragged: " + e);
		
		// need bounds checking.

		
		xpos += (e.getX() - startx);
		ypos += (e.getY() - starty);

	//	System.out.println("xpos: " + xpos + ", ypos: " + ypos + "startx: " + startx + ", starty: " + starty);
						    
		
		startx = e.getX();
		starty = e.getY();
		
		this.repaint();
		
	}
	
	public void mouseMoved(MouseEvent e) {
		System.out.println("Action: " + e);
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		System.out.println("Action: " + e);
	}

	
}