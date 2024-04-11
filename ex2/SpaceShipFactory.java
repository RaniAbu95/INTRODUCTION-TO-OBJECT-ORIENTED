/**
 * This class acts as a spaceship factory for the Space Wars game. 
 * @author rani.aboraya
 *
 */
public class SpaceShipFactory {
	
	private static int HUMAN_COUNTER = 0;
	private static final String HUMAN_SHIP = "h";
	private static final String RUNNER_SHIP = "r";
	private static final String BASHER_SHIP = "b";
	private static final String AGGRESSIVE_SHIP = "a";
	private static final String SPECIAL_SHIP = "s";
	private static final String DRUNKARD_SHIP = "d";
	
	/**
	*Creates spaceships. You should complete this method.
	*@param args an array which indicates the type of spaceships to create.
	* @return shipsArray An array that contains space ships objects. 
	*/
	
    public static SpaceShip[] createSpaceShips(String[] args) {
    	SpaceShip[] shipsArray = new SpaceShip[args.length];
    	for (int i=0;i<args.length;i++){
    		if(args[i].equals(HUMAN_SHIP)){
    			shipsArray[i]= new HumanShip();
				SpaceShipFactory.HUMAN_COUNTER++;
    			continue;
    		}
    		else if(args[i].equals(RUNNER_SHIP)){
    			shipsArray[i]= new RunnerShip();
    			continue;
    		}
			else if(args[i].equals(AGGRESSIVE_SHIP)) {
				shipsArray[i] = new AggressiveShip();
				continue;
			}
			else if (args[i].equals(DRUNKARD_SHIP)){
				shipsArray[i]= new DrunkardShip();
				continue;
			}

			else if(args[i].equals(SPECIAL_SHIP)){
				shipsArray[i]= new SpecialShip();
				continue;
			}
    		else if(args[i].equals(BASHER_SHIP)){
    			shipsArray[i]= new BasherShip();
    			continue;
    		}

    	}

    	if(SpaceShipFactory.HUMAN_COUNTER > 1) {//checks if there are more than one human ship.
			return null;
		}
		return (shipsArray);
    }
}
