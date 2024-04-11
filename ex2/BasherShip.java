import java.awt.Image;

import oop.ex2.GameGUI;


/**
 * This ship attempts to collide with other ships. It will always accelerate,
 * and will constantly turn towards the closest ship. If it gets within 
 * a distance of 0.19 units from another ship, it will attempt to turn on its
 * shields.
 * 
 * @author rani.aboraya
 */

public class BasherShip extends SpaceShip {
	private static final double DISTANCE_TO_CLOSER = 0.19;
	private static final int ZERO_ANGLE = 0;


	/**
	 * constructor for the basher ship.
	 */
	public BasherShip(){
		super();
		
	}
	/**Does the actions of this ship for this round.
	 * This is called once per round by the SpaceWars game driver.
	 *@param game the game object to which this ship belongs.
	 */

	public void doAction(SpaceWars game) {
		SpaceShip closerShip = game.getClosestShipTo(this);
		double distance = getPhysics().distanceFrom(closerShip.getPhysics());
		double angle = getPhysics().angleTo(closerShip.getPhysics());
		getPhysics().move(BasherShip.IS_ACCELERATE, BasherShip.NO_TURN);
		if (angle < BasherShip.ZERO_ANGLE && this.physics.getAngle() >= -Math.PI ) {
			getPhysics().move(BasherShip.IS_ACCELERATE, BasherShip.RIGHT_TURN);
		}
		if (angle > BasherShip.ZERO_ANGLE && this.physics.getAngle() <= Math.PI){
			getPhysics().move(BasherShip.IS_ACCELERATE, BasherShip.LEFT_TURN);
		}
		if (angle == BasherShip.ZERO_ANGLE) {
			getPhysics().move(BasherShip.IS_ACCELERATE, BasherShip.NO_TURN);
		}
		if (distance < BasherShip.DISTANCE_TO_CLOSER) {
			shieldOn();
		} else {
			this.shieldOn = false;
		}
	}

}

