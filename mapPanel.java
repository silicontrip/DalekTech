
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class mapPanel extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener {

	
	double scale;
	double dalekScale = 0.5;
	int xpos, ypos;
	Image map;
	Map gridMap;
	int startx, starty;

	
	
	ArrayList<Position> dalekImagePosition;
	ArrayList<Image> dalekImage;
	
	public static final int mapPanelWidth = 640;
	public static final int mapPanelHeight = 480;

	
	public mapPanel (Image map, Map m) {
		super();
		this.map = map;
		this.gridMap = m;
		
		dalekImagePosition = new ArrayList<Position>();
		dalekImage = new ArrayList<Image>();

		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addMouseListener(this);

		scale = 1;
		xpos = 0;
		ypos = 0;
		
	}
			
	Map getMap() { return gridMap; }
	
	
	int calX (int x) {
		return (int)((getMap().getRegScaleX() *x + getMap().getRegTLX()) * scale) + xpos;
	}
	
	int calY (int y) {
		return (int)((getMap().getRegScaleY() *y + getMap().getRegTLY()) * scale) + ypos;
	}
	
	
	void addArray(Image i, Position p) {
		dalekImagePosition.add(p);
		dalekImage.add(i);
	}
	void removeArray (int i) {
		dalekImagePosition.remove(i);
		dalekImage.remove(i);
	}
	int sizeArray () {
		
		if (dalekImagePosition.size() != dalekImage.size() ) {
			System.out.println ("mapPanel: internal inconsistancy error\n");
		}
		return dalekImagePosition.size();
	}
	public Dimension getPreferredSize() {
        return new Dimension(mapPanelWidth,mapPanelHeight);
    }

	int getMapHeight() { return (int)(map.getHeight(null) * scale); }
	int getMapWidth() { return (int)(map.getWidth(null) * scale); }

	void drawDalekAt (Graphics g, Image dalek, int x, int y, int w) {
		
		int h = (int)  (dalek.getHeight(null) * w / dalek.getWidth(null)  );

		BufferedImage thumbImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(dalek, 0, 0, w, h, null);
		
		g.drawImage(thumbImage,x-w/2,y-h/2,null);
		
	}
	
	
	public void paintComponent (Graphics g) {
		
		super.paintComponent(g);       

		int h = getMapHeight();
		int w = getMapWidth();

		BufferedImage thumbImage = new BufferedImage(mapPanelWidth, mapPanelHeight, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(map, xpos, ypos, w, h, null);
		g.drawImage(thumbImage,0,0,null);

		for (int n=0; n < this.sizeArray(); n++) {
			Position pos= dalekImagePosition.get(n);
			this.drawDalekAt(g,dalekImage.get(n),this.calX(pos.getX()), this.calY(pos.getY()), (int)(getMap().getRegScaleX() * scale * dalekScale));			
		}
		
		
	}
	
	boolean imageBound (double scale, int x, int y) {
		return x<=0 && y<=0 && map.getHeight(null) * scale+y >= mapPanelHeight && map.getWidth(null) * scale+x >= mapPanelWidth;
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
		
		int txpos = xpos + (e.getX() - startx);
		int typos = ypos + (e.getY() - starty);
		
						
		if (imageBound(this.scale,txpos,typos)) {
		xpos = txpos;
		ypos = typos;
		}
	//	System.out.println("xpos: " + xpos + ", ypos: " + ypos + "startx: " + startx + ", starty: " + starty);
						    
		
		startx = e.getX();
		starty = e.getY();
		
		this.repaint();
		
	}
	
	public void mouseMoved(MouseEvent e) {
		// System.out.println("Action: " + e);
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		//System.out.println("Action: " + e);
		double tempscale;
		tempscale = scale * (1.0 + ( e.getWheelRotation() / 60.0 ));
		
		
		if (imageBound(tempscale,xpos,ypos) ) {
			scale = tempscale;
		}
	//	System.out.println ("Old scale: " + scale + ", new scale : " + tempscale);

		this.repaint();

	}

	
}