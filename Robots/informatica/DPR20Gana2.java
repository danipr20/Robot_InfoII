package informatica;
import robocode.*;
import java.awt.Color;


public class DPR20 extends Robot{
    /**
     * run: DPR20's default behavior
     */
		boolean enemiDetected=false;	boolean posicionamiento=false;	boolean attack=false;
		double myEnergy, myHeading, myGunHeading, myX, myY,enemyVelocity;
		String enemyName="0";
		int bonusFire=0; int bonusRadar=0; int bonusMov=0;
    public void run() {
	    setBodyColor(Color.black);
        setRadarColor(Color.red);
        setGunColor(Color.white);
        setBulletColor(Color.red);
establecerEnemigo();
		int a=1;

	takeCenter();
	
		
if(posicionamiento){enemiDetected=false;attack=false;}

		

	

		
        while (true) {
		System.out.println("enemi:"+enemyName);	System.out.println("fire bonus: " + bonusFire);
		establecerEnemigo();
		if(!enemiDetected){spotEnemy(a,bonusRadar);}
		if(enemiDetected){a=-a;}
		enemiDetected=false;
		if(getEnergy()<40){myRobotInfo();}
		
        }}

    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
	enemiDetected=true;
	enemyName=e.getName();
		enemyVelocity=e.getVelocity();
	fireI(e.getDistance());
	 if (enemyName.equals("sample.PaintingRobot")&&!posicionamiento){
				turnLeft(90);
				ahead(bonusMov);}
	System.out.println("Energy: " + e.getEnergy());
    System.out.println("Distance: " + e.getDistance());
	System.out.println("real"+e.getName());
	System.out.println("var"+enemyName);
	System.out.println("fire bonus: " + bonusFire);
	System.out.println("vel: " + enemyVelocity);
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
	
	 if(!posicionamiento){
				turnLeft(90);
				ahead(bonusMov);
}
    }
    
    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        takeCenter();
    }
    
    public void takeCenter() {	
double startheading=getHeading();
//Al empezar nos posicionamos y tomamos el centro
		posicionamiento=true;	
		if((startheading<=180)){turnLeft(startheading);}
		else{turnRight(360-startheading);}
		ahead((getBattleFieldHeight()/2)-getY());
		turnRight(90);
		ahead((getBattleFieldWidth()/2)-getX());
		posicionamiento=false;
    }
    
    public void spotEnemy(int a, int bonusRadar) {
	int i=10+bonusRadar; 	
			i=i*a;
			turnGunRight(i);
				}


      public void myRobotInfo() {	
		myEnergy=	getEnergy();
myHeading= 	getHeading();
myGunHeading= 	getGunHeading();
myX=getX();
myY=getY();

		
    }       	
			
public void establecerEnemigo(){
	if (enemyName.equals("sample.Corners")) {//parametros optimizados 2,15,75
    bonusFire = 2;
    bonusRadar = 15;
	bonusMov=75;
	
} else if (enemyName.equals("sample.Crazy")) {
    bonusFire = 0;
    bonusRadar = 25;
	bonusMov=40;
	
} else if (enemyName.equals("sample.PaintingRobot")) {
    bonusFire = 0;
    bonusRadar = 10;
	bonusMov=25;
}  else  if (!enemyName.equals("sample.PaintingRobot")&&!enemyName.equals("sample.Crazy")&&!enemyName.equals("sample.PaintingRobot")&&!enemyName.equals("0")) {
    bonusFire = 1;
    bonusRadar = 15;
	bonusMov=100;
}else  if(enemyName.equals("0")){
    bonusFire = 0;
    bonusRadar = 35;
	bonusMov=25;
}
}


	 public void fireI(double robotDistance) {

	if (robotDistance > 300 || getEnergy() < 20) {
			fire(1+bonusFire);
		} else if (robotDistance > 75) {
			fire(2+bonusFire);
		} else {
			fire(3+bonusFire);
		}
	}
		
 
    

}
