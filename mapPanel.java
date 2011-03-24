import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class mapPanel extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener, ActionListener {

	javax.swing.Timer timer;
	
	double scale;
	double dalekScale = 0.5;
	int xpos, ypos;
	Image map;
	Map gridMap;
	int startx, starty;
	Guitwo callback;

	Image posDalekImage = null;
	Position posDalek=null;

	int mouseState = 0;
	
	String moveDalekName=null;
	Position moveDalekPosition=null;
	double moveDalekX,moveDalekY;
	double moveDalekTargetX,moveDalekTargetY;
	double moveDalekCurrentX,moveDalekCurrentY;
	
	HashMap<String,Position> dalekImagePosition;
	HashMap<String,Image> dalekImage;
	
	public static final int mapPanelWidth = 640;
	public static final int mapPanelHeight = 480;

	
	public mapPanel (Image map, Map m, Guitwo ui) {
		super();
		this.map = map;
		this.gridMap = m;
		this.callback = ui;
		
		dalekImagePosition = new HashMap<String,Position>();
		dalekImage = new HashMap<String,Image>();

		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addMouseListener(this);

		scale = 1;
		xpos = 0;
		ypos = 0;
		
		timer = new javax.swing.Timer(10, this);
		timer.setInitialDelay(10);
		timer.setCoalesce(false);
		timer.setRepeats(true);
		
	}
			
	Map getMap() { return gridMap; }
	double getScale() { return scale; }
	
	double calX (double x) {
		
		//System.out.println("regScaleX: " + getMap().getRegScaleX() +", RegTLX: " + getMap().getRegTLX());
		
		return ((getMap().getRegScaleX() *x + getMap().getRegTLX()) * getScale()) + xpos;
	}
	
	double calY (double y) {
		return ((getMap().getRegScaleY() *y + getMap().getRegTLY()) * getScale()) + ypos;
	}
		
	void notifyDalek(String s, Image i, Position p) {
		if (dalekImagePosition.containsKey(s)) {
			// dalek exists 
			// animate to new position.
			moveDalekName = s;
			moveDalekPosition = p;
			double distance = dalekImagePosition.get(s).distanceTo(p);
			moveDalekCurrentX = 0;
			moveDalekCurrentY = 0;

			moveDalekTargetX = p.getSpatialX() -  dalekImagePosition.get(s).getSpatialX() ;
			moveDalekTargetY = p.getSpatialY() -  dalekImagePosition.get(s).getSpatialY();

			moveDalekX = moveDalekTargetX / (distance * 100);
			moveDalekY = moveDalekTargetY / (distance * 100);
			
			
			timer.start();
		} else {
			System.out.println("**** adding dalek at " + p +" ****");
				// simply add the new dalek
			dalekImagePosition.put(s,p);
			dalekImage.put(s,i);
			this.repaint();
		}
	}
	void removeArray (String s) {
		dalekImagePosition.remove(s);
		dalekImage.remove(s);
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
		drawDalekAt(g,dalek,x,y,w,0);
	}
	
	void drawDalekAt (Graphics g, Image dalek, int x, int y, int w,int dir) {
		
		if (x>0 && x< mapPanelWidth && y>0 && y < mapPanelHeight) {
		
		int h = (int)  (dalek.getHeight(null) * w / dalek.getWidth(null)  );

		BufferedImage thumbImage = new BufferedImage(h*2, h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		if (dir != 0 ) {
			AffineTransform newXform =  AffineTransform.getRotateInstance(Math.toRadians(dir * 60),h,h/2);
			// newXform.rotate(Math.toRadians(dir * 60));
			graphics2D.setTransform(newXform);
		}
		graphics2D.drawImage(dalek, h - w/2, 0, w, h, null);
		
		g.drawImage(thumbImage,x-h,y-h/2,null);
		}
	}
	
	void setNothing() {
		mouseState = 0;
	}
	
	void setPositionDalek() {
		mouseState = 1;
	}
		
	void setDirectDalek() {
		mouseState = 2;
	}
	
	boolean isPositionDalek() { return mouseState == 1; }
	boolean isDirectDalek() { return mouseState == 2; }
	boolean isNothing() { return mouseState == 0; }

	
	void positionDalek (Image dalek) {
	
		this.setPositionDalek();
		this.posDalekImage = dalek;
		this.posDalek = null;
		
	}
	
	void directDalek (Image dalek, Position pos) { 
		
		this.setDirectDalek();
		this.posDalekImage = dalek;
		this.posDalek = pos;
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

		Iterator<String> it = dalekImagePosition.keySet().iterator();
		
		while (it.hasNext()){
			
			String n = it.next();
			
			Position pos= dalekImagePosition.get(n);
			
			System.out.println("**** dalek " + n + " at " + pos +" ****");

			// This is for the dalek animated movement
			if (moveDalekName != null && moveDalekName.equals(n)) {
				this.drawDalekAt(g,dalekImage.get(n),
								 (int)this.calX(pos.getSpatialX()+moveDalekCurrentX), 
								 (int)this.calY(pos.getSpatialY()+moveDalekCurrentY), 
								 (int)(getMap().getRegScaleX() * scale * dalekScale),
								 pos.getDirection());
			} else {

				this.drawDalekAt(g,dalekImage.get(n),
								 (int)this.calX(pos.getSpatialX()), 
								 (int)this.calY(pos.getSpatialY()), 
								 (int)(getMap().getRegScaleX() * scale * dalekScale),
								 pos.getDirection());	
			
			}
		}
		
		if (this.posDalekImage != null && this.posDalek != null) {
			this.drawDalekAt(g,posDalekImage,(int)this.calX(this.posDalek.getSpatialX()), (int)this.calY(this.posDalek.getSpatialY()), (int)(getMap().getRegScaleY() * scale * dalekScale),this.posDalek.getDirection());		
		}

	}
	
	boolean imageBound (double scale, int x, int y) {
		return x<=0 && y<=0 && map.getHeight(null) * scale+y >= mapPanelHeight && map.getWidth(null) * scale+x >= mapPanelWidth;
	}
	
	public void actionPerformed(ActionEvent e) {

		// more dalek sliding
		if (moveDalekName != null) {
		
			moveDalekCurrentX += moveDalekX;
			moveDalekCurrentY += moveDalekY;

			if (moveDalekCurrentX == moveDalekTargetX) {
				dalekImagePosition.put(moveDalekName,moveDalekPosition);
				moveDalekCurrentX = 0;
				moveDalekCurrentY = 0;
				moveDalekPosition = null;
				moveDalekName = null;
				timer.stop();
			}
			this.repaint();	
		}
		
	}
		
	public void mouseReleased(MouseEvent e) { 		
	//	System.out.println("mouseReleased: " + e);
	}
	
	public void mousePressed(MouseEvent e) { 		
	//	System.out.println("mousePressed: " + e);
		
		if (isNothing()) {
			startx = e.getX();
			starty = e.getY();
		}
	} 
	
	public void mouseEntered(MouseEvent e)
	{
		// System.out.println("mouseEntered: " + e);
		
		//e.getComponent().setFocusable(true);
	}
	
	public void mouseExited(MouseEvent e)
	{
		// System.out.println("mouseExited: " + e);
	}
	
	public void mouseClicked(MouseEvent e)
	{
		// System.out.println("mouseClicked: " + e);
		
		if (isPositionDalek()) {
			if (posDalek != null) {

				this.setNothing();
				callback.setSelectedPosition(posDalek);
			
				this.posDalekImage = null;
				this.posDalek = null;
			}
		}
		if (isDirectDalek()) {
			// I want to merge this with the above code.
			/*
			double x = (( e.getX() - xpos ) / scale - getMap().getRegTLX() ) / getMap().getRegScaleX();
			double y = (( e.getY() - ypos ) / scale - getMap().getRegTLY() ) / getMap().getRegScaleY();
			Position p = new Position (x,y);
			this.posDalek.facePosition(p);
			*/
			this.setNothing();
			
			callback.setSelectedDirection(this.posDalek.getDirection()); 
			this.posDalekImage = null;
			this.posDalek = null;
			
			this.repaint();
						
		}
		
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
		
	//	System.out.println("mouseState: " + mouseState);

		
		if (isPositionDalek()) {
		
			// mouse to grid
			
			double x = (( e.getX() - xpos ) / scale - getMap().getRegTLX() ) / getMap().getRegScaleX();
			double y = (( e.getY() - ypos ) / scale - getMap().getRegTLY() ) / getMap().getRegScaleY();

			if (posDalek == null) {
				posDalek = new Position(x,y,0);
			} else {
				posDalek.setPosition(x,y,0);
			}
			//System.out.println ("x: " + x + ", y: " + y);
			this.repaint();

		}
		if (isDirectDalek()) {
			double x = (( e.getX() - xpos ) / scale - getMap().getRegTLX() ) / getMap().getRegScaleX();
			double y = (( e.getY() - ypos ) / scale - getMap().getRegTLY() ) / getMap().getRegScaleY();
			
			Position p = new Position (x,y);
		
			posDalek.facePosition(p);
		//	dalekImagePosition.get(this.dirDalekName).facePosition(p);
			
			this.repaint();
			
			// System.out.println ("Direction : " +dir);
			
		}
		
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