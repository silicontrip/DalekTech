import java.util.ArrayList;

public class DalekFactory {
	
	private static DalekFactory instance = null;
	
	public static DalekFactory getInstance() {
		if (instance == null) {
			instance = new DalekFactory();
		}
		return instance;
	}
	
	
	static Dalek getRedCommander() {
		
		
		Dalek dalek = new Dalek("Red Commander", 4, 6, 4);
		
		DalekSection neck = new DalekSection ("Neck",6);
		DalekSectionDome dome = new DalekSectionDome("Dome",8,neck);
		DalekSection rightShoulder = new DalekSection ("Right Shoulder",8,neck,new Weapon("Pincer",0,1, 0, 2, 6, 6, 6,-1));
		DalekSection leftShoulder = new DalekSection ("Left Shoulder",8,neck,new Weapon("Medium Laser",0,3, 6, 9, 5, 5, 5,-1));
		DalekSectionSkirt rightSkirt = new DalekSectionSkirt ("Right Skirt", 16, rightShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		DalekSectionSkirt leftSkirt = new DalekSectionSkirt ("Left Skirt", 16, leftShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		
		dalek.addSection(neck);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightSkirt);
		dalek.addSection(rightSkirt);
		dalek.addSection(dome);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftShoulder);
		dalek.addSection(leftShoulder);
		dalek.addSection(neck);
		
		return dalek;
	}
	
	
	static Dalek getBlackSupreme() {
		
		Dalek dalek = new Dalek("Black Supreme", 4, 6, 4);
		
		DalekSection neck = new DalekSection ("Neck",6);
		DalekSectionDome dome = new DalekSectionDome("Dome",8,neck);
		DalekSection rightShoulder = new DalekSection ("Right Shoulder",8,neck,new Weapon("Plunger",0,1, 0, 2, 6, 6, 6,-1));
		DalekSection leftShoulder = new DalekSection ("Left Shoulder",8,neck,new Weapon("Medium Laser",0,3, 6, 9, 5, 5, 5,-1));
		DalekSectionSkirt rightSkirt = new DalekSectionSkirt ("Right Skirt", 16, rightShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		DalekSectionSkirt leftSkirt = new DalekSectionSkirt ("Left Skirt", 16, leftShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		
		dalek.addSection(neck);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightSkirt);
		dalek.addSection(rightSkirt);
		dalek.addSection(dome);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftShoulder);
		dalek.addSection(leftShoulder);
		dalek.addSection(neck);
		
		return dalek;
	}
	
	
	static Dalek getBlueDrone() {
		Dalek dalek = new Dalek("Blue Drone", 4, 6, 4);
		
		DalekSection neck = new DalekSection ("Neck",6);
		DalekSectionDome dome = new DalekSectionDome("Dome",8,neck);
		DalekSection rightShoulder = new DalekSection ("Right Shoulder",8,neck,new Weapon("Pincer",0,1, 0, 2, 6, 6, 6,-1));
		DalekSection leftShoulder = new DalekSection ("Left Shoulder",8,neck,new Weapon("Medium Laser",0,3, 6, 9, 5, 5, 5,-1));
		DalekSectionSkirt rightSkirt = new DalekSectionSkirt ("Right Skirt", 16, rightShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		DalekSectionSkirt leftSkirt = new DalekSectionSkirt ("Left Skirt", 16, leftShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		
		dalek.addSection(neck);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightSkirt);
		dalek.addSection(rightSkirt);
		dalek.addSection(dome);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftShoulder);
		dalek.addSection(leftShoulder);
		dalek.addSection(neck);
		
		return dalek;
	}
	
	static Dalek getRedSaucer() {
		Dalek dalek = new Dalek("Red Saucer Pilot", 4, 6, 4);
		
		DalekSection neck = new DalekSection ("Neck",6);
		DalekSectionDome dome = new DalekSectionDome("Dome",8,neck);
		DalekSection rightShoulder = new DalekSection ("Right Shoulder",8,neck,new Weapon("Pincer",0,1, 0, 2, 6, 6, 6,-1));
		DalekSection leftShoulder = new DalekSection ("Left Shoulder",8,neck,new Weapon("Medium Laser",0,3, 6, 9, 5, 5, 5,-1));
		DalekSectionSkirt rightSkirt = new DalekSectionSkirt ("Right Skirt", 16, rightShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		DalekSectionSkirt leftSkirt = new DalekSectionSkirt ("Left Skirt", 16, leftShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		
		dalek.addSection(neck);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightSkirt);
		dalek.addSection(rightSkirt);
		dalek.addSection(dome);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftShoulder);
		dalek.addSection(leftShoulder);
		dalek.addSection(neck);
		
		return dalek;
	}
	
	static Dalek getDalekSupreme() {
		
		Dalek dalek  = new Dalek(new String("Gold Supreme"), 3, 5, 4);		
		DalekSection neck = new DalekSection ("Neck",8,null);
		DalekSectionDome dome = new DalekSectionDome("Dome",10,neck);
		DalekSection rightShoulder = new DalekSection ("Right Shoulder",12,neck,new Weapon("Plunger",0,1, 0, 2, 6, 6, 6,-1));
		DalekSection leftShoulder = new DalekSection ("Left Shoulder",12,neck,new Weapon("Large Laser", 0,5,10,15, 8, 8, 8,-1));
		DalekSectionSkirt rightSkirt = new DalekSectionSkirt ("Right Skirt", 16, rightShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		DalekSectionSkirt leftSkirt = new DalekSectionSkirt ("Left Skirt", 16, leftShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		
		dalek.addSection(neck);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightSkirt);
		dalek.addSection(rightSkirt);
		dalek.addSection(dome);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftShoulder);
		dalek.addSection(leftShoulder);
		dalek.addSection(neck);
		
		
		return dalek;
	}
	
