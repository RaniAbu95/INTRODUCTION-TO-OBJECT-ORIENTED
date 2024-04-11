import java.awt.Image;

import oop.ex2.*;

/**
 * This ship has a unique behavior. The ship flies in circles and and it has
 * a higher speed comparing with all other space ships. This ship also shots
 * every round and teleports every 20 rounds and keeps its shield up during all the game.
 * @author rani.aboraya
 *
 */
public class SpecialShip extends SpaceShip{
	private int counterRounds = 0;
	private static final int CONST_VALUE_ONE = 10;
	private static final int CONST_VALUE_TWO = 2;
	private static final int ROUNDS_TO_TELEPORT = 20;

	/**
	 * constructor for the special ship.
	 */
	public SpecialShip(){
		super();
	}
    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
	public void doAction(SpaceWars game){
		getPhysics().move(SpecialShip.IS_ACCELERATE, SpecialShip.NO_TURN);
		if(counterRounds % 2 == 0){ //do this code in every even rounds.
			for(int i =0; i < CONST_VALUE_ONE; i++) {
				getPhysics().move(SpecialShip.IS_ACCELERATE, SpecialShip.RIGHT_TURN);
			}

		}
		else {
			for (int i = 0; i < CONST_VALUE_TWO; i++) {
				getPhysics().move(SpecialShip.IS_ACCELERATE, SpecialShip.LEFT_TURN);
			}
		}
		if(counterRounds % ROUNDS_TO_TELEPORT == 0){
			teleport();
		}
		shieldOn();
		fire(game);
		counterRounds++;
	}

	/**
	 * overrides the ShieldOn method at the SpaceShip and make the Special Ship put its shield on even though it
	 * does't have enough energy.
	 */
	public void shieldOn(){
		this.shieldOn = true;
	}

	/**
	 * overrides the teleport method at the SpaceShip class and make the Special Ship teleport even though it
	 * does't have enough energy.
	 */
	public void teleport() {
		this.physics = new SpaceShipPhysics();
	}

}
