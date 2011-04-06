import java.awt.*;
import java.awt.image.*;

public class AddComposite implements Composite {
	
	public static AddComposite DEFAULT = new AddComposite();
	
	private AddComposite(){
	}
	
	public CompositeContext createContext(ColorModel srcColorModel,ColorModel dstColorModel,RenderingHints hints) {
		
		return new CompositeContext(){
			public void compose(Raster src,Raster dstIn,WritableRaster dstOut){
				
				for (int x=0;x<dstOut.getWidth();x++){
					for (int y=0;y<dstOut.getHeight();y++){
						// Get the source pixels
						int[] srcPixels = new int[4];
						int[] dstPixels = new int[4];
						
						src.getPixel(x,y,srcPixels);
						dstIn.getPixel(x,y,dstPixels);
						
						srcPixels[0] = srcPixels[0] + dstPixels[0];
						srcPixels[1] = srcPixels[1] + dstPixels[1];
						srcPixels[2] = srcPixels[2] + dstPixels[2];
						dstOut.setPixel(x,y,srcPixels);					
						
					}
				}
			}
			public void dispose(){
			}
		};
		
	}
}
