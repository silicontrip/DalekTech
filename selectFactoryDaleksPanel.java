
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.net.URL;


public class selectFactoryDaleksPanel extends JPanel implements KeyListener {
	
	int dalekWidth;
	int dalekIconWidth;

	ArrayList<Dalek> dalekList;
	ArrayList<Integer> selectedDaleks;
	int select;
	HashMap<String,Image> dalekImages;

	void setDalekWidth( int i) { dalekWidth = i; }
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
		dalekImages = InitDalekImage();
	}
	
	public selectFactoryDaleksPanel (ArrayList<Dalek> dalekList) {
		super();
		this.dalekList = dalekList;
		dalekWidth = 1;
		addKeyListener(this);
		setFocusable(true);

		select = 0;
		selectedDaleks = new ArrayList<Integer>();
		dalekImages = InitDalekImage();
	}
	
	HashMap<String,Image> InitDalekImage() {
		
		HashMap<String,Image> imageMap = new HashMap<String,Image>();
		
		imageMap.put("Black Renegade",getImageWithFilename("Images/BlackRenegade.png"));
		imageMap.put("Black Supreme",getImageWithFilename("Images/BlackSupreme.png"));
		imageMap.put("Blue Drone",getImageWithFilename("Images/BlueDrone.png"));
		imageMap.put("Emperor Time War",getImageWithFilename("Images/EmperorTimeWar.png"));
		imageMap.put("Gold Supreme",getImageWithFilename("Images/GoldSupreme.png"));
		imageMap.put("Gold Time War",getImageWithFilename("Images/GoldTimeWar.png"));
		imageMap.put("Grey Renegade",getImageWithFilename("Images/GreyRenegade.png"));
		imageMap.put("Imperial",getImageWithFilename("Images/Imperial.png"));
		imageMap.put("Red Commander",getImageWithFilename("Images/RedCommander.png"));
		imageMap.put("Red Saucer Pilot",getImageWithFilename("Images/RedSaucerPilot.png"));
		imageMap.put("Special Weapon",getImageWithFilename("Images/SpecialWeapon.png"));
		
		return imageMap;
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
	
	Image getImageWithFilename (String fn) {
		URL imageURL = DalekTech.class.getResource(fn);
		// graphic should not return null
		// indicates programming error elsewhere
	//	if (imageURL != null) {
			return new ImageIcon(imageURL).getImage();
	//	}
	//	System.err.println ("Graphic Factory Returns Null");
	//	return null;
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
			
			int xl = 0;
			String dalekName;
			
			
			for (int d=select-2;d<=select+2;d++) {
				int dal = d % dalekList.size();
				if (dal < 0) { dal += dalekList.size();}
				
				dalekName = dalekList.get(dal).getName();
				this.drawDalekAt(g,dalekName,xl,360,getDalekWidth());

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
	
	public void keyPressed(KeyEvent e) {
		System.out.println("Action: " + e);
		
		int kc = e.getKeyCode();
		
		if (kc == KeyEvent.VK_LEFT) {
			select -- ;
			if (select < 0) { select += dalekList.size();}
			this.repaint();

		}
		
		if (kc == KeyEvent.VK_RIGHT) {
			select ++ ;
			select = select % dalekList.size();
			this.repaint();
		}
		
		if (kc == KeyEvent.VK_ENTER) {
			selectedDaleks.add(new Integer(select));
			this.repaint();
		}
		
		
    }
	
	public void keyReleased(KeyEvent e) {
		System.out.println("Action: " + e);
    }
	public void keyTyped(KeyEvent e) {
		System.out.println("Action: " + e);
    }
	
	
}