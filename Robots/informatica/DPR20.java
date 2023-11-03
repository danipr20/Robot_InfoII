package informatica;

import robocode.*;
import java.awt.Color;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class DPR20 extends Robot {
	// Variables de estado
	boolean esquiva;
	boolean enemiDetected = false;
	boolean posicionamiento = false;
	double gunTurnAmt;
	String enemyName = "0";
	String enemyScan;
	int bonusFire = 0;
	int bonusRadar = 0;
	int bonusMov = 0;
	int buscar = 0;

	public void run() {
		// Configuración de colores
		setBodyColor(Color.black);
		setRadarColor(Color.red);
		setGunColor(Color.white);
		setBulletColor(Color.red);

		establecerEnemigo(); // Ajusta la estrategia según el enemigo
		int a = 1;

		takeCenter(); // Posiciona el robot en el centro del campo de batalla

		if (posicionamiento) {
			enemiDetected = false;
		}

		while (true) {

			if (!enemiDetected) {
				spotEnemy(bonusRadar); // Gira el radar para buscar al enemigo
			}
			enemiDetected = false;
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		establecerEnemigo();
		enemiDetected = true;
		if (enemyScan == null) {
			enemyName = e.getName();
		}
		buscar = 0;
		if (!posicionamiento && (enemyName.equals("sample.Crazy") || enemyName.equals("sample.Corners"))) {
			fireI(e.getDistance());
		} // Decisión de cuánta energía usar para disparar
		else {
			if (e.getDistance() > 200 && !posicionamiento) {
				gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
				turnGunRight(gunTurnAmt);
				turnRight(e.getBearing());
				ahead(e.getDistance() - 50);
				return;
			}

			gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
			turnGunRight(gunTurnAmt);
			fireI(e.getDistance()); // Dispara al enemigo

		}
	}

	public void onHitByBullet(HitByBulletEvent e) {
		// En caso de ser impactado por una bala

		if (!posicionamiento && !esquiva && (enemyName.equals("sample.Corners") || enemyName.equals("sample.Crazy"))) {
			esquiva = true;
			turnLeft(90); // Gira 90 grados
			ahead(bonusMov); // Avanza según la estrategia
			turnGunRight(90);
			esquiva = false;
		}

	}

	public void onHitWall(HitWallEvent e) {
		takeCenter(); /*
						 * Reposicionam el robot en el centro del campo de batalla al
						 * chocar contra una pared
						 */
	}

	public void takeCenter() {
		double startheading = getHeading();
		posicionamiento = true;
		if (startheading <= 180) {
			turnLeft(startheading);
		} else {
			turnRight(360 - startheading);
		}
		ahead((getBattleFieldHeight() / 2) - getY());
		turnRight(90);
		ahead((getBattleFieldWidth() / 2) - getX());
		posicionamiento = false;
	}

	public void spotEnemy(int bonusRadar) {

		turnGunRight(gunTurnAmt);
		buscar++;
		if (buscar > 2) {
			gunTurnAmt = bonusRadar;
		}
		if (buscar > 5) {
			gunTurnAmt = -bonusRadar;
		}
		if (buscar > 11) {
			enemyName = "0";
		}

	}

	public void establecerEnemigo() {
		if (enemyName.equals("sample.Corners")) {
			bonusFire = 3;
			bonusRadar = 5;
			bonusMov = 75;
		} else if (enemyName.equals("sample.Crazy")) {
			bonusFire = 1;
			bonusRadar = 15;
			bonusMov = 40;
		} else if (enemyName.equals("sample.PaintingRobot")) {
			bonusFire = 2;
			bonusRadar = 10;
			bonusMov = 25;
		} else if (!enemyName.equals("sample.Crazy") && !enemyName.equals("sample.Crazy")
				&& !enemyName.equals("sample.PaintingRobot") && !enemyName.equals("0")) {
			bonusFire = 1;
			bonusRadar = 6;
			bonusMov = 10;
		} else if (enemyName.equals("0")) {
			bonusFire = 1;
			bonusRadar = 10;
			bonusMov = 25;
		}
	}

	public void fireI(double robotDistance) {
		if (robotDistance > 300 || getEnergy() < 20) {
			fire(1 + bonusFire); // Dispara con potencia 1 + bonus
		} else if (robotDistance > 75) {
			fire(2 + bonusFire); // Dispara con potencia 2 + bonus
		} else {
			fire(3 + bonusFire); // Dispara con potencia 3 + bonus
		}
	}

	public void onDeath(DeathEvent event) {
		System.out.println("Mierda...");
		System.out.println("Volveré a por ti " + enemyName);
	}
}