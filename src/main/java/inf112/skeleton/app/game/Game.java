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

/**
 * The game class that controls most of the game. The game class is the class that
 * keeps track of everything that happens.
 * @author Even Kolsgaard
 *
 */
public class Game {
	private Board board;
	private Robot[] robots;
	private ProgramCardDeck cardPack;
	private int round;


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
		for (Robot robot : robots) {
			ProgramCard[] newCards = cardPack.getRandomCards();
			robot.chooseCards(newCards);
		}
	}

	/**
	 * Calls the methods to that makes up a round. Not every method this method uses is
	 * implemented, but it gives a nice illustration on how the game should work.
	 */
	public void round() {
		startRound();
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
	 *
	 * @param nr the number of the phase in this round
	 */
	private void phase(int nr) {
		int[] prio = findPriority(nr);
		for (int x = robots.length - 1; x >= 0; x--) {
			int robot = prio[x];
			robotDoTurn(robots[robot], nr);
		}
	}

	/**
	 * Method that does the movement actions on the board e.g. gear
	 */
	public void activateMovement() {
		for (int x = 0; x < robots.length; x++) {
			Robot rob = robots[x];
			if (rob.isDestroyed()){
				continue;
			}
			Position pos = rob.getPosition();
			ArrayList<IItem> items = board.getItems(pos);
			for (int y = 0; y < items.size(); y++) {
				IItem item = items.get(0);
				if (item instanceof ConveyorBelt) {
					IItem temp = item;
					outerloop:
					for(int turn = 0; turn < 2; turn++){
						// 1. Sjekk hva som er i neste posisjon
						Position startPos = new Position(rob.getX(),rob.getY());
						Position nextPos = new Position(rob.getX(),rob.getY());
						nextPos.move(((ConveyorBelt) temp).getDirection());
						ArrayList<IItem> items2 = board.getItems(nextPos);
						Action rotate = null;
						IItem var = temp;
						for(IItem is : items2){
							if(is instanceof Pit){
								rob.die();
								board.removeRobot(rob.getPosition());
							} else if (is instanceof Wall){
								break outerloop;
							} else if (is instanceof Laser){
								break outerloop;
							} else if (is instanceof ConveyorBelt){
								rotate = ((ConveyorBelt) is).getRotation();
								temp = is;
							}
						}
						// 2. Hvis det enten er mer rullebånd eller et felt uten robot -> Beveg roboten
						if(board.isFree(nextPos)){
							robotMove(rob,((ConveyorBelt) var).getDirection());
						}
						// 3. Hvis neste rute er et rullebånd, sjekk rotasjonen -> roter
						if(rotate != null) {
							if (rotate.equals(Action.LEFTTURN)) {
								rob.rotateLeft();
							}
							if (rotate.equals(Action.RIGHTTURN)) {
								rob.rotateRight();
							}
						}
						// 4. Er det et dobbelt rullebånd -> Gjenta prosessen
						updateBoard(startPos,rob.getPosition());
						if(((ConveyorBelt) item).getSpeed() == 1){
							break;
						}
					}
				}
				if (item instanceof Gear) {
					Action rotation = ((Gear) item).getAction();
					if (rotation == Action.LEFTTURN) {
						rob.rotateLeft();
					} else {
						rob.rotateRight();
					}
				}
			}
		}
	}

	/**
	 * Method that activates the activities on the board that doesn't change the robots placement
	 * or/and rotation e.g. laser
	 */
	public void activatePassiveItems() {
		for (Robot rob : robots) {
			ArrayList<IItem> items = board.getItems(rob.getPosition());
			for (IItem item : items) {
				if (item instanceof LaserShoot) {
					Direction dir = ((LaserShoot) item).getDirection().getOppositeDirection();
					Object obstruction = trackLaser(dir,rob.getPosition());
					if (obstruction instanceof Laser){
						rob.takeDamage(((LaserShoot)item).getHarm());
						if (rob.isDestroyed()){
							board.removeRobot(rob.getPosition());
						}
					}
				}
			}
		}
	}

	/**
	 * Method that returns the first obstacle you hit in a given direction from a position
	 * @param shootingDir the direction of the line
	 * @param pos the start position
	 * @return an object of whatever that is in the way, return null if there is no obstacles
	 */
	public Object trackLaser(Direction shootingDir, Position pos) {
		Position shotPos = new Position(pos.getX(), pos.getY());
		// Må sjekke om det er vegg på nåværende rute
		ArrayList<IItem> currentItems = board.getItems(shotPos);
		for (IItem currIt : currentItems) {
			if (currIt instanceof Wall) {
				Direction dir1 = ((Wall) currIt).getDir();
				Direction dir2 = ((Wall) currIt).getDir2();
				if (dir1 == shootingDir || dir2 == shootingDir) {
					return currIt;
				}
			}
		}
		loop:
		while (true) {
			shotPos.move(shootingDir);
			if (shotPos.getY() >= board.getWidth() || shotPos.getY() < 0 || shotPos.getX() >= board.getHeight()
					|| shotPos.getX() < 0) {
				return null;
			}
			ArrayList<IItem> items = board.getItems(shotPos);
			for (IItem item : items) {
				if (item instanceof Wall) {
					Direction dir1 = ((Wall) item).getDir();
					Direction dir2 = ((Wall) item).getDir2();
					if (dir1 == shootingDir.getOppositeDirection() || dir2 == shootingDir.getOppositeDirection()) {
						return item;
					}
				}
				if (item instanceof Laser) {
					Direction turretDir = ((Laser) item).getDirection();
					if (turretDir == shootingDir) {
						return item;
					}
				}
			}
			Robot target = board.getRobot(shotPos);
			if (target != null) {
				return target;
			}
			for (IItem item : items) {
				if (item instanceof Wall) {
					Direction dir1 = ((Wall) item).getDir();
					Direction dir2 = ((Wall) item).getDir2();
					if (dir1 == shootingDir || dir2 == shootingDir) {
						return item;
					}
				}
				if (item instanceof Laser) {
					Direction turretDir = ((Laser) item).getDirection();
					if (turretDir == shootingDir.getOppositeDirection()) {
						return item;
					}
				}
			}
		}
	}
	/**
	 * Every robot shoots their laser
	 */
	public void shootLasers() {
		for(Robot rob : robots){
			Direction shootingDir = rob.getDirection();
			Object obstruction = trackLaser(shootingDir,rob.getPosition());
			if (obstruction instanceof Robot){
				((Robot) obstruction).takeDamage();
				if (((Robot) obstruction).isDestroyed()){
					board.removeRobot(rob.getPosition());
				}
			}
		}
	}
	/**
	 * Respawn the destroyed robots if the player has more lifetokens
	 */
	public void respawnRobots() {
		for(Robot rob : robots){
			if(rob.isDestroyed()) {
				if(!rob.gameOver()) {
					rob.respawn();
					board.insertRobot(rob.getPosition(), rob);
				}
			}
		}
	}
	/**
	 * Checks if any of the robots are placed on fields that repair them
	 */
	public void repairDamage() {
		for(Robot rob : robots){
			ArrayList<IItem> items = board.getItems(rob.getPosition());
			for(IItem item : items){
				if(item instanceof Wrench) {
					rob.makeBackup(rob.getPosition());
					rob.repairDamage();
				} else if(item instanceof Hammer){
					rob.repairDamage();
					rob.makeBackup(rob.getPosition());
					// Draw option card
				} else if(item instanceof Flag){
					rob.visitFlag();
				}
			}
		}
	}
	/**
	 * Checks if any robots have visited all flags.
	 * @return the robot that have visited all flags, returns null if nobody has done it yet.
	 */
	private Robot finished() {
		for(Robot robot : robots){
			if(robot.visitedFlags() == 3) {
				return robot;
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
	private void robotDoTurn(Robot rob,int nr) {
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
		for(IItem obstruction : currentItems){
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
		// Check for obstructions on the next tile
		for(IItem it : items){
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
/*
 - Hvorfor boolean i laser?
 - Metoden som velger tilfeldige kort fra en kortstokk skal ikke legge tilbake kort, slik som den gjør nå
 - Hva hvis det står en robot foran en annen på samlebåndet?
 	- Andre spesialtilfeller, to møtene roboter i et kryss, robot som skal av -> robotene dyttes ikke
		-> Lage en boolsk variabel som holder kontroll på om bevegelsen er fra kort eller rullebånd
	- Rotasjonen i svinger skal etter at roboten har blitt flyttet i svingen, men hvis en robot går inn i svingen, vil
	  roboten ikke roteres
 - Hvilken retning har rullebåndet i en sving? Retningen den starter eller retningen den skal til?
 	- Hva skjer i et kryss
 - Power down?
 */