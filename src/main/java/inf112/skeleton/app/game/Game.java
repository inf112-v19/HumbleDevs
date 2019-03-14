package inf112.skeleton.app.game;

import inf112.skeleton.app.GameObjects.Items.*;
import inf112.skeleton.app.GameObjects.Items.ConveyorBelt;

import java.util.ArrayList;

import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.GameObjects.Robot;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.card.Action;
import inf112.skeleton.app.card.ProgramCard;
import inf112.skeleton.app.card.ProgramCardDeck;
import org.lwjgl.Sys;

/**
 * The game class that controls most of the game. The game class is the class that
 * keeps track of everything that happens.
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
			repairDamage();
		}
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
					if(rotation.equals(Action.LEFTTURN)) {
						rob.rotateLeft();
						rob.move(((ConveyorBelt) s).getDirection().left());
					} else if(rotation.equals(Action.RIGHTTURN)) {
						rob.move(((ConveyorBelt) s).getDirection().right());
						rob.rotateRight();
					}
					// Update robot position on board
				} else if (item instanceof Gear) {
					Action rotation = ((Gear) s).getAction();
					if(rotation == Action.LEFTTURN) {
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
		for (int x = 0; x < robots.length; x++) {
			Robot rob = robots[x];
			ArrayList<IItem> items = board.getItems(robots[x].getPosition());
			for (int y = 0; y < items.size(); y++) {
				IItem item = items.get(y);
				if (item instanceof LaserShoot) {
					// Sjekk i retningen prosjektilet kom fra, hvis det står en robot i veien vil man ikke ta skade
					Direction projectileDir = ((LaserShoot) item).getDirection();
					Position followProjectile = new Position(rob.getX(), rob.getY());
					loop:
					while (true) {
						Robot obstructing = board.getRobot(followProjectile);
						if (obstructing != null) {
							if (!obstructing.equals(rob)){
								break;
							}
						}
						// Trenger ikke variabelen damage i laserShoot()
						ArrayList<IItem> items2 = board.getItems(followProjectile);
						for (int p = 0; p < items2.size(); p++) {
							IItem item2 = items2.get(p);
							if (item2 instanceof Laser) {
								int damage = ((Laser) item2).getDamageMultiplier();
								rob.takeDamage(damage);
								if (rob.isDestroyed()) {
									return;
								}
								break loop;
							}
						}
						followProjectile.move(projectileDir.getOppositeDirection());
						if(followProjectile.getX() > board.getWidth() || followProjectile.getY() > board.getHeight()
						|| followProjectile.getX() < 0 || followProjectile.getY() < 0){
							// ERROR
						}
					}
				}
				if (item instanceof Flag) {
					rob.visitFlag();
				}
			}
		}
	}
	/**
	 * To do
	 */
	public void shootLasers() {
		// To do, sjekk alle rutene i retningen laseren kommer fra, hvis man kommer helt til
		// laserskyteren vil man ta skade. Hvis det står en annen robot i veien vil man ikke
		// ta skade.
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
					board.insertRobot(rob.getPosition(), rob);
				}
			}
		}
	}

	public void repairDamage() {
		for(int x = 0; x < robots.length; x++) {
			Robot rob = robots[x];
			ArrayList<IItem> items = board.getItems(rob.getPosition());
			for(int y = 0; y < items.size(); y++) {
				IItem item = items.get(y);
				if(item instanceof Wrench) {
					rob.makeBackup(rob.getPosition());
					rob.repairDamage();
				} else if(item instanceof Hammer){
					rob.repairDamage();
					rob.makeBackup(rob.getPosition());
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
		if(rob.isDestroyed()) {
			return;
		}
		ProgramCard card = rob.getCards()[nr];
		Action action = card.getAction();
		if(action == Action.LEFTTURN) {
			rob.rotateLeft();
		} else if(action == Action.RIGHTTURN) {
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
		Position startPos = new Position(rob.getX(), rob.getY());
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
			board.removeRobot(rob.getPosition());
			return true;
		}
		ArrayList<IItem> items = board.getItems(pos);
		ArrayList<IItem> currentItems = board.getItems(startPos);
		// Check for obstruction on current tile
		for(int y = 0; y < currentItems.size(); y++){
			IItem obstruction = currentItems.get(y);
			if(obstruction instanceof  Wall){
				Direction wallDir1 = ((Wall) obstruction).getDir();
				Direction wallDir2 = ((Wall) obstruction).getDir2();
				if(wallDir1 == dir || wallDir2 == dir){
					return false;
				}
			}
			if(obstruction instanceof Laser){
				Direction laserDir = ((Laser) obstruction).getDirection();
				if(laserDir.getOppositeDirection() == dir){
					return false;
				}
			}
		}
		for(int x = 0; x < items.size(); x++) {
			IItem it = items.get(x);
			if(it instanceof Pit) {
				rob.die();
				board.removeRobot(rob.getPosition());
				return true;
			}
			if(it instanceof Wall) {
				Direction wallDir1 = ((Wall) it).getDir();
				Direction wallDir2 = ((Wall) it).getDir2();
				if(wallDir1.getOppositeDirection() == dir || wallDir2.getOppositeDirection() == dir){
					return false;
				}
			}
			if(it instanceof  Laser){
				Direction laserDir = ((Laser) it).getDirection();
				if (laserDir.getOppositeDirection() == dir){
					return false;
				}
			}
		}
		if(board.isFree(pos)) {
			rob.move(dir);
			updateBoard(startPos, rob.getPosition());
			return true;
		}
		Robot rob2 = board.getRobot(pos);
		boolean moved = robotMove(rob2,dir);
		if(!moved) {
			return false;
		}
		rob.move(dir);
		updateBoard(startPos, rob.getPosition());
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
			Player per = new Player(Direction.NORTH, 2+x, 2, "Robot" + x);
			robots[x] = per;
			board.insertRobot(new Position(2+x,2), per);
		}
	}
	/**
	 * Method only used to test the sorting based on the card priority.
	 * @return array of robots
	 */
	public Robot[] getRobots() {
		return this.robots;
	}
	/**
	 * Method to update the position of a robot on the board
	 * @param start the position where the robot was placed
	 * @param end the position where to robot is moved to now
	 */
	public void updateBoard(Position start, Position end) {
		Robot rob = board.getRobot(start);
		if(rob == null) {
			return;
		}
		board.removeRobot(start);
		board.insertRobot(end, rob);
	}
}