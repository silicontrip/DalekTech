public class statusPanel extends JPanel {

	String name;
	Image dalekTacticalImage;
	Image dalekDamageImage;
	Image dalekImage;
	Integer engineCurrent;
	Integer engineWalk;
	Integer engineRun;

	public static final int panelWidth = 160;
	public static final int panelHeight = 480;

	Font techFont;
	
	public statusPanel () {
		super();
		
		engineCurrent = new Integer(0);
		engineWalk = new Integer(0);
		engineRun = new Integer(0);
		dalekImage = null;
		
		name = null;
		
		techFont = new Font("Arial",0,10);

		
	}
	
	int getPanelHeight() { return panelHeight; }
	int getPanelWidth() { return panelWidth; }

	void setName(String s) { this.name = new String(s); }
	void setDamageImage(Image i) { this.dalekDamageImage = i; }
	void setTacticalImage(Image i) { this.dalekTacticalImage = i; }
	
	void setEngineCurrent (int i) { engineCurrent = new Integer(i); }
	void setEngineWalk (int i) { engineWalk = new Integer(i); }
	void setEngineRun (int i) { engineRun = new Integer(i); }

	void tacticalView() { dalekImage = this.dalekTacticalImage; }
	void DamageView() { dalekImage = this.dalekDamageImage; }

	
	public Dimension getPreferredSize() {
        return new Dimension(panelWidth,panelHeight);
    }
	
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);


		if ( dalekImage != null) {
			int w = 100;
			int h = (int)  (dalek.getHeight(null) * w / dalek.getWidth(null)  );

			BufferedImage thumbImage = new BufferedImage(mapPanelWidth, mapPanelHeight, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(dalekImage, 0,0, w, h, null);
			g.drawImage(thumbImage,getPanelWidth - (w/2), getPanelHeight() - h,null);
		}
		
		if (this.engineRun > 0) {
				// draw engine graph
		}
		if (this.name != null) {
			g.setFont(techFont);
			g.drawString(this.name,40,24);
		}
	}
	
}
