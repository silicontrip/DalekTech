import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

public class MapImage extends BufferedImage implements ActionListener {
	
	Image baseImage;
	mapPanel mapp;
	
	// Temporary Dalek
	Image tempDalekImage=null;
	Position tempDalekPosition=null;
	
	// Selector image
	Image selectorImage=null;
	Position selectorPosition=null;
	
	// Daleks
	HashMap<Long,Position> dalekImageEndPosition;
	HashMap<Long,Position> dalekImagePosition;
	HashMap<Long,Image> dalekImage;
	
	// Movement cost
	Integer forwardCost;
	Integer backwardCost;
	Position costPosition;
	Boolean forwardMove;
	Boolean backwardMove;
	Image arrowImage=null;
	Image arrowLeftImage=null;
	Image arrowRightImage=null;

	
	
	// Weapons fire...
	
	
	
	Map map;
	int percent;
	
	javax.swing.Timer timer;
	
	public MapImage(Image baseImage, Map map) {
		super(baseImage.getWidth(null),baseImage.getHeight(null),BufferedImage.TYPE_4BYTE_ABGR);
		dalekImagePosition = new HashMap<Long,Position>();
		dalekImageEndPosition = new HashMap<Long,Position>();
		dalekImage = new HashMap<Long,Image>();
		
		timer = new javax.swing.Timer(10, this);
		timer.setInitialDelay(10);
		timer.setCoalesce(false);
		timer.setRepeats(true);		
		
		this.setBaseImage(baseImage);
		this.setMap(map);
	}
	
	public void setMap(Map m) { this.map = m; }
	public Map getMap() { return this.map; }
	
	
	public Image getBaseImage() { return baseImage; }
	public void setBaseImage(Image i) { baseImage = i; }
	
	int imageX (Position p) { return (int)((getMap().getRegScaleX() *p.getSpatialX() + getMap().getRegTLX()) ); }
	int imageY (Position p) { return (int)((getMap().getRegScaleY() *p.getSpatialY() + getMap().getRegTLY()) ); }
	int imageXPercent (Position p1, Position p2) { return (int)((getMap().getRegScaleX() * (p1.getSpatialX() * this.getInversePercentAsDouble() + p2.getSpatialX() * this.getPercentAsDouble()) + getMap().getRegTLX()) ); }
	int imageYPercent (Position p1, Position p2) { return (int)((getMap().getRegScaleY() * (p1.getSpatialY() * this.getInversePercentAsDouble() + p2.getSpatialY() * this.getPercentAsDouble()) + getMap().getRegTLY()) ); }
	double imageAngle(Position p) { return p.getAngle(); }
	double imageAnglePercent(Position p1, Position p2) { 
		// do we turn clockwise or anti-clockwise
		
		double angle1 = p1.getAngle();
		double angle2 = p2.getAngle();

		if (angle1 - angle2 > java.lang.Math.PI) { angle2 += 2 * java.lang.Math.PI; }
		if (angle2 - angle1 > java.lang.Math.PI) { angle2 += 2 * java.lang.Math.PI; }
		return  (p1.getAngle() * this.getInversePercentAsDouble() + p2.getAngle() * this.getPercentAsDouble()); 
	}
	
	
	// add daleks
	
	public void addDalek(Long id, Image i, Position p) {
		dalekImage.put(id,i);
		dalekImagePosition.put(id,p);
	}
	
	// dalek start position
	// dalek end position
	
	public void moveDalek(mapPanel mapp, Long id, Position p) {
		
		this.mapp = mapp;
		setPercent(0);
		dalekImageEndPosition.put(id,p);
		timer.start();
		
	}
	
	public boolean hasDalek (Long id) { return dalekImagePosition.containsKey(id); }
	
	// dalek percent movement
	
	public void setPercent(int i) { this.percent = i; }
	public int getPercent() { return this.percent; }
	public boolean endPercent() { return this.percent >= 100; }
	public double getPercentAsDouble() { return this.percent / 100.0; }
	public double getInversePercentAsDouble() { return 1.0 - this.percent / 100.0; }
	
	// selector position
	
