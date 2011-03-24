
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class selectFactoryDaleksPanel extends JPanel implements KeyListener, ActionListener {
	
	int dalekWidth;
	int dalekIconWidth;
	int slide, slideDelta;
	Guitwo callback;

	javax.swing.Timer timer;

	
	ArrayList<Dalek> dalekList;
	ArrayList<Integer> selectedDaleks;
	int select;
	HashMap<String,Image> dalekImages;

	void setDalekWidth(int i) { dalekWidth = i; }
	int getDalekWidth() { return dalekWidth; }
	
	void setDalekIconWidth( int i) { dalekIconWidth = i; }
	int getDalekIconWidth() { return dalekIconWidth; }

	
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
	public selectFactoryDaleksPanel (HashMap<String,Image> dalekImages, ArrayList<Dalek> dalekList,Guitwo ui) {
		super();
		this.dalekList = dalekList;
		this.callback = ui;
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
	
	Image dalekImage(String name) {
// I think I'd rather it throw an exception
		// there should be no case where not found is valid
//		if (dalekImages.containsKey(name)) {
			return dalekImages.get(name);
//		}
//		System.out.println("Canot find: "+ name);
//		return null;
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(640,480);
    }
	
	public void setDalekList(ArrayList<Dalek> dalekList) {
		this.dalekList = dalekList;
	}
	
	void drawDalekAt (Graphics g, String name, int x, int y, int w) {
	
		Image dalek = dalekImage(name);
		int h = (int)  (dalek.getHeight(null) * w / dalek.getWidth(null)  );
		
		BufferedImage thumbImage = new BufferedImage(w, y, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(dalek, 0, 0, w, h, null);
		
		g.drawImage(thumbImage,x,y-h,null);
		
	}
	
	
	public void paintComponent (Graphics g) {
		
		super.paintComponent(g);       
		
		if (dalekList != null) {
			
			int xl = -128;
			String dalekName;
			
			
			for (int d=select-3;d<=select+3;d++) {
				int dal = d % dalekList.size();
				if (dal < 0) { dal += dalekList.size();}
				
				dalekName = dalekList.get(dal).getName();
				this.drawDalekAt(g,dalekName,xl+slide,360,getDalekWidth());

				xl += getDalekWidth();
			}	
			
			Iterator<Integer> it = selectedDaleks.iterator();
			xl = 0;
			
			while (it.hasNext()) {
				dalekName = dalekList.get(it.next()).getName();
				
				this.drawDalekAt(g,dalekName,xl,480,getDalekIconWidth());
				
				xl += getDalekIconWidth();
				
				
			}
			
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		// I want to slide the daleks across.
		
		//System.out.println ("Action Event");
		
		if (slideDelta != 0 ) {
		slide += slideDelta;
		if (slide < -128) {
			select ++ ;
			select = select % dalekList.size();

			slide = 0;
			timer.stop();
			
		}
		if (slide > 128) {
			
			select -- ;
			if (select < 0) { select += dalekList.size();}
			slide = 0;

			timer.stop();
			
		}
		
		this.repaint();
		}

		
	}
	
	public void keyPressed(KeyEvent e) {
	//	System.out.println("Action: " + e);
		
		int kc = e.getKeyCode();
		
		if (kc == KeyEvent.VK_LEFT) {
			slideDelta = -5;
			timer.start();
		}
		
		if (kc == KeyEvent.VK_RIGHT) {
			slideDelta = 5;
			
		//	select ++ ;
		//	select = select % dalekList.size();
			timer.start();
		}
		
		if (kc == KeyEvent.VK_SPACE) {
			selectedDaleks.add(new Integer(select));
			this.repaint();
		}
		
		if (kc == KeyEvent.VK_ENTER) {
			if (selectedDaleks.size() > 0) {
				callback.setSelectedDaleks(selectedDaleks);
			}
		}
		
    }
	
	public void keyReleased(KeyEvent e) {
	//	System.out.println("Action: " + e);
    }
	public void keyTyped(KeyEvent e) {
		//System.out.println("Action: " + e);
    }
	
	
}