import java.awt.Image;
import java.util.Random;

import oop.ex2.GameGUI;
/**
 * This ship flies in a randomly way. in each round it chose randomly 
 * weather to turn left or right or not turning at all.
 * 
 * @author rani.aboraya
 */
public class DrunkardShip extends SpaceShip {
	private Random rand = new Random();
	private static final int TIME_TO_GET_CRAZY = 10;
	private int roundsCounter;

	/**
	 * constructor for the drunkard ship.
	 */
	public DrunkardShip(){
		super();
		this.roundsCounter = 0;

	}
	/**Does the actions of this ship for this round.
	 * This is called once per round by the SpaceWars game driver.
	 *@param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game){
	int randomTurn = rand.nextInt(DrunkardShip.LEFT_TURN + 1);
	randomTurn += DrunkardShip.RIGHT_TURN;
	for(int i = 0; i < 5; i++){
		getPhysics().move(DrunkardShip.IS_ACCELERATE,randomTurn);
	}
	if(this.roundsCounter++ > DrunkardShip.TIME_TO_GET_CRAZY){
		teleport();
		this.roundsCounter = 0;
	}
	this.roundsCounter++;
	this.curEnergy++;
	}


}
