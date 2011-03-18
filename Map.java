import java.util.*;
import java.io.*;

public class Map implements Serializable {

	public static final int CLEAR = 0;
	public static final int WOODS = 1;
	public static final int ROUGH = 2;
	public static final int HEAVYWOODS = 3;
	public static final int WATER = 4;
	public static final int PAVEMENT = 5;		
	
	static Map instance = null;
	Hex maparray[][];
	int sizex, sizey;
	String graphicFile;
	int  regtlx, regtly, regbrx, regbry;

       public static Map getInstance() {
                if (instance == null) {
                        instance = new Map();
                }
                return instance;
        }

	
	public Map() { ; }
	
	int getSizeX() { return sizex; }
	int getSizeY() { return sizey; }
	int getRegTLX () { return regtlx; }
	int getRegTLY () { return regtly; }
	int getRegBRX () { return regbrx; }
	int getRegBRY () { return regbry; }

	int getRegHeight() { return regbry - regtly; }
	int getRegWidth() { return regbrx - regtlx; }

	double getRegScaleX () { return this.getRegWidth()/this.getSizeX(); }
	double getRegScaleY () { return this.getRegHeight()/this.getSizeY(); }

	
	String getImageName() { return graphicFile; }
	
	Hex getHexAt(Position p) {
		try {
		return maparray[p.getX()][p.getY()];
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	ArrayList<Hex> getLineOfHex(Position p1,Position p2) {
		
		double xs = p1.getSpatialX();
		double ys = p1.getSpatialY();
		double exs = p2.getSpatialX();
		double eys = p2.getSpatialY();
		double scale = p1.distanceTo(p2) * 2;
		Position here; 
		Position there;
		double deltaX = (exs - xs) / scale;
		double deltaY = (eys - ys) / scale;
		int index=0;
		
		ArrayList<Hex> h = new ArrayList<Hex>();
		
		here = new Position(p1);
		there = new Position(p1);
		h.add(getHexAt(here));
		while (!here.equals(p2)) {
			
			while (here.equals(there)) {
				xs += deltaX;
				ys += deltaY;
				here = new Position(xs,ys);
			}
			h.add(getHexAt(here));
			there.setPosition(here);
		}
		return h;
	}
	
	
	
	
	
	void initSampleMap() {

		maparray=new Hex[15][17];
		sizex=15; sizey=17;
		
		graphicFile = new String("object83_0.png");
		regtlx = 98; regtly = 66;
		regbrx = 1232; regbry = 1564;
			
		maparray[0][0] = new Hex(CLEAR,0);
		maparray[1][0] = new Hex(WOODS,0);
		maparray[2][0] = new Hex(CLEAR,0);
		maparray[3][0] = new Hex(CLEAR,0);
		maparray[4][0] = new Hex(WOODS,0);
		maparray[5][0] = new Hex(CLEAR,0);
		maparray[6][0] = new Hex(CLEAR,0);
		maparray[7][0] = new Hex(CLEAR,0);
		maparray[8][0] = new Hex(CLEAR,0);
		maparray[9][0] = new Hex(WOODS,0);
		maparray[10][0] = new Hex(CLEAR,0);
		maparray[11][0] = new Hex(CLEAR,0);
		maparray[12][0] = new Hex(CLEAR,0);
		maparray[13][0] = new Hex(CLEAR,1);
		maparray[14][0] = new Hex(CLEAR,0);
		
		maparray[0][1] = new Hex(CLEAR,0);
		maparray[1][1] = new Hex(WOODS,0);
		maparray[2][1] = new Hex(WOODS,0);
		maparray[3][1] = new Hex(WATER,-1);
		maparray[4][1] = new Hex(WATER,-1);
		maparray[5][1] = new Hex(WATER,-1);
		maparray[6][1] = new Hex(WATER,-1);
		maparray[7][1] = new Hex(CLEAR,0);
		maparray[8][1] = new Hex(WOODS,0);
		maparray[9][1] = new Hex(WOODS,0);
		maparray[10][1] = new Hex(WOODS,0);
		maparray[11][1] = new Hex(CLEAR,1);
		maparray[12][1] = new Hex(CLEAR,1);
		maparray[13][1] = new Hex(CLEAR,1);
		maparray[14][1] = new Hex(CLEAR,1);

		maparray[0][2] = new Hex(WOODS,0);
		maparray[1][2] = new Hex(WOODS,0);
		maparray[2][2] = new Hex(WATER,-1);
		maparray[3][2] = new Hex(WATER,-1);
		maparray[4][2] = new Hex(WATER,-1);
		maparray[5][2] = new Hex(WATER,-1);
		maparray[6][2] = new Hex(WATER,-1);
		maparray[7][2] = new Hex(CLEAR,0);
		maparray[8][2] = new Hex(CLEAR,0);
		maparray[9][2] = new Hex(CLEAR,0);
		maparray[10][2] = new Hex(CLEAR,0);
		maparray[11][2] = new Hex(CLEAR,1);
		maparray[12][2] = new Hex(CLEAR,1);
		maparray[13][2] = new Hex(CLEAR,1);
		maparray[14][2] = new Hex(CLEAR,1);
		
		maparray[0][3] = new Hex(CLEAR,0);
		maparray[1][3] = new Hex(WOODS,0);
		maparray[2][3] = new Hex(WATER,-1);
		maparray[3][3] = new Hex(WATER,-1);
		maparray[4][3] = new Hex(WATER,-1);
		maparray[5][3] = new Hex(CLEAR,1);
		maparray[6][3] = new Hex(CLEAR,0);
		maparray[7][3] = new Hex(CLEAR,1);
		maparray[8][3] = new Hex(CLEAR,1);
		maparray[9][3] = new Hex(CLEAR,0);
		maparray[10][3] = new Hex(CLEAR,0);
		maparray[11][3] = new Hex(CLEAR,1);
		maparray[12][3] = new Hex(CLEAR,1);
		maparray[13][3] = new Hex(CLEAR,1);
		maparray[14][3] = new Hex(CLEAR,1);

		maparray[0][4] = new Hex(CLEAR,0);
		maparray[1][4] = new Hex(CLEAR,0);
		maparray[2][4] = new Hex(WATER,-1);
		maparray[3][4] = new Hex(WATER,-1);
		maparray[4][4] = new Hex(CLEAR,1);
		maparray[5][4] = new Hex(CLEAR,2);
		maparray[6][4] = new Hex(CLEAR,2);
		maparray[7][4] = new Hex(CLEAR,2);
		maparray[8][4] = new Hex(CLEAR,1);
		maparray[9][4] = new Hex(CLEAR,0);
		maparray[10][4] = new Hex(WOODS,1);
		maparray[11][4] = new Hex(WOODS,1);
		maparray[12][4] = new Hex(CLEAR,1);
		maparray[13][4] = new Hex(CLEAR,1);
		maparray[14][4] = new Hex(CLEAR,1);
		
		maparray[0][5] = new Hex(CLEAR,0);
		maparray[1][5] = new Hex(CLEAR,0);
		maparray[2][5] = new Hex(WATER,-1);
		maparray[3][5] = new Hex(WATER,-1);
		maparray[4][5] = new Hex(CLEAR,1);
		maparray[5][5] = new Hex(CLEAR,2);
		maparray[6][5] = new Hex(CLEAR,2);
		maparray[7][5] = new Hex(CLEAR,2);
		maparray[8][5] = new Hex(CLEAR,2);
		maparray[9][5] = new Hex(CLEAR,0);
		maparray[10][5] = new Hex(WOODS,1);
		maparray[11][5] = new Hex(WOODS,1);
		maparray[12][5] = new Hex(CLEAR,1);
		maparray[13][5] = new Hex(WOODS,0);
		maparray[14][5] = new Hex(CLEAR,0);
		
		maparray[0][6] = new Hex(CLEAR,0);
		maparray[1][6] = new Hex(WATER,-1);
		maparray[2][6] = new Hex(WATER,-1);
		maparray[3][6] = new Hex(WATER,-1);
		maparray[4][6] = new Hex(CLEAR,1);
		maparray[5][6] = new Hex(CLEAR,2);
		maparray[6][6] = new Hex(CLEAR,2);
		maparray[7][6] = new Hex(CLEAR,1);
		maparray[8][6] = new Hex(CLEAR,0);
		maparray[9][6] = new Hex(WOODS,0);
		maparray[10][6] = new Hex(WOODS,0);
		maparray[11][6] = new Hex(WOODS,0);
		maparray[12][6] = new Hex(CLEAR,0);
		maparray[13][6] = new Hex(CLEAR,0);
		maparray[14][6] = new Hex(CLEAR,0);

		maparray[0][7] = new Hex(CLEAR,0);
		maparray[1][7] = new Hex(WATER,-1);
		maparray[2][7] = new Hex(WATER,-1);
		maparray[3][7] = new Hex(WATER,-1);
		maparray[4][7] = new Hex(CLEAR,1);
		maparray[5][7] = new Hex(WOODS,1);
		maparray[6][7] = new Hex(CLEAR,1);
		maparray[7][7] = new Hex(WATER,-1);
		maparray[8][7] = new Hex(CLEAR,0);
		maparray[9][7] = new Hex(CLEAR,0);
		maparray[10][7] = new Hex(WOODS,0);
		maparray[11][7] = new Hex(WATER,-1);
		maparray[12][7] = new Hex(CLEAR,0);
		maparray[13][7] = new Hex(CLEAR,0);
		maparray[14][7] = new Hex(WOODS,0);

		maparray[0][8] = new Hex(CLEAR,0);
		maparray[1][8] = new Hex(WATER,-1);
		maparray[2][8] = new Hex(WATER,-1);
		maparray[3][8] = new Hex(WATER,-1);
		maparray[4][8] = new Hex(WATER,-1);
		maparray[5][8] = new Hex(WOODS,1);
		maparray[6][8] = new Hex(CLEAR,1);
		maparray[7][8] = new Hex(WATER,-1);
		maparray[8][8] = new Hex(CLEAR,0);
		maparray[9][8] = new Hex(WATER,-1);
		maparray[10][8] = new Hex(WATER,-1);
		maparray[11][8] = new Hex(WATER,-1);
		maparray[12][8] = new Hex(CLEAR,0);
		maparray[13][8] = new Hex(CLEAR,0);
		maparray[14][8] = new Hex(CLEAR,0);
		
		maparray[0][9] = new Hex(WATER,-1);
		maparray[1][9] = new Hex(WATER,-1);
		maparray[2][9] = new Hex(WATER,-1);
		maparray[3][9] = new Hex(WATER,-1);
		maparray[4][9] = new Hex(WATER,-1);
		maparray[5][9] = new Hex(WOODS,0);
		maparray[6][9] = new Hex(WATER,-1);
		maparray[7][9] = new Hex(WATER,-2);
		maparray[8][9] = new Hex(WATER,-1);
		maparray[9][9] = new Hex(WATER,-2);
		maparray[10][9] = new Hex(WATER,-2);
		maparray[11][9] = new Hex(WATER,-2);
		maparray[12][9] = new Hex(WATER,-1);
		maparray[13][9] = new Hex(WATER,-1);
		maparray[14][9] = new Hex(CLEAR,0);
		
		maparray[0][10] = new Hex(CLEAR,0);
		maparray[1][10] = new Hex(WATER,-1);
		maparray[2][10] = new Hex(WATER,-1);
		maparray[3][10] = new Hex(CLEAR,1);
		maparray[4][10] = new Hex(WOODS,1);
		maparray[5][10] = new Hex(WATER,-1);
		maparray[6][10] = new Hex(WATER,-1);
		maparray[7][10] = new Hex(WATER,-2);
		maparray[8][10] = new Hex(WATER,-2);
		maparray[9][10] = new Hex(CLEAR,1);
		maparray[10][10] = new Hex(WATER,-2);
		maparray[11][10] = new Hex(WATER,-2);
		maparray[12][10] = new Hex(WATER,-1);
		maparray[13][10] = new Hex(WATER,-1);
		maparray[14][10] = new Hex(CLEAR,0);
		
		maparray[0][11] = new Hex(CLEAR,0);
		maparray[1][11] = new Hex(WATER,-1);
		maparray[2][11] = new Hex(WATER,-1);
		maparray[3][11] = new Hex(WOODS,1);
		maparray[4][11] = new Hex(CLEAR,1);
		maparray[5][11] = new Hex(WATER,-1);
		maparray[6][11] = new Hex(WATER,-2);
		maparray[7][11] = new Hex(CLEAR,1);
		maparray[8][11] = new Hex(CLEAR,1);
		maparray[9][11] = new Hex(CLEAR,1);
		maparray[10][11] = new Hex(CLEAR,1);
		maparray[11][11] = new Hex(WATER,-2);
		maparray[12][11] = new Hex(WATER,-1);
		maparray[13][11] = new Hex(WATER,-1);
		maparray[14][11] = new Hex(CLEAR,0);
		
		maparray[0][12] = new Hex(CLEAR,0);
		maparray[1][12] = new Hex(CLEAR,0);
		maparray[2][12] = new Hex(WATER,-1);
		maparray[3][12] = new Hex(WATER,-1);
		maparray[4][12] = new Hex(CLEAR,1);
		maparray[5][12] = new Hex(WATER,-1);
		maparray[6][12] = new Hex(WATER,-2);
		maparray[7][12] = new Hex(WATER,-2);
		maparray[8][12] = new Hex(CLEAR,1);
		maparray[9][12] = new Hex(WATER,-2);
		maparray[10][12] = new Hex(WATER,-2);
		maparray[11][12] = new Hex(WATER,-1);
		maparray[12][12] = new Hex(WATER,-1);
		maparray[13][12] = new Hex(CLEAR,0);
		maparray[14][12] = new Hex(CLEAR,0);

		maparray[0][13] = new Hex(CLEAR,0);
		maparray[1][13] = new Hex(WATER,-1);
		maparray[2][13] = new Hex(WATER,-1);
		maparray[3][13] = new Hex(WATER,-1);
		maparray[4][13] = new Hex(WATER,-1);
		maparray[5][13] = new Hex(WATER,-1);
		maparray[6][13] = new Hex(WATER,-2);
		maparray[7][13] = new Hex(WATER,-2);
		maparray[8][13] = new Hex(WATER,-2);
		maparray[9][13] = new Hex(WATER,-1);
		maparray[10][13] = new Hex(WATER,-1);
		maparray[11][13] = new Hex(CLEAR,0);
		maparray[12][13] = new Hex(CLEAR,0);
		maparray[13][13] = new Hex(CLEAR,1);
		maparray[14][13] = new Hex(CLEAR,1);
		
		maparray[0][14] = new Hex(CLEAR,0);
		maparray[1][14] = new Hex(WATER,-1);
		maparray[2][14] = new Hex(WATER,-1);
		maparray[3][14] = new Hex(WATER,-1);
		maparray[4][14] = new Hex(WATER,-1);
		maparray[5][14] = new Hex(WATER,-1);
		maparray[6][14] = new Hex(WATER,-1);
		maparray[7][14] = new Hex(WATER,-1);
		maparray[8][14] = new Hex(WATER,-1);
		maparray[9][14] = new Hex(WATER,-1);
		maparray[10][14] = new Hex(CLEAR,1);
		maparray[11][14] = new Hex(CLEAR,1);
		maparray[12][14] = new Hex(CLEAR,1);
		maparray[13][14] = new Hex(CLEAR,1);
		maparray[14][14] = new Hex(WOODS,1);
		
		maparray[0][15] = new Hex(WOODS,0);
		maparray[1][15] = new Hex(WATER,-1);
		maparray[2][15] = new Hex(WATER,-1);
		maparray[3][15] = new Hex(WATER,-1);
		maparray[4][15] = new Hex(WATER,-1);
		maparray[5][15] = new Hex(WATER,-1);
		maparray[6][15] = new Hex(WATER,-1);
		maparray[7][15] = new Hex(WATER,-1);
		maparray[8][15] = new Hex(WATER,-1);
		maparray[9][15] = new Hex(CLEAR,0);
		maparray[10][15] = new Hex(CLEAR,1);
		maparray[11][15] = new Hex(CLEAR,1);
		maparray[12][15] = new Hex(CLEAR,1);
		maparray[13][15] = new Hex(CLEAR,1);
		maparray[14][15] = new Hex(CLEAR,1);
		
		maparray[0][16] = new Hex(CLEAR,0);
		maparray[1][16] = new Hex(CLEAR,0);
		maparray[2][16] = new Hex(CLEAR,0);
		maparray[3][16] = new Hex(CLEAR,0);
		maparray[4][16] = new Hex(WATER,-1);
		maparray[5][16] = new Hex(CLEAR,0);
		maparray[6][16] = new Hex(CLEAR,0);
		maparray[7][16] = new Hex(WATER,-1);
		maparray[8][16] = new Hex(CLEAR,0);
		maparray[9][16] = new Hex(CLEAR,0);
		maparray[10][16] = new Hex(CLEAR,0);
		maparray[11][16] = new Hex(CLEAR,0);
		maparray[12][16] = new Hex(CLEAR,0);
		maparray[13][16] = new Hex(CLEAR,0);
		maparray[14][16] = new Hex(CLEAR,0);
		
	}
	
}
