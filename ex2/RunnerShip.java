import java.awt.Image;

import oop.ex2.*;

/**This spaceship attempts to run away from the fight. It will always accelerate,
 *and will constantly turn away from the closest ship. If the nearest ship is 
 *closer than 0.25 units, and if its angle to the Runner is less than 0.23 radians,
 *the Runner feels threatened and will attempt to teleport.
 *
 * @author rani.aboraya
 */


public class RunnerShip extends SpaceShip {

	private static final double DISTANCE_TO_CLOSER = 0.25;
	private static final double ANGLE_FROM_CLOSER = 0.23;
	private static final int ZERO_ANGLE = 0;

	/**
	 * constructor for the runner ship.
 	 */
	public RunnerShip(){
		super();
	}
	
    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
	
	 public void doAction(SpaceWars game) {
		 SpaceShip closerShip= game.getClosestShipTo(this);
		 double distance = getPhysics().distanceFrom(closerShip.getPhysics());
		 double angle = getPhysics().angleTo(closerShip.getPhysics());
		 getPhysics().move(RunnerShip.IS_ACCELERATE, RunnerShip.NO_TURN);
		 if(angle < RunnerShip.ANGLE_FROM_CLOSER && distance < RunnerShip.DISTANCE_TO_CLOSER) {
			 teleport();
		 }
		 if (angle < RunnerShip.ZERO_ANGLE && this.physics.getAngle() >= -Math.PI){
			 getPhysics().move(RunnerShip.IS_ACCELERATE, RunnerShip.LEFT_TURN);
		 }
		 if (angle > RunnerShip.ZERO_ANGLE && this.physics.getAngle() <= Math.PI) {
			 getPhysics().move(RunnerShip.IS_ACCELERATE, RunnerShip.RIGHT_TURN);
		 }
		 if(angle == RunnerShip.ZERO_ANGLE) {
			 getPhysics().move(RunnerShip.IS_ACCELERATE, RunnerShip.NO_TURN);
		 }
		 if(this.curEnergy < this.maximalEnergy){
			 this.curEnergy++;
		 }
		 
	 }
}
