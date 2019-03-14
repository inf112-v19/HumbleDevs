
package inf112.skeleton.app.game;

import inf112.skeleton.app.GameObjects.Items.ConveyorBelt;
import inf112.skeleton.app.GameObjects.Items.Flag;
import inf112.skeleton.app.GameObjects.Items.Gear;
import inf112.skeleton.app.GameObjects.Items.Hammer;
import inf112.skeleton.app.GameObjects.Items.IItem;
import inf112.skeleton.app.GameObjects.Items.Laser;
import inf112.skeleton.app.GameObjects.Items.Pit;
import inf112.skeleton.app.GameObjects.Items.Wall;
import inf112.skeleton.app.GameObjects.Items.Wrench;

import java.util.ArrayList;

import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.GameObjects.Robot;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.card.Action;
import inf112.skeleton.app.card.ProgramCard;
import inf112.skeleton.app.card.ProgramCardDeck;

/**
 * The game class that controls most of the game. The game class is the class that
 * keeps track of everything that happens.
 * 
 * Note for assignment 3: Some methods in this class is not implemented or only partially. The reason
 * for this is because it makes it easy for us to see what needs to be done.
 * Methods that not are implemented, but just for the structure
 * 	- shootLasers()
 *  - initializePlayers()
 *  	* We have not decided how we should make this and is for know completely trivial
 *   - updateBoard()
 *   	* This is a extremely important method that updates the board on where the robots are located.
 *   	  It has to remove the robots from the old location and place them on the new. We have not
 *   	  found a solution that we are satisfied with yet, it is therefore not implemented yet. We may
 *   	  even put it in another class.
 * @author Even Kolsgaard
 *
 */
public class Game {
	private static Board board;
	private static Robot[] robots;
	private ProgramCardDeck cardPack;
	private static int round;
	
	
	public Game(Board board, int players) {
		this.board = board;
		this.cardPack = new ProgramCardDeck();
		this.round = 1;
		robots = new Robot[players];
		initializePlayers(players);	
	}
	
	/**
	 * Starts the a new round by dealing new cards to every player
	 */
	public void startRound() {
		for(int x = 0; x < robots.length; x++) {
			ProgramCard[] newCards = cardPack.getRandomCards();
			robots[x].chooseCards(newCards);
		}
	}
	
	/**
	 * Calls the methods to that makes up a round. Not every method this method uses is 
	 * implemented, but it gives a nice illustration on how the game should work.
	 */
	public void round() {
		for (int x = 0; x < 5; x++) {
			phase(x);
			activateMovement();
			activatePassiveItems();
			shootLasers();
		}
		assessDamage();
		respawnRobots();
	}
	/**
	 * Starts one phase. The robots are first sorted with respect to the priority of the card that
	 * is going to be used this round. For every robot the robotDoTurn - method is called.
	 * @param nr the number of the phase in this round
	 */
	public void phase(int nr) {
		int[] prio = findPriority(nr);
		for(int x = robots.length-1; x >= 0 ; x--) {
			int robot = prio[x];
			robotDoTurn(robots[robot],nr);
		}
	}
	/**
	 * Method that does the movement actions on the board e.g. gear
	 */
	public void activateMovement() {
		for(int x = 0; x < robots.length; x++) {
			Robot rob = robots[x];
			if(!rob.isDestroyed()) continue;
			Position pos = rob.getPosition();
			IItem s = (IItem) board.getItems(pos);
			ArrayList<IItem> items = board.getItems(pos);
			for(int y = 0; y < items.size(); y++) {
				IItem item = items.get(0);
				// Implementation is, for now, only for single collectionbands
				if(item instanceof ConveyorBelt) {
					//int steps = ((CollectionBand) s).getMovement();
					Action rotation = ((ConveyorBelt) s).getRotation();
					if(rotation.equals(Action.LEFT)) {
						rob.rotateLeft();
						rob.move(((ConveyorBelt) s).getDirection().left());
					} else if(rotation.equals(Action.RIGHT)) {
						rob.move(((ConveyorBelt) s).getDirection().right());
						rob.rotateRight();
					}
				} else if (item instanceof Gear) {
					Action rotation = ((Gear) s).getAction();
					if(rotation == Action.LEFT) {
						rob.rotateLeft();
					} else {
						rob.rotateRight();
					}
				} else {
					continue;
				}
			}
		}
	}
	
	/**
	 * Method that activates the activities on the board that doesn't change the robots placement
	 * or/and rotation e.g. laser
	 */
	public void activatePassiveItems() {
		for(int x = 0; x < robots.length; x++) {
			Robot rob = robots[x];
			ArrayList<IItem> items = board.getItems(robots[x].getPosition());
			for(int y = 0; y < items.size(); y++) {
				IItem item = items.get(y);
				if(item instanceof Laser) {
					// To do
				} else if(item instanceof Flag) {
					rob.visitFlag();
				} else {
					continue;
				}
			}
		}
	}
	/**
	 * To do
	 */
	public void shootLasers() {
		// To do
	}
	
