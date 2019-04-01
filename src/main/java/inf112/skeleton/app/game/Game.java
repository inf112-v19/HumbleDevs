//package inf112.skeleton.app.game;
//
//import inf112.skeleton.app.gameObjects.Items.*;
//import inf112.skeleton.app.gameObjects.Items.ConveyorBelt;
//
//import java.util.ArrayList;
//
//import inf112.skeleton.app.gameObjects.Player;
//import inf112.skeleton.app.gameObjects.Robot;
//import inf112.skeleton.app.board.Board;
//import inf112.skeleton.app.board.Direction;
//import inf112.skeleton.app.board.Position;
//import inf112.skeleton.app.card.Action;
//import inf112.skeleton.app.card.ProgramCard;
//import inf112.skeleton.app.card.ProgramCardDeck;
//
///**
// * The class that controls most of the game. The game class is the class that
// * keeps track of everything that happens.
// */
//public class Game {
//	private Board board;
//	private Robot[] robots;
//
//	public Game(Board board, int players) {
//		this.board = board;
//		robots = new Robot[players];
//	}
//	public void setUp(){
//		initializePlayers(robots.length);
//	}
//
//	/**
//	 * Starts the a new round by dealing new cards to every player
//	 */
//	public void startRound() {
//		ProgramCardDeck cardPack = new ProgramCardDeck();
//		for (Robot robot : robots) {
//			// Må først avgjøre om man ønsker å "power down"
//			int numbCards = 9 - robot.getDamageTokens();
//			ProgramCard[] newCards = cardPack.getRandomCards(numbCards);
//			robot.chooseCards(newCards);
//		}
//	}
//	/**
//	 * Calls the methods to that makes up a round. Not every method this method uses is
//	 * implemented, but it gives a nice illustration on how the game should work.
//	 */
//	public void round() {
//		startRound();
//		for (int x = 0; x < 5; x++) {
//			phase(x);
//			activateMovement();
//			activatePassiveItems();
//			robotShootLasers();
//			repairAndCheckFlags();
//		}
//		respawnRobots();
//	}
//	/**
//	 * Starts one phase. The robots are first sorted with respect to the priority of the card that
//	 * is going to be used this round. For every robot the robotDoTurn - method is called.
//	 * @param nr the number of the phase in this round
//	 */
//	public void phase(int nr) {
//		int[] prio = findPriority(nr);
//		for (int x = robots.length - 1; x >= 0; x--) {
//			int robot = prio[x];
//			robotDoTurn(robots[robot], nr);
//		}
//	}
//	/**
//	 * Method that does the movement actions on the board e.g. gear
//	 */
//	public void activateMovement() {
//		for (int x = 0; x < robots.length; x++) {
//			Robot rob = robots[x];
//			if (rob.isDestroyed()){
//				continue;
//			}
//			Position pos = rob.getPosition();
//			ArrayList<IItem> items = board.getItems(pos);
//			for (int y = 0; y < items.size(); y++) {
//				IItem item = items.get(0);
//				if (item instanceof ConveyorBelt) {
//					IItem temp = item;
//					outerloop:
//					for(int turn = 0; turn < 2; turn++){
//						Position startPos = new Position(rob.getX(),rob.getY());
//						Position nextPos = new Position(rob.getX(),rob.getY());
//						nextPos.move(((ConveyorBelt) temp).getDirection());
//						ArrayList<IItem> items2 = 	board.getItems(nextPos);
//						Action rotate = null;
//						IItem var = temp;
//						for(IItem is : items2){
//							if(is instanceof Pit){
//								rob.die();
//								board.removeRobot(rob.getPosition());
//							} else if (is instanceof Wall) {
//								break outerloop;
//							} else if (is instanceof ConveyorBelt){
//								rotate = ((ConveyorBelt) is).getRotation();
//								temp = is;
//							}
//						}
//						if(board.isFree(nextPos)){
//							robotMove(rob,((ConveyorBelt) var).getDirection());
//						}
//						if(rotate != null) {
//							if(!rob.getDirection().equals(((ConveyorBelt) temp).getDirection())){
//								if (rotate.equals(Action.LEFTTURN)) {
//									rob.rotateLeft();
//								}
//								if (rotate.equals(Action.RIGHTTURN)) {
//									rob.rotateRight();
//								}
//							}
//						}
//						updateBoard(startPos,rob.getPosition());
//						if(((ConveyorBelt) item).getSpeed() == 1){
//							break;
//						}
//					}
//				}
//				if (item instanceof Gear) {
//					Action rotation = ((Gear) item).getAction();
//					if (rotation == Action.LEFTTURN) {
//						rob.rotateLeft();
//					} else {
//						rob.rotateRight();
//					}
//				}
//			}
//		}
//	}
//	/**
//	 * Method that activates the activities on the board that doesn't change the robots placement
//	 * or/and rotation e.g. laser
//	 */
//	public void activatePassiveItems() {
//		for (Robot rob : robots) {
//			ArrayList<IItem> items = board.getItems(rob.getPosition());
//			for (IItem item : items) {
//				if (item instanceof LaserShoot) {
//					Direction dir = ((LaserShoot) item).getDirection().getOppositeDirection();
//					Object obstruction = trackLaser(dir,rob.getPosition());
//					if (obstruction instanceof Laser){
//						rob.takeDamage(((LaserShoot)item).getRays());
//						if (rob.isDestroyed()){
//							board.removeRobot(rob.getPosition());
//						}
//					}
//				}
//			}
//		}
//	}
//	/**
//	 * Method that returns the first obstacle you hit in a given direction from a position
//	 * @param shootingDir the direction of the line
//	 * @param pos the start position
//	 * @return an object of whatever that is in the way, return null if there is no obstacles
//	 */
//	public Object trackLaser(Direction shootingDir, Position pos) {
//		Position shotPos = new Position(pos.getX(), pos.getY());
//		ArrayList<IItem> currentItems = board.getItems(shotPos);
//		for (IItem currIt : currentItems) {
//			if (currIt instanceof Wall) {
//				Direction dir1 = ((Wall) currIt).getDir();
//				Direction dir2 = ((Wall) currIt).getDir2();
//				if (dir1 == shootingDir || dir2 == shootingDir) {
//					return currIt;
//				}
//			}
//		}
//		while (true) {
//			shotPos.move(shootingDir);
//			if (shotPos.getY() >= board.getWidth() || shotPos.getY() < 0 || shotPos.getX() >= board.getHeight()
//					|| shotPos.getX() < 0) {
//				return null;
//			}
//			ArrayList<IItem> items = board.getItems(shotPos);
//			for (IItem item : items) {
//				if (item instanceof Wall) {
//					Direction dir1 = ((Wall) item).getDir();
//					Direction dir2 = ((Wall) item).getDir2();
//					if (dir1 == shootingDir.getOppositeDirection() || dir2 == shootingDir.getOppositeDirection()) {
//						return item;
//					}
//				}
//				// Vil være unødvendig hvis lasere ikke kan plasseres på baksiden vegger
//				if (item instanceof Laser) {
//					Direction turretDir = ((Laser) item).getDirection();
//					if (turretDir == shootingDir) {
//						return item;
//					}
//				}
//			}
//			Robot target = board.getRobot(shotPos);
//			if (target != null) {
//				return target;
//			}
//			for (IItem item : items) {
//				if (item instanceof Wall) {
//					Direction dir1 = ((Wall) item).getDir();
//					Direction dir2 = ((Wall) item).getDir2();
//					if (dir1 == shootingDir || dir2 == shootingDir) {
//						return item;
//					}
//				}
//				if (item instanceof Laser) {
//					Direction turretDir = ((Laser) item).getDirection();
//					if (turretDir == shootingDir.getOppositeDirection()) {
//						return item;
//					}
//				}
//			}
//		}
//	}
//	/**
//	 * Every robot shoots their laser
//	 */
//	public void robotShootLasers() {
//		for(Robot rob : robots){
//			if(rob.isPoweredDown() || rob.isDestroyed()){
//				continue;
//			}
//			Direction shootingDir = rob.getDirection();
//			Object obstruction = trackLaser(shootingDir,rob.getPosition());
//			if (obstruction instanceof Robot){
//				((Robot) obstruction).takeDamage();
//				if (((Robot) obstruction).isDestroyed()){
//					board.removeRobot(rob.getPosition());
//				}
//			}
//		}
//	}
//	/**
//	 * Respawn the destroyed robots if the player has more lifetokens
//	 */
//	public void respawnRobots() {
//		for(Robot rob : robots){
//			if(rob.isDestroyed()) {
//				if(!rob.gameOver()) {
//					rob.respawn();
//					if(board.isFree(rob.getPosition())){
//						board.insertRobot(rob.getPosition(), rob);
//					} else {
//						// Må la spilleren velge en posisjon ved siden av backup
//						// Roboten må oppdatere plasseringen sin
//					}
//				}
//			}
//		}
//	}
//	/**
//	 * Checks if any of the robots are placed on fields that repairs them
//	 */
//	public void repairAndCheckFlags() {
//		for(Robot rob : robots){
//			if (rob.isDestroyed()){
//				continue;
//			}
//			ArrayList<IItem> items = board.getItems(rob.getPosition());
//			for(IItem item : items){
//				if(item instanceof RepairTool) {
//					rob.makeBackup(rob.getPosition());
//					rob.repairDamage();
//					if(((RepairTool) item).wrenchAndHammer()){
//						// Draw option card
//					}
//				} else if(item instanceof Flag){
//					rob.visitFlag((Flag)item);
//				}
//			}
//		}
//	}
//	/**
//	 * Checks if any robots have visited all flags.
//	 * @return the robot that have visited all flags, returns null if nobody has done it yet.
//	 */
//	public Robot finished() {
//		for(Robot robot : robots){
//			if(robot.visitedFlags() == 4) {
//				return robot;
//			}
//		}
//		return null;
//	}
//	/**
//	 * This method makes the robot do one turn. It checks the card and does what it says.
//	 * If it's a move card, the robotMove - method is called. If it's a rotate card, the robot will
//	 * rotate.
//	 * @param rob The robot that are going to do its turn
//	 * @param nr The phase number
//	 */
//	private void robotDoTurn(Robot rob, int nr) {
//		if(rob.isDestroyed()) {
//			return;
//		}
//		ProgramCard card = rob.getCards()[nr];
//		Action action = card.getAction();
//		if(action == Action.LEFTTURN) {
//			rob.rotateLeft();
//		} else if(action == Action.RIGHTTURN) {
//			rob.rotateRight();
//		} else if (action == Action.UTURN) {
//			rob.rotateRight();
//			rob.rotateRight();
//		} else if (action == Action.MOVEFORWARD) {
//			int move = card.getMove();
//			while(move > 0) {
//				if(rob.isDestroyed()) {
//					break;
//				}
//				if(!robotMove(rob,rob.getDirection())) {
//					break;
//				}
//				move--;
//			}
//		} else {
//			int move = card.getMove();
//			while(move > 0) {
//				if(!rob.isDestroyed()) {
//					break;
//				}
//				if(!robotMove(rob,rob.getDirection().getOppositeDirection())) {
//					break;
//				}
//				move--;
//			}
//		}
//	}
//	/**
//	 * Method that moves the robot. Checks the new position and depending on that, the robot will
//	 * either move or stand still. If there is a robot in the way, this method is used again on
//	 * that robot.
//	 * @param rob the robot that are going to be moved
//	 * @param dir the direction of the movement
//	 * @return True if the robot is moved, false otherwise
//	 */
//	public boolean robotMove(Robot rob, Direction dir) {
//		Position startPos = new Position(rob.getX(), rob.getY());
//		Position pos = new Position(rob.getX(),rob.getY());
//		switch(dir) {
//			case NORTH: pos.moveNorth();
//				break;
//			case EAST: pos.moveEast();
//				break;
//			case WEST: pos.moveWest();
//				break;
//			case SOUTH: pos.moveSouth();
//				break;
//		}
//		int xPos = pos.getX();
//		int yPos = pos.getY();
//		if(xPos > board.getWidth() || xPos < 0 || yPos < 0 || yPos > board.getHeight()) {
//			rob.die();
//			board.removeRobot(rob.getPosition());
//			return true;
//		}
//		ArrayList<IItem> items = board.getItems(pos);
//		ArrayList<IItem> currentItems = board.getItems(startPos);
//		// Check for obstruction on current tile
//		for(IItem obstruction : currentItems){
//			if(obstruction instanceof  Wall){
//				Direction wallDir1 = ((Wall) obstruction).getDir();
//				Direction wallDir2 = ((Wall) obstruction).getDir2();
//				if(wallDir1 == dir || wallDir2 == dir){
//					return false;
//				}
//			}
//		}
//		// Check for obstructions on the next tile
//		for(IItem it : items){
//			if(it instanceof Pit) {
//				rob.die();
//				board.removeRobot(rob.getPosition());
//				return true;
//			}
//			if(it instanceof Wall) {
//				Direction wallDir1 = ((Wall) it).getDir();
//				Direction wallDir2 = ((Wall) it).getDir2();
//				if (wallDir1.getOppositeDirection() == dir || wallDir2.getOppositeDirection() == dir) {
//					return false;
//				}
//			}
//		}
//		if(board.isFree(pos)) {
//			rob.move(dir);
//			updateBoard(startPos, rob.getPosition());
//			return true;
//		}
//		Robot rob2 = board.getRobot(pos);
//		boolean moved = robotMove(rob2,dir);
//		if(!moved) {
//			return false;
//		}
//		rob.move(dir);
//		updateBoard(startPos, rob.getPosition());
//		return true;
//	}
//	/**
//	 * The method that orders the robots with respect to the priority of the card that each robot
//	 * is going to play this turn
//	 * @param cardnr the number of the phase
//	 * @return an array with the right order
//	 */
//	public int[] findPriority(int cardnr) {
//		double[][] pri = new double[robots.length][2];
//		int count = 0;
//		for(int x = 0; x < robots.length; x++) {
//			ProgramCard card = robots[x].getCards()[cardnr];
//			if(card == null){
//				pri[x][1] = x;
//				pri[x][0] = -1;
//				count++;
//				continue;
//			}
//			pri[x][1] = x;
//			pri[x][0] = robots[x].getCards()[cardnr].getPriority();
//		}
//		java.util.Arrays.sort(pri, new java.util.Comparator<double[]>() {
//			public int compare(double[] a, double[] b) {
//				return Double.compare(a[0], b[0]);
//			}
//		});
//		int[] prio = new int[robots.length - count];
//		int index = 0;
//		for(int x = 0; x < robots.length; x++) {
//			int prior = (int) pri[x][0];
//			if(prior == -1){
//				continue;
//			}
//			prio[index] = (int) pri[x][1];
//			index++;
//		}
//		return prio;
//	}
//
//	private void initializePlayers(int numb) {
//		ArrayList<Position> startDocks = board.getDockPositions();
//		for(int x = 0; x < numb; x++) {
//			Position pos = startDocks.get(x);
//			String filePath = "texture/robot" + x+1 + ".png";
//			Player player = new Player(Direction.NORTH, pos.getX(),pos.getY(), "jd", filePath);
//			board.insertRobot(pos,player);
//		}
//	}
//	/**
//	 * Method used to get the robots
//	 * @return array of robots
//	 */
//	public Robot[] getRobots() {
//		return this.robots;
//	}
//	/**
//	 * Method to updates the position of a robot on the board
//	 * @param start the position where the robot was placed
//	 * @param end the position where to robot is moved
//	 */
//	private void updateBoard(Position start, Position end) {
//		Robot rob = board.getRobot(start);
//		if(rob == null) {
//			return;
//		}
//		board.removeRobot(start);
//		board.insertRobot(end, rob);
//	}
//}