	static Dalek getGreyRenegade() {
		
		Dalek dalek = new Dalek("Grey Renegade", 4, 6, 4);
		
		DalekSection neck = new DalekSection ("Neck",6);
		DalekSectionDome dome = new DalekSectionDome("Dome",8,neck);
		DalekSection rightShoulder = new DalekSection ("Right Shoulder",8,neck,new Weapon("Plunger",0,1, 0, 2, 6, 6, 6,-1));
		DalekSection leftShoulder = new DalekSection ("Left Shoulder",8,neck,new Weapon("Medium Laser",0,3, 6, 9, 5, 5, 5,-1));
		DalekSectionSkirt rightSkirt = new DalekSectionSkirt ("Right Skirt", 16, rightShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		DalekSectionSkirt leftSkirt = new DalekSectionSkirt ("Left Skirt", 16, leftShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		
		dalek.addSection(neck);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightSkirt);
		dalek.addSection(rightSkirt);
		dalek.addSection(dome);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftShoulder);
		dalek.addSection(leftShoulder);
		dalek.addSection(neck);
		
		return dalek;
	}
	
	static Dalek getBlackRenegade() {
		
		Dalek dalek = new Dalek(new String("Black Supreme"), 6, 9, 4);		
		DalekSection neck = new DalekSection ("Neck",8);
		DalekSectionDome dome = new DalekSectionDome("Dome",8,neck);
		DalekSection rightShoulder = new DalekSection ("Right Shoulder",10,neck,new Weapon("Plunger",0,1, 0, 2, 6, 6, 6,-1));
		DalekSection leftShoulder = new DalekSection ("Left Shoulder",10,neck,new Weapon("Large Laser", 0,5,10,15, 8, 8, 8,-1));
		DalekSectionSkirt rightSkirt = new DalekSectionSkirt ("Right Skirt", 16, rightShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		DalekSectionSkirt leftSkirt = new DalekSectionSkirt ("Left Skirt", 16, leftShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		
		dalek.addSection(neck);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightSkirt);
		dalek.addSection(rightSkirt);
		dalek.addSection(dome);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftShoulder);
		dalek.addSection(leftShoulder);
		dalek.addSection(neck);
		
		
		return dalek;
	}
	
	static Dalek getImperial() {
		
		Dalek dalek = new Dalek(new String("Imperial"), 4, 6, 4);
		
		DalekSection neck = new DalekSection ("Neck",6);
		DalekSectionDome dome = new DalekSectionDome("Dome",8,neck);
		DalekSection rightShoulder = new DalekSection ("Right Shoulder",8,neck,new Weapon("Plunger",0,1, 0, 2, 6, 6, 6,-1));
		DalekSection leftShoulder = new DalekSection ("Left Shoulder",8,neck,new Weapon("Medium Laser",0,3, 6, 9, 5, 5, 5,-1));
		DalekSectionSkirt rightSkirt = new DalekSectionSkirt ("Right Skirt", 16, rightShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		DalekSectionSkirt leftSkirt = new DalekSectionSkirt ("Left Skirt", 16, leftShoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		
		dalek.addSection(neck);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightShoulder);
		dalek.addSection(rightSkirt);
		dalek.addSection(rightSkirt);
		dalek.addSection(dome);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftShoulder);
		dalek.addSection(leftShoulder);
		dalek.addSection(neck);
		
		
		return dalek;
	}
	
	static Dalek getSpecialWeapon() {
		
		
		Dalek dalek = new Dalek(new String("Special Weapon"), 2, 3, 4);
		
		DalekSection neck = new DalekSection ("Neck",12);
		DalekSectionDome dome = new DalekSectionDome("Dome",6,neck);
		DalekSection shoulder = new DalekSection ("Shoulder",20,neck,new Weapon("PPC",3,6,12,18,10,10,10,-1));
		DalekSectionSkirt rightSkirt = new DalekSectionSkirt ("Right Skirt", 20, shoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		DalekSectionSkirt leftSkirt = new DalekSectionSkirt ("Left Skirt", 20, shoulder,new Weapon("Auto Bomb/10",0,1,2,3,10,10,10,1));
		
		dalek.addSection(neck);
		dalek.addSection(shoulder);
		dalek.addSection(shoulder);
		dalek.addSection(rightSkirt);
		dalek.addSection(rightSkirt);
		dalek.addSection(dome);
		dalek.addSection(leftSkirt);
		dalek.addSection(leftSkirt);
		dalek.addSection(shoulder);
		dalek.addSection(shoulder);
		dalek.addSection(neck);
		
		
		return dalek;
	}
	
	static ArrayList<Dalek> getAllDaleks() {
	
		ArrayList<Dalek> dalekList = new ArrayList<Dalek>();
		
		dalekList.add(getRedCommander());
		dalekList.add(getBlackSupreme());
		dalekList.add(getBlueDrone());
		dalekList.add(getRedSaucer());
		dalekList.add(getDalekSupreme());
		dalekList.add(getGreyRenegade());
		dalekList.add(getBlackRenegade());
		dalekList.add(getImperial());
		dalekList.add(getSpecialWeapon());

		return dalekList;
	}
	
}	