	/**
	 * Respawn the destroyed robots if the player has more lifetokens
	 */
	public void respawnRobots() {
		for(int x = 0; x < robots.length; x++) {
			Robot rob = robots[x];
			if(rob.isDestroyed()) {
				if(!rob.gameOver()) {
					rob.respawn();
				}
			}
		}
	}
	
	public void assessDamage() {
		for(int x = 0; x < robots.length; x++) {
			Robot rob = robots[x];
			ArrayList<IItem> items = board.getItems(rob.getPosition());
			for(int y = 0; y < items.size(); y++) {
				IItem item = items.get(y);
				if(item instanceof Wrench) {
					rob.repairDamage();
				} else if(item instanceof Hammer){
					rob.repairDamage();
					// Draw option card
				}
			}
		}
	}
	/**
	 * Checks if any robots have visited all flags.
	 * @return the robot that have visited all flags, returns null if nobody has done it yet.
	 */
	public Robot finished() {
		for(int x = 0; x < robots.length; x++) {
			if(robots[x].visitedFlags() == 3) {
				return robots[x];
			}
		}
		return null;
	}
	/**
	 * This method makes the robot do one turn. It checks the card and does what it says.
	 * If it's a move card, the robotMove - method is called. If it's a rotate card, the robot will
	 * rotate.
	 * @param rob The robot that are going to do its turn
	 * @param nr The phase number
	 */
	public void robotDoTurn(Robot rob,int nr) {
		if(!rob.isDestroyed()) {
			return;
		}
		ProgramCard card = rob.getCards()[nr];
		Action action = card.getAction();
		if(action == Action.LEFT) {
			rob.rotateLeft();
		} else if(action == Action.RIGHT) {
			rob.rotateRight();
		} else if (action == Action.UTURN) {
			rob.rotateRight();
			rob.rotateRight();
		} else if (action == Action.MOVEFORWARD) {
			int move = card.getMove();
			while(move > 0) {
				if(!rob.isDestroyed()) {
					break;
				}
				if(!robotMove(rob,rob.getDirection())) {
					break;
				}
				move--;
			}
		} else {
			int move = card.getMove();
			while(move > 0) {
				if(!rob.isDestroyed()) {
					break;
				}
				if(!robotMove(rob,rob.getDirection().getOppositeDirection())) {
					break;
				}
				move--;
			}
		}
	}
	/**
	 * Method that moves the robot. Checks the new position and depending on that, the robot will
	 * either move or stand still. If there is a robot in the way, this method is used again on 
	 * that robot.
	 * @param rob the robot that are going to be moved
	 * @param dir the direction of the movement
	 * @return True if the robot is moved, false otherwise
	 */
	public boolean robotMove(Robot rob, Direction dir) {
		Position pos = new Position(rob.getX(),rob.getY());
		switch(dir) {
			case NORTH: pos.moveNorth();
			break;
			case EAST: pos.moveEast();
			break;
			case WEST: pos.moveWest();
			break;
			case SOUTH: pos.moveSouth();
			break;
		}
		int xPos = pos.getX();
		int yPos = pos.getY();
		if(xPos > board.getWidth() || xPos < 0 || yPos < 0 || yPos > board.getHeight()) {
			rob.die();
			return true;
		}
		ArrayList<IItem> items = board.getItems(pos);
		for(int x = 0; x < items.size(); x++) {
			IItem it = items.get(x);
			if(it instanceof Pit) {
				rob.die();
				return true;
			}
			if(it instanceof Wall) {
				return false;
			}
		}
		if(board.isFree(pos)) {
			rob.move(dir);
			return true;
		}
		Robot rob2 = board.getRobot(pos);
		boolean moved = robotMove(rob2,dir);
		if(!moved) {
			return false;
		}
		rob.move(dir);
		
		return true;
	}
	
	/**
	 * The method that orders the robots with respect to the priority of the card that each robot
	 * is going to play this turn
	 * @param cardnr the number of the phase
	 * @return an array with the right order
	 */
	public int[] findPriority(int cardnr) {
		double[][] pri = new double[robots.length][2];
		for(int x = 0; x < robots.length; x++) {
			pri[x][1] = x;
			pri[x][0] = robots[x].getCards()[cardnr].getPriority();
		}
		java.util.Arrays.sort(pri, new java.util.Comparator<double[]>() {
		    public int compare(double[] a, double[] b) {
		        return Double.compare(a[0], b[0]);
		    }
		});
		int[] prio = new int[robots.length];
		for(int x = 0; x < robots.length; x++) {
			prio[x] = (int) pri[x][1];
		}
		return prio;
	}
	
	public void initializePlayers(int numb) {
		for(int x = 0; x < numb; x++) {
			Player per = new Player(Direction.NORTH, 2, 2, "Robot");
			robots[x] = per;
		}
	}
	
	/**
	 * Method only used to test the sorting based on the card priority.
	 * @return
	 */
	public Robot[] getRobots() {
		return this.robots;
	}

}
