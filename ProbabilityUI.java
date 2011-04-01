import java.awt.*;

public class ProbabilityUI {
	
	int xscale;
	int yscale;
	static  final int prob[] = { 1,3,6,10,15,21,26,30,33,35,36 };
	Integer targetCost;
	int x,y;
	Font probFont;
	int fontYOffset;
	
	public ProbabilityUI () { ; }
	
	public void setXscale(int i) { xscale  = i; }
	public void setYscale(int i) { yscale  = i; }
	public void setX(int i) { x = i; }
	public void setY(int i) { y = i; }
	public void setFontYOffset(int i) { fontYOffset = i; }
	public void setLocation(int x, int y) { this.x = x; this.y = y; }
	public void setScale (int x, int y) { this.xscale = x; this.yscale = y; }
	
	public void setTargetCost(Integer i) { targetCost = i; }
	public int getTargetCost() { return targetCost.intValue(); }
	public boolean hasTargetCost() { return targetCost != null; }
	
	
	public void setFont(Font f) { probFont = f; }
	public Font getFont() { return probFont; }
	
	
	//public void setGraphics (Graphics g)
	
	public int getXscale() { return xscale; }
	public int getYscale() { return yscale; }
	public int getFontYOffset() { return fontYOffset; }
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	
	
	
	public void paintComponent (Graphics g) {
		
		if (this.hasTargetCost()) {
			
			int end = prob.length -1;
			int cost = getTargetCost() - 2;
			int difficulty = 0;
			
			Polygon pg = new Polygon();
			
			g.setColor(java.awt.Color.ORANGE);
			
			if (cost >= prob.length) { 
				g.setColor(java.awt.Color.RED);
				cost = end; 
				difficulty = 100;
			} else {
				difficulty = 100 * prob[cost] / 36;
			}
			
			if (cost < 5) { g.setColor(java.awt.Color.GREEN); }
			
			for (int i=0; i<= cost; i++) {
				pg.addPoint(i*getXscale()+getX(), prob[i] * getYscale() + getY());
			}
			pg.addPoint(cost*getXscale()+getX(),getY());
			pg.addPoint(getX(),getY());
			
			g.fillPolygon(pg);
			g.setColor(java.awt.Color.WHITE);
			
			for (int i=1; i< prob.length; i++) {
				g.drawLine((i-1)*getXscale() +getX(), prob[i-1] * getYscale() + getY(), getXscale()*i+getX(), getYscale()*prob[i] + getY());
			}
			
			g.drawLine(getX()+cost*getXscale(),getY(),getX()+cost*getXscale(),prob[cost]*getYscale() + getY());
			g.drawLine(getX(),getY(),getX()+end*getXscale(),getY());
			g.drawLine(getX()+end*getXscale(),getY(),getX()+end*getXscale(),getY()+prob[end]*getYscale());
			
			g.setFont( getFont() );
			
			g.drawString(difficulty +"%", getX(),getY()+getFontYOffset());
		}
		
	}
	
}