	void setSelectorPosition(Position p) { this.selectorPosition = p; }
	void setSelectorImage(Image i) { this.selectorImage = i; }
	
	Image getSelectorImage() { return selectorImage; }
	Position getSelectorPosition() { return selectorPosition; }
	
	
	// movement cost
	public void setMovementCost(Position p, Integer forward, Integer backward, Boolean forwardMove, Boolean backwardMove) {
		this.costPosition = p;
		this.forwardCost = forward;
		this.backwardCost = backward;
		this.forwardMove = forwardMove;
		this.backwardMove = backwardMove;
	}
	public Position getMovementPosition () { return costPosition; }
	public void setMovementImage(Image i) { arrowImage = i; }
	public void setLeftImage(Image i) { arrowLeftImage = i; }
	public void setRightImage(Image i) { arrowRightImage = i; }

	public Image getMovementImage() { return arrowImage; }
	public Image getLeftImage() { return arrowLeftImage; }
	public Image getRightImage() { return arrowRightImage; }

	
	// movement directions
	
	// temporary dalek
	public void setTemporaryDalek(Image i, Position p) { tempDalekImage=i; tempDalekPosition=p; }
	public void setTemporaryDalekPosition(Position p) { tempDalekPosition=p; }
	public void setTemporaryDalekImage(Image i) { tempDalekImage=i; }
	
	Image getTemporaryDalekImage() { return tempDalekImage; }
	Position getTemporaryDalekPosition() { return tempDalekPosition; }
	void faceTemporaryDalekPosition(Position p) { tempDalekPosition.facePosition(p);}
	
	
	boolean hasTemporaryDalek() { return tempDalekImage != null && tempDalekPosition != null; }
	boolean hasSelector() { return selectorImage != null && selectorPosition != null; }
	boolean hasDaleks() { return dalekImagePosition != null && dalekImage != null; }
	boolean hasMovementCost() { return costPosition != null && forwardCost != null && backwardCost != null && forwardMove != null && backwardMove != null;}
	
