import java.awt.Image;

import oop.ex2.GameGUI;
/**This ship pursues other ships and tries to fire at them. It will always 
 *accelerate, and turn towards the nearest ship. When its angle to the nearest
 *ship is less than 0.21 radians, it will try to fire.
 *@author rani.aboraya
 */

public class AggressiveShip extends SpaceShip {
	private static final double ANGLE_FROM_ANOTHER = 0.21;
	private static final int ROUNDS_TO_SHOT = 8;
	private int roundsCounter;
	private static final int ZERO_ANGLE = 0;

	/**
	 * constructor for the aggressive ship.
	 */
	public AggressiveShip(){
		super();
		roundsCounter = 8;
	}
	
	/**Does the actions of this ship for this round.
	 * This is called once per round by the SpaceWars game driver.
	 *@param game the game object to which this ship belongs.
	 */
	
	public void doAction(SpaceWars game){
		SpaceShip closerShip= game.getClosestShipTo(this);
	 	double angle = getPhysics().angleTo(closerShip.getPhysics());
		getPhysics().move(AggressiveShip.IS_ACCELERATE, AggressiveShip.NO_TURN);
		if (angle < AggressiveShip.ZERO_ANGLE && this.physics.getAngle() >= -Math.PI ) {
			getPhysics().move(AggressiveShip.IS_ACCELERATE, AggressiveShip.RIGHT_TURN);
		}
		if (angle > AggressiveShip.ZERO_ANGLE && this.physics.getAngle() <= Math.PI) {
			getPhysics().move(AggressiveShip.IS_ACCELERATE, AggressiveShip.LEFT_TURN);
		}
		if(angle == AggressiveShip.ZERO_ANGLE) {
			getPhysics().move(AggressiveShip.IS_ACCELERATE, AggressiveShip.NO_TURN);
		}
		if(angle < AggressiveShip.ANGLE_FROM_ANOTHER && roundsCounter >= AggressiveShip.ROUNDS_TO_SHOT){
			fire(game);
		}
		if(this.curEnergy < this.maximalEnergy){
			this.curEnergy++;
		}
		roundsCounter++;
	}
	
	 /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
	public void fire(SpaceWars game) {
		game.addShot(getPhysics());
		this.roundsCounter = 0;
		if(this.curEnergy - AggressiveShip.FIRE_COST < 0) {
			this.curEnergy = 0;
		}
		else{
			this.curEnergy -= AggressiveShip.FIRE_COST;
		}
	}

	
}
