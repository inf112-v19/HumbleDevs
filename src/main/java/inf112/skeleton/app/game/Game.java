
package inf112.skeleton.app.game;
import java.util.Random;

import inf112.skeleton.app.GameObjects.Items.Flag;
import inf112.skeleton.app.GameObjects.Items.IItem;
import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.GameObjects.Robot;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.card.Action;
import inf112.skeleton.app.card.ProgramCard;
import inf112.skeleton.app.card.ProgramCardDeck;

/**
 * The game class that controls most of the game. The game class is the class that always
 * keeps track of everything that happens.
 * @author Even Kolsgaard
 *
 */
public class Game {
	private static Random rng;
	private static Board board;
	private static Robot[] robots;
	private ProgramCardDeck cardPack;
	private static int round;
	
	
	public Game(Board board, ProgramCard[] cards, int players) {
		this.board = board;
		this.cardPack = new ProgramCardDeck();
		this.round = 1;
		rng = new Random();
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
	
	public void round() {
		for (int x = 0; x < 5; x++) {
			phase(x);
			activateBoard();
		}
	}
	/**
	 * Starts one phase. The robots are first sorted with respect to the priority of the card that
	 * is going to be used this round. For every robot the robotDoTurn - method is called.
	 * @param nr the number of the phase in this round
	 */
	public void phase(int nr) {
		int[] prio = findPriority(nr);
		for(int x = 0; x < robots.length; x++) {
			int robot = prio[x];
			robotDoTurn(robots[robot],nr);
		}
	}
	/**
	 * Method that does the activities on the board e.g. laser
	 */
	public void activateBoard() {
		for(int x = 0; x < robots.length; x++) {
			Robot rob = robots[x];
			if(!rob.isAlive()) continue;
			Position pos = rob.getPosition();
			IItem s = (IItem) board.getElement(pos);
			if(s.equals(null)); continue;
			if(s instanceof /*rullebånd*/) {
				/* Finn retning til rullebåndet og beveg roboten i den retningen*/
			} else if (s instanceof /*laser*/) {
				/* Finn antall lasere og la roboten ta skade*/
			} else if (s instanceof Flag) {
				rob.visitFlag();
			} else if (s instanceof /*skrutrekker*/) {
			}
		}
	}
	/**
	 * Checks if any robots have visited all flags
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
		if(!rob.isAlive()) {
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
				if(!rob.isAlive()) {
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
				if(!rob.isAlive()) {
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
		Position pos = rob.getPosition();
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
		IItem it = (IItem) board.getElement(pos);
		if(it instanceof Pit) {
			rob.die();
			return true;
		}
		if(it instanceof Wall) {
			return false;
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
	
	// Må gjøres bedre
	public void initializePlayers(int numb) {
		for(int x = 0; x < numb; x++) {
			Player per = new Player(Direction.NORTH, 2, 2, 10, "Robot");
			robots[x] = per;
		}
	}

}