		public void repaint() {
			
			// layer 0 map graphic
			Graphics2D canvas = this.createGraphics();
			canvas.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			canvas.drawImage(this.getBaseImage(), 0, 0,  null);
			
			// layer 1 selector image
			if (this.hasSelector()) {
				int h = (int)getMap().getRegScaleY();
				int w = this.getSelectorImage().getWidth(null) * h / this.getSelectorImage().getHeight(null);
				//int h = this.getSelectorImage().getWidth(null);
				
				canvas.drawImage(this.getSelectorImage(),
								 this.imageX(getSelectorPosition())-w/2,
								 this.imageY(getSelectorPosition())-h/2,
								 w,h,null);
			}
			
			// layer weapons fire
			
			
			
			// layer 2 dalek positions
			if (this.hasDaleks()) {
				
				Iterator<Long> it = dalekImagePosition.keySet().iterator();
				
				while (it.hasNext()){
					
					Long n = it.next();
					Image dalek = dalekImage.get(n);
					Position pos= dalekImagePosition.get(n);
					Position epos = null;
					
					int h = (int)getMap().getRegScaleY();
					int w = dalek.getWidth(null) * h / dalek.getHeight(null);
					
					if (dalekImageEndPosition.containsKey(n)) {
						epos = dalekImageEndPosition.get(n);
						// rotation...
						
						AffineTransform oldXform = canvas.getTransform();
						AffineTransform newXform = AffineTransform.getRotateInstance(imageAnglePercent(pos,epos),imageXPercent(pos,epos),imageYPercent(pos,epos));
						canvas.setTransform(newXform);
						canvas.drawImage(dalek,imageXPercent(pos,epos)-w/2,imageYPercent(pos,epos)-h/2,w,h,null);
						canvas.setTransform(oldXform);

					} else {
						AffineTransform oldXform = canvas.getTransform();
						AffineTransform newXform = AffineTransform.getRotateInstance(pos.getAngle(),imageX(pos),imageY(pos));
						canvas.setTransform(newXform);
						canvas.drawImage(dalek,imageX(pos)-w/2,imageY(pos)-h/2,w,h,null);
						canvas.setTransform(oldXform);
					}
				}
			}
			
			// layer explosion
			
			// layer 3 dalek cost and direction
			// doing the recode allowed me to understand the registration and rotation process for images
			if (this.hasMovementCost()) {
				int h = (int)(getMap().getRegScaleY() * 0.66 ); // nice easy scale factor. 2 thirds height of a hex
				int w = getMovementImage().getWidth(null) * h / getMovementImage().getHeight(null);
				
				// going to assume right arrow and left arrow are the same dimensions
				int wl = getLeftImage().getWidth(null) * h / getLeftImage().getHeight(null);
				
			//	System.out.println ("movement postition: " + getMovementPosition());
				
				
				Position forwardPosition = getMovementPosition().newForwardsPosition();
				Position backwardPosition = getMovementPosition().newBackwardsPosition();
				
			//	System.out.println ("forward postition: " + forwardPosition);

				
				Position leftPosition =  getMovementPosition().newForwardLeftPosition();
				Position rightPosition =  getMovementPosition().newForwardRightPosition();

			//	System.out.println ("left postition: " + leftPosition);
			//	System.out.println ("right postition: " + rightPosition);

				
				AffineTransform oldXform = canvas.getTransform();
				AffineTransform newXform = AffineTransform.getRotateInstance(forwardPosition.getAngle(),imageX(leftPosition),imageY(leftPosition));
				canvas.setTransform(newXform);
				canvas.drawImage(getLeftImage(),imageX(leftPosition)-wl/2,imageY(leftPosition)-h/2,wl,h,null);
				canvas.setTransform(oldXform);
				
				newXform = AffineTransform.getRotateInstance(forwardPosition.getAngle(),imageX(rightPosition),imageY(rightPosition));
				canvas.setTransform(newXform);
				canvas.drawImage(getRightImage(),imageX(rightPosition)-wl/2,imageY(rightPosition)-h/2,wl,h,null);
				canvas.setTransform(oldXform);

				
				if (forwardMove) {
					newXform = AffineTransform.getRotateInstance(forwardPosition.getAngle(),imageX(forwardPosition),imageY(forwardPosition));
					canvas.setTransform(newXform);
					canvas.drawImage(getMovementImage(),imageX(forwardPosition)-w/2,imageY(forwardPosition)-h/2,w,h,null);
					canvas.setTransform(oldXform);
				}
				if (backwardMove) {
					newXform = AffineTransform.getRotateInstance(backwardPosition.getReverseAngle(),imageX(backwardPosition),imageY(backwardPosition));
					canvas.setTransform(newXform);
					canvas.drawImage(getMovementImage(),imageX(backwardPosition)-w/2,imageY(backwardPosition)-h/2,w,h,null);
					canvas.setTransform(oldXform);
				}

			}
			
			// layer 4 temporary dalek
			
			if (this.hasTemporaryDalek()) {
				
				int h = (int)this.getMap().getRegScaleY();
				int w = this.getTemporaryDalekImage().getWidth(null) * h / getTemporaryDalekImage().getHeight(null);
				int x = this.imageX(getTemporaryDalekPosition());
				int y = this.imageY(getTemporaryDalekPosition());
				
				AffineTransform oldXform = canvas.getTransform();
				AffineTransform newXform = AffineTransform.getRotateInstance(getTemporaryDalekPosition().getAngle(),x,y);
				canvas.setTransform(newXform);
				canvas.drawImage(this.getTemporaryDalekImage(),x-w/2,y-h/2,w,h,null);
				canvas.setTransform(oldXform);
			}
			
		}
		
		public void actionPerformed(ActionEvent e) {
			this.setPercent(this.getPercent() + 2);
			mapp.repaint();
			if (this.endPercent()) {
				
				setPercent(0);
				timer.stop();
				
				Iterator<Long> it = dalekImageEndPosition.keySet().iterator();
				while (it.hasNext()){
					Long n = it.next();
					dalekImagePosition.put(n,dalekImageEndPosition.get(n));
				//	dalekImageEndPosition.remove(n);
				}
				dalekImageEndPosition.clear();
			}
		}
		
	}