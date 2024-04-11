import java.awt.Image;
import oop.ex2.*;
import java.math.*;

/** abstract base class for all the spaceships.
* @author Rani.aboraya
*/
public abstract class SpaceShip{
	
	protected int healthLevel;
	protected int curEnergy ;
	protected int maximalEnergy;
	protected boolean shieldOn;
	protected Physics physics;
	/**
	 * The constant value 0 that indicates that the ship has made no turn.
	 */
	protected static final int NO_TURN = 0;

	/**
	 *The constant value 1 that we send to the move method that indicates that
     * it should go left.
	 */
	protected static final int LEFT_TURN = 1;

	/**
	 * The constant value -1 that we send to the move method that indicates 
     * that it should go right.
	 */
	protected static final int RIGHT_TURN = -1;

	/**
	 * A boolean variable that has the initial value true, which indicates 
     * that the ship is accelerating.
	 */
	protected static final boolean IS_ACCELERATE = true;


	private static  final int INITIAL_HEALTH = 22;

	/**
	 * The initial health value for a space ship.
	 */
	private static  final int INITIAL_MAXIMAL_ENERGY = 210;

	/**
	 * The initial energy value for a space ship.
	 */
	private static  final int INITIAL_ENERGY = 190;

	/**
	 * The amount of energy units that the space ship gets when bashing.
	 */
	private static  final int BASHING_VALUE = 18;

	/**
	 * The amount of energy units that the space ship lose when teleporting.
	 */
	private static  final int TELEPORTING_COST = 140;

	/**
	 * The amount of energy units that the space ship lose when it gets hit.
	 */
	private static  final int HIT_LOST = 10;

	/**
	 * The amount of energy units that the space ship lose when it turns on the shield.
	 */
	private static  final int SHIELD_COST = 3;

	/**
	 * The amount of energy units that the space ship lose when it shot.
	 */
	protected static  final int FIRE_COST = 19;


	/** 
	 * Build a new SpaceShip with the given information(energy,shield,health...)
	 */
	public SpaceShip(){
		this.healthLevel = INITIAL_HEALTH;
		this.shieldOn = false;
		this.maximalEnergy = INITIAL_MAXIMAL_ENERGY;
		this.curEnergy = INITIAL_ENERGY;
		this.physics = new SpaceShipPhysics();
	}
	

   
    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
	public abstract void doAction(SpaceWars game);


	/**
	 * This function updates the health level for a space ship after getting shot or collided with another ship.
	 */
	private void updateAfterHit(){
		if(this.healthLevel - 1 < 0 ){
			this.healthLevel = 0;
		}
		else{
			this.healthLevel --;
		}

		if (this.maximalEnergy - SpaceShip.HIT_LOST < 0) {
			this.maximalEnergy = 0;
		}
		else {
			this.maximalEnergy -= SpaceShip.HIT_LOST;
		}
		if(this.maximalEnergy < this.curEnergy){
			this.curEnergy = this.maximalEnergy;
		}
	}


    /**
     * This method is called every time a collision between two ships occours.
     */
    public void collidedWithAnotherShip(){
    	if(!this.shieldOn){
			updateAfterHit();
		}
    	else{
    		this.maximalEnergy += SpaceShip.BASHING_VALUE;
			this.curEnergy += SpaceShip.BASHING_VALUE;
    	}
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset(){

    	this.healthLevel = SpaceShip.INITIAL_HEALTH;
    	this.curEnergy = SpaceShip.INITIAL_ENERGY;
		this.maximalEnergy = SpaceShip.INITIAL_MAXIMAL_ENERGY;
    	this.shieldOn = false;
    	this.physics = new SpaceShipPhysics();

    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
	public boolean isDead() {
        if(this.healthLevel == 0) {
			return true;
		}
        return false;
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return (SpaceShipPhysics) this.physics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
    	if(!this.shieldOn) {
			updateAfterHit();
		}
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed
     * on the GUI at
     * the end of the round.
     * 
     * @return the image of this ship.
     */
     public Image getImage(){
		if(this.shieldOn) {
			return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
		}
		else {
			return GameGUI.ENEMY_SPACESHIP_IMAGE;
		}

	}

    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game){
		game.addShot(getPhysics());
		this.curEnergy -= SpaceShip.FIRE_COST;
	}


    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn(){
		if (this.curEnergy >= SpaceShip.SHIELD_COST) {
			this.shieldOn = true;
			this.curEnergy -= SpaceShip.SHIELD_COST;
		}
		else{
			this.shieldOn = false;
		}
	}

    /**
     * Attempts to teleport.
     */
    public void teleport() {
    	 if(this.curEnergy >= SpaceShip.TELEPORTING_COST){
    		 this.curEnergy -= SpaceShip.TELEPORTING_COST;
    		 this.physics = new SpaceShipPhysics();
    	 }
    }
    
}
