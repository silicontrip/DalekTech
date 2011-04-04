import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class mapPanel extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener, ActionListener, KeyListener {

	javax.swing.Timer timer;
	
	double scale;
	double dalekScale = 0.5;
	int xpos, ypos;
	MapImage map;
	Image selectorImage=null;
	Position selectorPosition=null;
	Map gridMap;
	int startx, starty;
	Guitwo callback;
	
	Image arrowImage = null;
	Position forwardPosition = null;
	Position backwardPosition = null;
	
	ProbabilityUI probUI;
	
	Image posDalekImage = null;
	Position posDalek=null;

	int mouseState = 0;
	
	Integer forwardCost = null;
	Integer backwardCost = null;
	Position costPosition = null;
	Boolean forwardMove = null;
	Boolean backwardMove = null;
	
	Integer targetCost = null;
	Double targetDistance = null;
	ArrayList<Hex> lineSight = null;
	String interfaceMessage = null;
	
	String moveDalekName=null;
	Position moveDalekPosition=null;
	double moveDalekX,moveDalekY,moveDalekDir;
	double moveDalekTargetX,moveDalekTargetY,moveDalekTargetDir;
	double moveDalekCurrentX,moveDalekCurrentY,moveDalekCurrentDir;
	
	double moveXpos=0, moveYpos=0;
	Position moveXYpos;
	
	HashMap<String,Position> dalekImagePosition;
	HashMap<String,Image> dalekImage;
	
	public static final int mapPanelWidth = 640;
	public static final int mapPanelHeight = 480;

	public mapPanel (Image map, Map m, Guitwo ui) {
		super();
		this.map = new MapImage(map,m);
		this.gridMap = m;
		this.callback = ui;
		
		dalekImagePosition = new HashMap<String,Position>();
		dalekImage = new HashMap<String,Image>();

		
		probUI = new ProbabilityUI ();
		probUI.setScale(8,-2);
		probUI.setFont(new Font("Eurostile",0,12));
		probUI.setFontYOffset(20);
		probUI.setLocation(500,100);
							  
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addMouseListener(this);
		addKeyListener(this);

		scale = 1;
		xpos = 0;
		ypos = 0;
		
		timer = new javax.swing.Timer(10, this);
		timer.setInitialDelay(10);
		timer.setCoalesce(false);
		timer.setRepeats(true);
		

		
	}
			
	MapImage getMapImage() { return map; }
	void setSelectorImage(Image i) { this.getMapImage().setSelectorImage(i); }
	void setArrowImage(Image i) { this.arrowImage = i; }

	void setSelectorPosition(Position p) { this.getMapImage().setSelectorPosition(p); }
//	Position getSelectorPosition() { return this.selectorPosition; }
	
	void setInterfaceMessage(String s) { interfaceMessage = s; }
		
	
	Map getMap() { return gridMap; }
	double getScale() { return scale; }
	
	double calX (double x) { return ((getMap().getRegScaleX() *x + getMap().getRegTLX()) * getScale()) + xpos; }
	double calY (double y) { return ((getMap().getRegScaleY() *y + getMap().getRegTLY()) * getScale()) + ypos; }
		
	void centreOn(Position p) { 
	
		moveXpos = (mapPanelWidth/2  - this.calX(p.getSpatialX())) / 50.0;
		moveYpos = (mapPanelHeight/2 - this.calY(p.getSpatialY())) / 50.0;
				
		moveXYpos = new Position (p);		
		
		timer.start();
	}
	
	void notifyDalek(String s, Image i, Position p) {
		// scroll map display to position.
		
		this.centreOn(p);
		
		//System.out.println("Animate: " + moveXpos + ","+moveYpos+" : " + moveXYpos);
		
		if (dalekImagePosition.containsKey(s)) {
			
			map.moveDalek(s,p);
			/*
			// dalek exists 
			// animate to new position.
			
			// System.out.println("Animating: "+s + " from: " + dalekImagePosition.get(s) +" to " + p);

			double distance = dalekImagePosition.get(s).distanceTo(p);
			if (distance > 0.0) {

				moveDalekName = s;
				moveDalekPosition = new Position(p);
				moveDalekCurrentX = 0;
				moveDalekCurrentY = 0;

				moveDalekDir = 0;
				moveDalekTargetDir = 0;
				
				moveDalekTargetX = p.getSpatialX() -  dalekImagePosition.get(s).getSpatialX() ;
				moveDalekTargetY = p.getSpatialY() -  dalekImagePosition.get(s).getSpatialY();

				moveDalekX = moveDalekTargetX / (distance * 50.0);
				moveDalekY = moveDalekTargetY / (distance * 50.0);
			
				// System.out.println("Target: " + moveDalekTargetX + "," + moveDalekTargetY +" Delta: " + moveDalekX + "," + moveDalekY);
			
				// timer.start();
			} else if ( p.getDirection() != dalekImagePosition.get(s).getDirection()) {
				
				// reposition dalek incase of rotation.
				// want to animate
				
				moveDalekName = s;
				moveDalekPosition = new Position(p);

				moveDalekCurrentX = 0;
				moveDalekCurrentY = 0;
				moveDalekTargetX = 0;
				moveDalekTargetY = 0;
				moveDalekX = 0;
				moveDalekY = 0;

				
				moveDalekCurrentDir = 0;
				moveDalekTargetDir = p.getDirection().getTurnTo(dalekImagePosition.get(s).getDirection());
							
				moveDalekDir = moveDalekTargetDir  / 50.0;
				// dalekImagePosition.put(moveDalekName,moveDalekPosition);
				// timer.start();

			}
			 */
		} else {
			map.addDalek(s,i,p);
			/*
			// System.out.println("**** adding dalek at " + p +" ****");
				// simply add the new dalek
			dalekImagePosition.put(s,new Position(p));
			dalekImage.put(s,i);
			this.repaint();
			 */
		}
		timer.start();
	}
	/*
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
	 */
	public Dimension getPreferredSize() {
        return new Dimension(mapPanelWidth,mapPanelHeight);
    }

	int getMapHeight() { return (int)(map.getHeight(null) * scale); }
	int getMapWidth() { return (int)(map.getWidth(null) * scale); }

	void drawDalekAt (Graphics g, Image dalek, int x, int y, int w) {
		drawDalekAt(g,dalek,x,y,w,0);
	}
	
	
	void drawDalekAt (Graphics g, Image dalek, int x, int y, int w,Direction dir) {
		if (dir != null ) {
			drawDalekAt(g,dalek,x,y,w,dir.getDirection());
		} else {
			drawDalekAt(g,dalek,x,y,w,0);
		}
	}
	
	void drawDalekAt (Graphics g, Image dalek, int x, int y, int w,double dir) {
		
		int h = (int)  (dalek.getHeight(null) * w / dalek.getWidth(null)  );
		
		if (g.hitClip(x-h,y-h/2,h*2, h)) {
	//	if (x>-h && x< mapPanelWidth+h && y>-h&& y < mapPanelHeight+h) {

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
		
		map.setTemporaryDalekImage(dalek);
/*		
		this.posDalekImage = dalek;
		this.posDalek = null;
*/		
	}
	
	void directDalek (Image dalek, Position pos) { 
		
		
		this.setDirectDalek();
		
		map.setTemporaryDalek(dalek,pos);
		/*
		this.posDalekImage = dalek;
		this.posDalek = pos;
		 */
	}
	
	public void setMovementCost(Position p, Integer forward, Integer backward, Boolean forwardMove, Boolean backwardMove) {
		costPosition=p;
		forwardCost=forward;
		backwardCost=backward;
		
		this.forwardMove = forwardMove;
		this.backwardMove = backwardMove;
	}
	
	Position getMovementCostPosition() { return costPosition; }
	
	public void setTargetDistance (Double d) { this.targetDistance = d; }
	public double getTargetDistance () { return targetDistance.doubleValue(); }
	
	public void setLineOfSight (ArrayList<Hex> los) { this.lineSight = los; }
	public ArrayList<Hex> getLineOfSight () { return lineSight; }
	
	public void setTargetCost(Integer i) { probUI.setTargetCost(i); }	
		
	void paintMovementCost(Graphics g) {

		int h = arrowImage.getHeight(null);
		int w = arrowImage.getWidth(null);

		
		g.setFont( new Font("Eurostile",0,24));
		g.setColor(java.awt.Color.WHITE);

		BufferedImage thumb1 = new BufferedImage(h*2, w*2, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D canvas1 = thumb1.createGraphics();
		canvas1.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		
		BufferedImage thumb2 = new BufferedImage(h*2, w*2, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D canvas2 = thumb2.createGraphics();
		canvas2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		
		if (this.forwardMove) {
			AffineTransform newXform =  AffineTransform.getRotateInstance(getMovementCostPosition().getDirection().getAngle(),h,w);
			canvas1.setTransform(newXform);
			canvas1.drawImage(arrowImage,h,w,w/3,h/3,null);	
			g.drawImage(thumb1,(int)this.calX(getMovementCostPosition().newForwardsPosition().getSpatialX())-w/2,
						(int)this.calY(getMovementCostPosition().newForwardsPosition().getSpatialY())-h/2,null);
			
			g.drawString(forwardCost.toString(),(int)this.calX(getMovementCostPosition().newForwardsPosition().getSpatialX()),
						 (int)this.calY(getMovementCostPosition().newForwardsPosition().getSpatialY()));
		}
		if (this.backwardMove) {
			
			AffineTransform newXform =  AffineTransform.getRotateInstance(getMovementCostPosition().getDirection().getReverseAngle(),h,w);
			canvas2.setTransform(newXform);
			canvas2.drawImage(arrowImage,h,w,w/3,h/3,null);	
			
			g.drawImage(thumb2,(int)this.calX(getMovementCostPosition().newBackwardsPosition().getSpatialX()),
						(int)this.calY(getMovementCostPosition().newBackwardsPosition().getSpatialY()),null);

			
			g.drawString(backwardCost.toString(),(int)this.calX(getMovementCostPosition().newBackwardsPosition().getSpatialX()),
						 (int)this.calY(getMovementCostPosition().newBackwardsPosition().getSpatialY()));
		}

		
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
		 
		
		// drawn in the MapImage class
		/* 
		if (getSelectorPosition() != null) {
			// arg horrible registration values.
			g.drawImage(selectorImage, 
						(int)this.calX(getSelectorPosition().getSpatialX() - 0.57),
						(int)this.calY(getSelectorPosition().getSpatialY() - 0.50), 
						(int)(getMap().getRegScaleX() * scale * 1.175),
						(int)(getMap().getRegScaleY() * scale * 1.025 ),
						null);
		}
		
		
	//	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		// g.drawImage(map,xpos,ypos,w,h,null);  // doesn't do antialiased scaling.
		
		
		Iterator<String> it = dalekImagePosition.keySet().iterator();
		
		while (it.hasNext()){
			
			String n = it.next();
			
			Position pos= dalekImagePosition.get(n);
			

			// This is for the dalek animated movement
			if (moveDalekName != null && moveDalekName.equals(n)) {
				// System.out.println("**** dalek " + n + " at " + pos +" ****");

				this.drawDalekAt(g,dalekImage.get(n),
								 (int)this.calX(pos.getSpatialX()+moveDalekCurrentX), 
								 (int)this.calY(pos.getSpatialY()+moveDalekCurrentY), 
								 (int)(getMap().getRegScaleX() * scale * dalekScale),
								 pos.getDirection().getDirection() + moveDalekCurrentDir);
			} else {

				this.drawDalekAt(g,dalekImage.get(n),
								 (int)this.calX(pos.getSpatialX()), 
								 (int)this.calY(pos.getSpatialY()), 
								 (int)(getMap().getRegScaleX() * scale * dalekScale),
								 pos.getDirection());	
			
			}
		}
		
		// temporary dalek image (for positioning and direction)
		if (this.posDalekImage != null && this.posDalek != null) {
			this.drawDalekAt(g,posDalekImage,
							 (int)this.calX(this.posDalek.getSpatialX()), 
							 (int)this.calY(this.posDalek.getSpatialY()), 
							 (int)(getMap().getRegScaleY() * scale * dalekScale),
							 this.posDalek.getDirection());		
		}
		
		if (costPosition != null ) {
			paintMovementCost(g);
		}
		*/
		
		probUI.paintComponent(g); 
		
		if (interfaceMessage != null) {
			
			g.setColor(java.awt.Color.WHITE);
			g.setFont( new Font("Eurostile",0,24));
			g.drawString(interfaceMessage,32,32);
		}
			

	}
	
	boolean imageBound (double scale, int x, int y) {
		return x<=0 && y<=0 && map.getHeight(null) * scale+y >= mapPanelHeight && map.getWidth(null) * scale+x >= mapPanelWidth;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		// more dalek sliding
		/*
		if (moveDalekName != null) {
			
			// 	System.out.println ("**** Animating movement ****");
			
			moveDalekCurrentX += moveDalekX;
			moveDalekCurrentY += moveDalekY;
			moveDalekCurrentDir += moveDalekDir;
			
			if (java.lang.Math.abs(moveDalekCurrentX - moveDalekTargetX) < 0.01 &&
				java.lang.Math.abs(moveDalekCurrentY - moveDalekTargetY) < 0.01 && 
				java.lang.Math.abs(moveDalekCurrentDir - moveDalekTargetDir) < 0.01) {
				
				// System.out.println ("**** Dalek at Target ****");
				
				dalekImagePosition.put(moveDalekName,moveDalekPosition);
				moveDalekCurrentX = 0;
				moveDalekCurrentY = 0;
				moveDalekCurrentDir=0;
				moveDalekDir = 0;
				moveDalekPosition = null;
				moveDalekName = null;
				//	timer.stop();
			}
		}
		 */
		if (moveXYpos != null) {
			
			int txpos = xpos + (int)moveXpos;
			int typos = ypos + (int)moveYpos;
			
			boolean xfin,yfin,xcant,ycant;
			
			double dx,dy;
			
			yfin =  (moveYpos > -1 && moveYpos < 1);
			xfin =  (moveXpos > -1 && moveXpos < 1);

			
			dx = java.lang.Math.abs(mapPanelWidth/2  - this.calX(moveXYpos.getSpatialX()));
			dy = java.lang.Math.abs(mapPanelHeight/2  - this.calY(moveXYpos.getSpatialY()));
			
			xfin = dx <java.lang.Math.abs(moveXpos) || xfin;
			yfin = dy <java.lang.Math.abs(moveYpos) || yfin;
			
			//centreOn(moveXYpos);
			
			ycant = (!imageBound(this.scale,xpos,typos));
			xcant = (!imageBound(this.scale,txpos,ypos));

			//System.out.print("Motion: " + moveXpos+ ", " + moveYpos + " Distance: " + dx + ", " +dy);

//			System.out.println(" xfin: " + xfin + ", yfin: " + yfin + ", xcant: " + xcant + ", ycant: " + ycant);
			
			if (imageBound(this.scale,xpos,typos)) { ypos = typos; }
			if (imageBound(this.scale,txpos,ypos)) { xpos = txpos; }
			if (yfin) { moveYpos = 0;}
			if (xfin) { moveXpos = 0;}

			if ((xcant && ycant) || (xfin && yfin) || (xcant && yfin) || (xfin && ycant)){
				moveXpos =0;
				moveYpos =0;
				moveXYpos = null;
			} 
			
		}
		
		if (moveDalekName == null && moveXYpos == null) {
			timer.stop();
		}
		
		this.repaint();	
		
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
						
		if (imageBound(this.scale,xpos,typos)) {
			// xpos = txpos;
			ypos = typos;
		}
		if (imageBound(this.scale,txpos,ypos)) {
			xpos = txpos;
			//ypos = typos;
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

			getMapImage().setTemporaryDalekPosition(new Position(x,y));
			/*
			if (posDalek == null) {
				posDalek = new Position(x,y);
			} else {
				posDalek.setPosition(x,y);
			}
			//System.out.println ("x: " + x + ", y: " + y);
			 */
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
			// want to change xpos and ypos to accomodate scale change
			scale = tempscale;
		}
	//	System.out.println ("Old scale: " + scale + ", new scale : " + tempscale);

		this.repaint();

	}
	
	public void keyPressed(KeyEvent e) {
			//System.out.println("Action: " + e);
		
		int kc = e.getKeyCode();
		
		if (kc == KeyEvent.VK_LEFT) {
			callback.setSelectedMovement(Tables.LEFT);
		}
		
		if (kc == KeyEvent.VK_RIGHT) {
			callback.setSelectedMovement(Tables.RIGHT);

		}
		
		if (kc == KeyEvent.VK_UP) {
			callback.setSelectedMovement(Tables.FORWARD);
		}
		
		if (kc == KeyEvent.VK_DOWN) {
			callback.setSelectedMovement(Tables.BACKWARD);
			
		}
		
		
		if (kc == KeyEvent.VK_SPACE) {
		}
		
		if (kc == KeyEvent.VK_ENTER) {
			callback.setSelectedMovement(Tables.NONE);
		}
		
    }
	
	public void keyReleased(KeyEvent e) {
		//	System.out.println("Action: " + e);
    }
	public void keyTyped(KeyEvent e) {
		//System.out.println("Action: " + e);
    }
	

	
}