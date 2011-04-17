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
	Image targetDalek;
	
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

		
		//probUI = new ProbabilityUI ();
		// probUI.setScale(8,-2);
		//probUI.setFont(new Font("Eurostile",0,64));
		// probUI.setFontYOffset(20);
		//probUI.setLocation(500,100);
							  
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
	void setArrowImage(Image i) { this.getMapImage().setMovementImage(i); }
	void setLeftImage(Image i) { this.getMapImage().setLeftImage(i); }
	void setRightImage(Image i) { this.getMapImage().setRightImage(i); }

	
	void setProbabilityUI(ProbabilityUI pui) { this.probUI = pui; }

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
	
	void notifyDalek(Long l, Image i, Position p) {
		// scroll map display to position.
		
		this.centreOn(p);
			
		if (getMapImage().hasDalek(l)) {
			// System.out.println("Move Dalek - " + p);
			getMapImage().moveDalek(this,l,p);
		} else {
			getMapImage().addDalek(l,i,p);
		}
		timer.start();
	}

	public Dimension getPreferredSize() {
        return new Dimension(mapPanelWidth,mapPanelHeight);
    }

	int getMapHeight() { return (int)(map.getHeight(null) * scale); }
	int getMapWidth() { return (int)(map.getWidth(null) * scale); }
	
	void setNothing() {
		//System.out.println("setNothing()");
		mouseState = 0;
	}
	
	void setPositionDalek() {
		//System.out.println("setPosition()");
		mouseState = 1;
	}
		
	void setDirectDalek() {
		//System.out.println("setDirect()");
		mouseState = 2;
	}
	
	boolean isPositionDalek() { return mouseState == 1; }
	boolean isDirectDalek() { return mouseState == 2; }
	boolean isNothing() { return mouseState == 0; }

	
	void positionDalek (Image dalek) {
		this.setPositionDalek();
		getMapImage().setTemporaryDalekImage(dalek);
	}
	
	void directDalek (Image dalek, Position pos) { 
		this.setDirectDalek();
		getMapImage().setTemporaryDalek(dalek,pos);
	}
	
	public void setMovementCost(Position p, Integer forward, Integer backward, Boolean forwardMove, Boolean backwardMove) {		
		getMapImage().setMovementCost(p,forward,backward,forwardMove,backwardMove);
	}
	
	Position getMovementCostPosition() { return costPosition; }
	
	public void setTargetDistance (Double d) { this.targetDistance = d; }
	public double getTargetDistance () { return targetDistance.doubleValue(); }
	
	public void setLineOfSight (ArrayList<Hex> los) { this.lineSight = los; }
	public ArrayList<Hex> getLineOfSight () { return lineSight; }
	
	// public void setTargetCost(Integer i) { probUI.setTargetCost(i); }	
	
	public void setTarget(Image i) { targetDalek = i; }
	
	/*
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
	*/
	public void paintComponent (Graphics g) {
		
		super.paintComponent(g);       

		int h = getMapHeight();
		int w = getMapWidth();

		
		BufferedImage thumbImage = new BufferedImage(mapPanelWidth, mapPanelHeight, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D canvas  = thumbImage.createGraphics();
		canvas.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		map.repaint();
		canvas.drawImage(map, xpos, ypos, w, h, null);
		 
		// need proportional scaling
		if (targetDalek != null) {
			int tw = 128 * targetDalek.getWidth(null) / targetDalek.getHeight(null);
			canvas.drawImage(targetDalek,500,200,tw,128,null);
		}
		
		// line of sight 
		
		// I may move this to a BufferedImage
		// probUI.paintComponent(canvas); 
		
		// oh look a BufferedImage
		canvas.drawImage(probUI,500,64,64,64,null);
		
		if (interfaceMessage != null) {
			// again want to move this to an image
			canvas.setColor(java.awt.Color.WHITE);
			canvas.setFont( new Font("Eurostile",0,24));
			canvas.drawString(interfaceMessage,32,32);
		}
		
		g.drawImage(thumbImage,0,0,null);


	}
	
	boolean imageBound (double scale, int x, int y) {
		return x<=0 && y<=0 && map.getHeight(null) * scale+y >= mapPanelHeight && map.getWidth(null) * scale+x >= mapPanelWidth;
	}
	
	public void actionPerformed(ActionEvent e) {
		
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
		
		//if (isNothing()) { // still want to drag the display when positioning daleks
			startx = e.getX();
			starty = e.getY();
		//}
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
			if (getMapImage().getTemporaryDalekPosition() != null) {
				this.setNothing();
				callback.setSelectedPosition(getMapImage().getTemporaryDalekPosition());
				
				getMapImage().setTemporaryDalek(null,null);
			}
		}
		if (isDirectDalek()) {
			// I want to merge this with the above code.
			this.setNothing();
			
			callback.setSelectedDirection(getMapImage().getTemporaryDalekPosition().getDirection()); 
			getMapImage().setTemporaryDalek(null,null);
			
			this.repaint();
		}
		if (getMapImage().getMovementPosition() != null) {
			
			Position ref  = getMapImage().getMovementPosition();
			
			double x = (( e.getX() - xpos ) / scale - getMap().getRegTLX() ) / getMap().getRegScaleX();
			double y = (( e.getY() - ypos ) / scale - getMap().getRegTLY() ) / getMap().getRegScaleY();
			
			Position click = new Position (x,y);
			// check for positions.
			if ( ref.equalsIgnoreDirection(click)) { callback.setSelectedMovement(Tables.DONE); }
			if ( ref.newForwardsPosition().equalsIgnoreDirection(click)) { callback.setSelectedMovement(Tables.FORWARD); }
			if ( ref.newBackwardsPosition().equalsIgnoreDirection(click)) { callback.setSelectedMovement(Tables.BACKWARD); }
			if ( ref.newForwardRightPosition().equalsIgnoreDirection(click)) { callback.setSelectedMovement(Tables.RIGHT); }
			if ( ref.newForwardLeftPosition().equalsIgnoreDirection(click)) { callback.setSelectedMovement(Tables.LEFT); }
			
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
		 //System.out.println("Action: " + e + " mouseState: " + mouseState);
		//System.out.println("mouseState: " + mouseState);

		
		if (isPositionDalek()) {
		
			// mouse to grid
			
			double x = (( e.getX() - xpos ) / scale - getMap().getRegTLX() ) / getMap().getRegScaleX();
			double y = (( e.getY() - ypos ) / scale - getMap().getRegTLY() ) / getMap().getRegScaleY();

			getMapImage().setTemporaryDalekPosition(new Position(x,y));
			this.repaint();

		}
		if (isDirectDalek()) {
			double x = (( e.getX() - xpos ) / scale - getMap().getRegTLX() ) / getMap().getRegScaleX();
			double y = (( e.getY() - ypos ) / scale - getMap().getRegTLY() ) / getMap().getRegScaleY();
			
			Position p = new Position (x,y);
		
			//posDalek.facePosition(p);
			getMapImage().faceTemporaryDalekPosition(p);
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
			callback.setSelectedMovement(Tables.SELECT);

		}
		
		if (kc == KeyEvent.VK_ENTER) {
			callback.setSelectedMovement(Tables.DONE);
		}
		
    }
	
	public void keyReleased(KeyEvent e) {
		//	System.out.println("Action: " + e);
    }
	public void keyTyped(KeyEvent e) {
		//System.out.println("Action: " + e);
    }
	

	
}