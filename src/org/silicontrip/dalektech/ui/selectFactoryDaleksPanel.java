package org.silicontrip.dalektech.ui;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class selectFactoryDaleksPanel extends JPanel implements KeyListener, ActionListener {
	
	int dalekWidth;
	int dalekIconWidth;
	int slide, slideDelta;
	Image selector;
	Image overlay;
	Image background;

	
	int selectedMovement;
	
	javax.swing.Timer timer;

//	statusPanel sp;
	
	ArrayList<Dalek> dalekList;
	ArrayList<Integer> selectedDaleks;
	int select, endSelect;
	HashMap<String,Image> dalekImages;
	

	void setDalekWidth(int i) { dalekWidth = i; }
	int getDalekWidth() { return dalekWidth; }
	
	void setDalekIconWidth(int i) { dalekIconWidth = i; }
	int getDalekIconWidth() { return dalekIconWidth; }

	void setSelectedMovement(int i) { this.selectedMovement = i; }
	int getSelectedMovement() { return selectedMovement; }
	
	
	public selectFactoryDaleksPanel () {
		super();

		dalekWidth = 1;
		addKeyListener(this);
		setFocusable(true);
		select = 0;
		selectedDaleks = new ArrayList<Integer>();
		
		timer = new javax.swing.Timer(10, this);
		timer.setInitialDelay(10);
		timer.setCoalesce(false);
		timer.setRepeats(true);
		
	}
	
	// really want this to be a generic interface class not a specific sub class.
	public selectFactoryDaleksPanel (HashMap<String,Image> dalekImages, ArrayList<Dalek> dalekList) {
		super();
		this.dalekList = dalekList;
		this.dalekImages = dalekImages;
		dalekWidth = 1;
		addKeyListener(this);
		setFocusable(true);

		select = 0;
		selectedDaleks = new ArrayList<Integer>();
		timer = new javax.swing.Timer(10, this);
		timer.setInitialDelay(10);
		timer.setCoalesce(false);
		timer.setRepeats(true);
	}
		
	void setSelectorImage (Image i) { selector = i; }
	void setOverlayImage (Image i) { overlay = i; }	
	void setBackgroundImage (Image i) { background = i; }
	Image dalekImage(String name) { return dalekImages.get(name); }
	public Dimension getPreferredSize() { return new Dimension(640,480); }	
	public void setDalekList(ArrayList<Dalek> dalekList) { this.dalekList = dalekList; }
	
	void drawDalekAt (Graphics g, String name, int x, int y, int w) {
	
		Image dalek = dalekImage(name);
		int h = (int)  (dalek.getHeight(null) * w / dalek.getWidth(null)  );
		
		BufferedImage thumbImage = new BufferedImage(w, y, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(dalek, 0, 0, w, h, null);
		
		g.drawImage(thumbImage,x,y-h,null);
		
	}
	
	
	public void addDalek(int i) {
		selectedDaleks.add(i);
	}
	
	public void centreOn(int i) {

		int direction = i - select;
		
		if (direction == 1 || direction < -1) {
			slide = getDalekWidth();
			slideDelta = -5;
		}
		if (direction == -1 || direction > 1) {
			slide = -getDalekWidth();
			slideDelta = 5;
		}
		
		//System.out.println ("Select: " + select + "  I: " + i) ;
		
		select = i;
		timer.start();

	}
	
	public void paintComponent (Graphics g) {
		
		super.paintComponent(g);       
		
		if (dalekList != null) {
			
			//background image
			
			int xl = -128;
			String dalekName;
						
			for (int d=select-3;d<=select+3;d++) {
				int dal = d % dalekList.size();
				if (dal < 0) { dal += dalekList.size();}
				
				dalekName = dalekList.get(dal).getName();
				this.drawDalekAt(g,dalekName,xl+slide,360,getDalekWidth());

				xl += getDalekWidth();
			}	
			
			BufferedImage thumbImage = new BufferedImage(128, 320, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(selector, 0, 0, 128, 320, null);
			
			g.drawImage(thumbImage,256,80,null);
			
			g.drawImage(overlay,0,0,640,480,null);

			
			Iterator<Integer> it = selectedDaleks.iterator();
			xl = 0;
			
			while (it.hasNext()) {
				dalekName = dalekList.get(it.next()).getName();
				
				this.drawDalekAt(g,dalekName,xl+32,458,getDalekIconWidth());
				
				xl += getDalekIconWidth();
				
				
			}
			

			
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		// I want to slide the daleks across.
		
		//System.out.println ("Action Event");
		
		if (slideDelta != 0 ) {
			slide += slideDelta;
			
			if (java.lang.Math.abs(slide) < 5) {
				slide = 0;
				slideDelta = 0;
				timer.stop();			
			}
		
			this.repaint();
		}

		
	}
	

	public void keyPressed(KeyEvent e) {
		//System.out.println("Action: " + e);
		
		int kc = e.getKeyCode();
		
		if (kc == KeyEvent.VK_LEFT) {
			this.setSelectedMovement(Tables.LEFT);
		}
		
		if (kc == KeyEvent.VK_RIGHT) {
			this.setSelectedMovement(Tables.RIGHT);
			
		}
		
		if (kc == KeyEvent.VK_UP) {
			this.setSelectedMovement(Tables.FORWARD);
		}
		
		if (kc == KeyEvent.VK_DOWN) {
			this.setSelectedMovement(Tables.BACKWARD);
			
		}
		
		
		if (kc == KeyEvent.VK_SPACE) {
			this.setSelectedMovement(Tables.SELECT);
		}
		
		if (kc == KeyEvent.VK_ENTER) {
			this.setSelectedMovement(Tables.DONE);
		}
		
    }
	
	
	public void keyReleased(KeyEvent e) {
	//	System.out.println("Action: " + e);
    }
	public void keyTyped(KeyEvent e) {
		//System.out.println("Action: " + e);
    }
	
	
}
