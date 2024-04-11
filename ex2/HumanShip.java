import java.awt.Image;

import oop.ex2.*;

/**Controlled by the user. The keys are: left-arrow and right-arrow to turn, 
 * up-arrow to accelerate, 'd' to fire a shot, 's' to turn on the shield, 'a' 
 * to teleport. You can assume there will be at most one human controlled ship
 *  in a game, but you're not required to enforce this
 */

public class HumanShip extends SpaceShip {
	private int roundsCounter;
	private static final int ROUNDS_TO_SHOT = 8;

	/**
	 * constructor for the human ship.
	 */
	public HumanShip(){
		super();
		roundsCounter = ROUNDS_TO_SHOT;
	}
	
	/**Does the actions of this ship for this round.
	 * This is called once per round by the SpaceWars game driver.
	 *@param game the game object to which this ship belongs.
	 */
	
	public void doAction(SpaceWars game) {
		if(game.getGUI().isTeleportPressed()) {
			teleport();
		}
		if(game.getGUI().isUpPressed()&&!game.getGUI().isLeftPressed()&&!game.getGUI().isRightPressed()){
			getPhysics().move(HumanShip.IS_ACCELERATE, HumanShip.NO_TURN);
		}
		if(game.getGUI().isUpPressed()&&game.getGUI().isLeftPressed()&&!game.getGUI().isRightPressed()){
			getPhysics().move(HumanShip.IS_ACCELERATE, HumanShip.LEFT_TURN);
		}
		if(game.getGUI().isUpPressed()&&!game.getGUI().isLeftPressed()&&game.getGUI().isRightPressed()) {
			getPhysics().move(HumanShip.IS_ACCELERATE, HumanShip.RIGHT_TURN);
		}
		if(game.getGUI().isShieldsPressed()){
			shieldOn();
		}
		else {
			this.shieldOn = false;
		}
		if(game.getGUI().isShotPressed() && roundsCounter >= HumanShip.ROUNDS_TO_SHOT) {
			fire(game);
		}
		if(!game.getGUI().isUpPressed() && game.getGUI().isLeftPressed()&&!game.getGUI().isRightPressed()) {
			getPhysics().move(!HumanShip.IS_ACCELERATE, HumanShip.LEFT_TURN);
		}
		if(!game.getGUI().isUpPressed() &&!game.getGUI().isLeftPressed()&&game.getGUI().isRightPressed()) {
			getPhysics().move(!HumanShip.IS_ACCELERATE, HumanShip.RIGHT_TURN);
		}
		if(!game.getGUI().isUpPressed()&&!game.getGUI().isLeftPressed()&&!game.getGUI().isRightPressed()) {
			getPhysics().move(!HumanShip.IS_ACCELERATE, HumanShip.NO_TURN);
		}
		if(this.curEnergy < this.maximalEnergy){
			this.curEnergy++;
		}
		roundsCounter++;
	}
		
	/**
	 * Gets the image of this ship. This method should return the image of the 
     * ship with or without the shield. This will be displayed on the GUI at 
     * the end of the round
	 * @return the image of this ship.
	 */
	 public Image getImage(){
		 if(this.shieldOn) {
			 return GameGUI.SPACESHIP_IMAGE_SHIELD;
		 }
		 else {
			 return GameGUI.SPACESHIP_IMAGE;
		 }

	 }
	 
	 /** Attempts to fire a shot.
	  *@param game the game object.
	  */
	 
	public void fire(SpaceWars game) {
		game.addShot(getPhysics());
		this.roundsCounter = 0;
		if(this.curEnergy - HumanShip.FIRE_COST < 0) {
			this.curEnergy = 0;
		}
		else{
			this.curEnergy -= HumanShip.FIRE_COST;
		}
	}

}
