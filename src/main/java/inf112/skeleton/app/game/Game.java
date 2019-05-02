package inf112.skeleton.app.game;

import inf112.skeleton.app.gameObjects.AI;
import inf112.skeleton.app.gameObjects.Items.*;
import inf112.skeleton.app.gameObjects.Items.ConveyorBelt;

import java.util.ArrayList;

import inf112.skeleton.app.gameObjects.Player;
import inf112.skeleton.app.gameObjects.Robot;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.card.Action;
import inf112.skeleton.app.card.ProgramCard;
import inf112.skeleton.app.card.ProgramCardDeck;
import inf112.skeleton.app.graphics.AssetManager;
import inf112.skeleton.app.graphics.screens.GameScreen;

/**
 * The class that controls most of the game. The game class is the class that
 * keeps track of everything that happens.
 */
public class Game {
    private Board board;
    private Robot[] robots;

    public Game(Board board) {
        this.board = board;
    }

    public Game(Board board, Robot[] robots) {
        this.board = board;
        this.robots = robots;
    }

    public Game () {
        this.board = null;
        this.robots = null;
    }

    public void setBoard (Board board) {
        this.board = board;
    }

    /**
     * Starts the a new round by dealing new cards to every player
     */
    public void startRound() {
        ProgramCardDeck cardPack = new ProgramCardDeck();
        for (Robot robot : robots) {
            // Må først avgjøre om man ønsker å "power down"
            int numbCards = 9 - robot.getDamageTokens();
            ProgramCard[] newCards = cardPack.getRandomCards(numbCards);
            robot.chooseCards(newCards);
        }
    }


    /**
     * Starts the a new round by dealing new cards to every player
     */

    /**
     * Calls the methods to that makes up a round. Not every method this method uses is
     * implemented, but it gives a nice illustration on how the game should work.
     */
    public void round() {
        for (int x = 0; x < 5; x++) {
            phase(x);
            activateMovement();
            activatePassiveItems();
            robotShootLasers();
            repairAndCheckFlags();
        }
        respawnRobots();
        return;
    }
    /**
     * Starts one phase. The robots are first sorted with respect to the priority of the card that
     * is going to be used this round. For every robot the robotDoTurn - method is called.
     *
     * @param nr the number of the phase in this round
     */
    public void phase(int nr) {
        int[] prio = findPriority(nr);
        for (int x = prio.length-1; x >= 0; x--) {
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
            if (rob.isDestroyed()) {
                continue;
            }
            Position pos = rob.getPosition();
            ArrayList<IItem> items = board.getItems(pos);
            for (int y = 0; y < items.size(); y++) {
                IItem item = items.get(y);
                if (item instanceof ConveyorBelt) {
                    IItem temp = item;
                    for (int turn = 0; turn < 2; turn++) {
                        Position startPos = new Position(rob.getX(), rob.getY());
                        Position nextPos = new Position(rob.getX(), rob.getY());
                        nextPos.move(((ConveyorBelt) temp).getDirection());
                        // Kan man ikke bevege seg så bryt ut
                        if(!robotMove(rob,((ConveyorBelt) temp).getDirection(),true)){
                            break;
                        }
                        if(nextPos.getY() > board.getHeight() - 1 || nextPos.getY() < 0 ||
                                nextPos.getX() > board.getWidth() - 1 || nextPos.getX() < 0){
                            rob.die();
                            updateBoard(rob.getPosition(),null);
                            break;
                        }
                        // Må sjekke hva som er i neste posisjon, er det en robot må den fjernes fra brettet (midlertidig)
                        if(!board.isFree(nextPos)){
                            board.removeRobot(nextPos);
                        }
                        robotMove(rob,((ConveyorBelt) temp).getDirection(),false);
                        ArrayList<IItem> items2 = board.getItems(nextPos);
                        Action rotate = null;
                        boolean cb = false;
                        for (IItem is : items2) {
                            if (is instanceof Pit) {
                                rob.die();
                                updateBoard(rob.getPosition(),null);
                            }
                            if (is instanceof ConveyorBelt) {
                                rotate = ((ConveyorBelt) is).getRotation();
                                temp = is;
                                cb = true;
                            }
                        }
                        if(!cb){
                            turn++;
                        }
                        if (rotate != null) {
                            if (!rob.getDirection().equals(((ConveyorBelt) temp).getDirection())) {
                                if (rotate.equals(Action.LEFTTURN)) {
                                    rob.rotateLeft();
                                    GameScreen.updateBoard(rob);
                                }
                                if (rotate.equals(Action.RIGHTTURN)) {
                                    rob.rotateRight();
                                    GameScreen.updateBoard(rob);
                                }
                            }
                        }
                        updateBoard(startPos, rob.getPosition());
                        if (((ConveyorBelt) item).getSpeed() == 1) {
                            break;
                        }
                    }
                }
                if (item instanceof Gear) {
                    Action rotation = ((Gear) item).getAction();
                    if (rotation == Action.LEFTTURN) {
                        rob.rotateLeft();
                        GameScreen.updateBoard(rob);
                    } else {
                        rob.rotateRight();
                        GameScreen.updateBoard(rob);
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
                if (item instanceof Laser) {
                    Laser laser = (Laser) item;
                    // Need to check both directions and find the LaserStart object which decides
                    // the firing direction of this robot. If we find an obstruction (wall or another robot), we can earlyreturn.
                    Direction dir1 = laser.getDirection();
                    Direction dir2 = dir1.getOppositeDirection();

                    Object obstruction1 = trackLaser(dir1, rob.getPosition()).get(1);
                    Object obstruction2 = trackLaser(dir2, rob.getPosition()).get(1);

                    if (obstruction1 instanceof LaserStart || obstruction2 instanceof LaserStart) {
                        rob.takeDamage(laser.getDamageMultiplier());
                        if (rob.isDestroyed()) {
                            updateBoard(rob.getPosition(),null);
                        }
                    }
                }
            }
        }
    }
    /**
     * Method that returns the first info about the first obstacle a robot hits in a given direction from a position.
     *
     * @param shootingDir the direction of the line
     * @param pos         the start position
     * @return [Position, Object] a ArrayList with a Position and an Object (either a Wall, Robot, LaserStart or null,
     * in the case there is no obstacle)
     */
    public ArrayList<Object> trackLaser(Direction shootingDir, Position pos) {
        Integer steps = 1;
        ArrayList<Object> trackingInfo = new ArrayList<>();
        Position shotPos = new Position(pos.getX(), pos.getY());
        ArrayList<IItem> currentItems = board.getItems(shotPos);
        // Check current tile for wall in shooting direction
        for (IItem item : currentItems) {
            if (item instanceof Wall) {
                Direction dir1 = ((Wall) item).getDir();
                Direction dir2 = ((Wall) item).getDir2();
                if (dir1 == shootingDir || dir2 == shootingDir) {
                    trackingInfo.add(shotPos);
                    trackingInfo.add(item);
                    trackingInfo.add(steps);
                    return trackingInfo;
                }
            }
            if (item instanceof LaserStart){
                trackingInfo.add(shotPos);
                trackingInfo.add(item);
                trackingInfo.add(steps);
                return trackingInfo;
            }
        }
        // Iterate through all tiles in shooting direction
        while (true) {
            // Check if we are out of bounds
            shotPos.move(shootingDir);
            if (shotPos.getY() >= board.getWidth() || shotPos.getY() < 0 || shotPos.getX() >= board.getHeight()
                    || shotPos.getX() < 0) {
                // Move position back to return latest position
                shotPos.move(shootingDir.getOppositeDirection());
                trackingInfo.add(shotPos);
                trackingInfo.add(null);
                trackingInfo.add(steps);
                return trackingInfo;
            }
            ArrayList<IItem> items = board.getItems(shotPos);
            // Check if closest side of tile in shooting direction has wall
            for (IItem item : items) {
                if (item instanceof Wall) {
                    Direction dir1 = ((Wall) item).getDir();
                    Direction dir2 = ((Wall) item).getDir2();

                    if (dir1 == shootingDir.getOppositeDirection()) {
                        trackingInfo.add(shotPos);
                        trackingInfo.add(item);
                        trackingInfo.add(steps);
                        return trackingInfo;
                    }
                    if (dir2 != null && dir2 == shootingDir.getOppositeDirection()) {
                        trackingInfo.add(shotPos);
                        trackingInfo.add(item);
                        trackingInfo.add(steps);
                        return trackingInfo;
                    }
                }
            }
            // Check if tile contains robot
            Robot target = board.getRobot(shotPos);
            if (target != null) {
                trackingInfo.add(shotPos);
                trackingInfo.add(target);
                trackingInfo.add(steps);
                return trackingInfo;
            }

            for (IItem item : items) {
                // Check LaserStart
                if (item instanceof LaserStart) {
                    Direction turretDir = ((LaserStart) item).getDirection();
                    if (turretDir == shootingDir.getOppositeDirection()) {
                        trackingInfo.add(shotPos);
                        trackingInfo.add(item);
                        trackingInfo.add(steps);
                        return trackingInfo;
                    }
                }
                // Check far side of tile in shooting direction has wall
                if (item instanceof Wall) {
                    Direction dir1 = ((Wall) item).getDir();
                    Direction dir2 = ((Wall) item).getDir2();

                    if (dir1 == shootingDir) {
                        trackingInfo.add(shotPos);
                        trackingInfo.add(item);
                        trackingInfo.add(steps);
                        return trackingInfo;
                    }
                    if (dir2 != null && dir2 == shootingDir) {
                        trackingInfo.add(shotPos);
                        trackingInfo.add(item);
                        trackingInfo.add(steps);
                        return trackingInfo;
                    }
                }
            }
            steps++;
        }
    }
    /**
     * Every robot shoots their laser
     */
    public void robotShootLasers() {
        for (Robot rob : robots) {
            if (rob.isPoweredDown() || rob.isDestroyed()) {
                continue;
            }
            Direction shootingDir = rob.getDirection();
            ArrayList<Object> tracking = trackLaser(shootingDir, rob.getPosition());
            Position finalPos = (Position) tracking.get(0);
            Object obstruction = tracking.get(1);
            Integer steps = (Integer) tracking.get(2);

            GameScreen.shootRobotLaser(rob.getX(), rob.getY(), finalPos.getX(), finalPos.getY(), shootingDir, steps);

            if (obstruction instanceof Robot) {
                // shot into robot
                Robot targetRobot = (Robot) obstruction;
                targetRobot.takeDamage();
                if (targetRobot.isDestroyed()) {
                    updateBoard((targetRobot).getPosition(),null);
                }
            }

        }
    }

    /**
     * Respawn the destroyed robots if the player has more lifetokens
     */
    public void respawnRobots() {
        for (Robot rob : robots) {
            if (rob.isDestroyed()) {
                if (!rob.gameOver()) {
                    rob.respawn();
                    if (!board.isFree(rob.getPosition())) {
                        Position pos = board.getRandomPositionNextToPosition(rob.getPosition());
                        rob.makeBackup(new Position(pos.getX(), pos.getY()));
                        rob.respawn();
                    }
                    board.insertRobot(rob.getPosition(),rob);

                  GameScreen.updateBoard(rob);
                }
            }
        }
    }

    /**
     * Checks if any of the robots are placed on fields that repairs them
     */
    public void repairAndCheckFlags() {
        for (Robot rob : robots) {
            if (rob.isDestroyed()) {
                continue;
            }
            ArrayList<IItem> items = board.getItems(rob.getPosition());
            for (IItem item : items) {
                if (item instanceof RepairTool) {
                    rob.makeBackup(new Position(rob.getPosition().getX(), rob.getPosition().getY()));
                    rob.repairDamage();
                    if (((RepairTool) item).wrenchAndHammer()) {
                        // Draw option card
                    }
                } else if (item instanceof Flag) {
                    rob.visitFlag((Flag) item);
                }
            }
        }
    }

    /**
     * Checks if any robots have visited all flags.
     * @return the robot that have visited all flags, returns null if nobody has done it yet.
     */
    public Robot finished() {
        int robotsAlive = 0;
        // Win condition #1: Visit all four flags
        for (Robot robot : robots) {
            if(!robot.gameOver()){
                robotsAlive++;
            }
            if (robot.visitedFlags() == 4) {
                return robot;
            }
        }
        // Win condition #2: Be the only robot alive
        if(robotsAlive == 1){
            for (Robot robot : robots){
                if(!robot.gameOver()){
                    return robot;
                }
            }
        }
        return null;
    }

    /**
     * This method makes the robot do one turn. It checks the card and does what it says.
     * If it's a move card, the robotMove - method is called. If it's a rotate card, the robot will
     * rotate.
     *
     * @param rob The robot that are going to do its turn
     * @param nr  The phase number
     */
    public void robotDoTurn(Robot rob, int nr) {
        if (rob.isDestroyed()) {
            return;
        }
        ProgramCard card = rob.getCards()[nr];
        Action action = card.getAction();
        if (action == Action.LEFTTURN) {
            rob.rotateLeft();
            GameScreen.updateBoard(rob);
        } else if (action == Action.RIGHTTURN) {
            rob.rotateRight();
            GameScreen.updateBoard(rob);
        } else if (action == Action.UTURN) {
            rob.rotateRight();
            rob.rotateRight();
            GameScreen.updateBoard(rob);
        } else if (action == Action.MOVEFORWARD) {
            int move = card.getMove();
            while (move > 0) {
                if (rob.isDestroyed()) {
                    break;
                }
                if (!robotMove(rob, rob.getDirection(),false)) {
                    break;
                }
                move--;
            }
        } else {
            int move = card.getMove();
            while (move > 0) {
                if (rob.isDestroyed()) {
                    break;
                }
                if (!robotMove(rob, rob.getDirection().getOppositeDirection(),false)) {
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
     *
     * @param rob the robot that are going to be moved
     * @param dir the direction of the movement
     * @return True if the robot is moved, false otherwise
     */
    public boolean robotMove(Robot rob, Direction dir, boolean cb) {
        Position startPos = new Position(rob.getX(), rob.getY());
        Position pos = new Position(rob.getX(), rob.getY());
        switch (dir) {
            case NORTH:
                pos.moveNorth();
                break;
            case EAST:
                pos.moveEast();
                break;
            case WEST:
                pos.moveWest();
                break;
            case SOUTH:
                pos.moveSouth();
                break;
        }
        int xPos = pos.getX();
        int yPos = pos.getY();
        if (xPos > board.getWidth() - 1  || xPos < 0 || yPos < 0 || yPos > board.getHeight() - 1) {
            if (cb) {
                return true;
            } else {
                rob.die();
                updateBoard(rob.getPosition(),null);
                return true;
            }
        }
        ArrayList<IItem> items = board.getItems(pos);
        ArrayList<IItem> currentItems = board.getItems(startPos);
        // Check for obstruction on current tile
        for (IItem obstruction : currentItems) {
            if (obstruction instanceof Wall) {
                Direction wallDir1 = ((Wall) obstruction).getDir();
                Direction wallDir2 = ((Wall) obstruction).getDir2();
                if (wallDir1 == dir || wallDir2 == dir) {
                    return false;
                }
            }
        }
        // Check for obstructions on the next tile
        for (IItem it : items) {
            if (it instanceof Pit) {
                if (cb) {
                    return true;
                } else {
                    rob.die();
                    updateBoard(rob.getPosition(), null);
                    return true;
                }
            }
            if (it instanceof Wall) {
                Direction wallDir1 = ((Wall) it).getDir();
                Direction wallDir2 = ((Wall) it).getDir2();
                if (wallDir1.getOppositeDirection() == dir ) {
                    return false;
                }
                if(wallDir2 != null && wallDir2.getOppositeDirection() == dir){
                    return false;
                }
            }
        }
        if (board.isFree(pos)) {
            if (cb) {
                return true;
            } else {
                rob.move(dir);
                updateBoard(startPos, rob.getPosition());
                return true;
            }
        }
        if(containsConveyorBelt(pos) == null && cb){
            return false;
        }
        Robot rob2 = board.getRobot(pos);
        if(cb){
            boolean moved = robotMove(rob2, dir,true);
            if(!moved){
                return false;
            }
        } else {
            boolean moved = robotMove(rob2,dir,false);
            if (!moved) {
                return false;
            }
        }
        if (cb) {
            return true;
        }

        rob.move(dir);
        updateBoard(startPos, rob.getPosition());
        return true;
    }

    /**
     * Checks if a position contains a conveyor belt. It should return the conveyor belt if it exist.
     * @param pos position to be checked
     * @return the conveyor belt if it exists, otherwise null
     */
    private ConveyorBelt containsConveyorBelt(Position pos){
        ArrayList<IItem> items = board.getItems(pos);
        for(IItem item : items){
            if (item instanceof ConveyorBelt){
                return (ConveyorBelt) item;
            }
        }
        return null;
    }

	/**
	 * The method that orders the robots with respect to the priority of the card that each robot
	 * is going to play this turn
	 * @param cardnr the number of the phase
	 * @return an array with the right order
	 */
	public int[] findPriority(int cardnr) {
		double[][] pri = new double[robots.length][2];
		int count = 0;
		for(int x = 0; x < robots.length; x++) {
			ProgramCard card = robots[x].getCards()[cardnr];
			if(card == null){
				pri[x][1] = x;
				pri[x][0] = -1;
				count++;
				continue;
			}
			pri[x][1] = x;
			pri[x][0] = robots[x].getCards()[cardnr].getPriority();
		}
		java.util.Arrays.sort(pri, new java.util.Comparator<double[]>() {
			public int compare(double[] a, double[] b) {
				return Double.compare(a[0], b[0]);
			}
		});
		int[] prio = new int[robots.length - count];
		int index = 0;
		for(int x = 0; x < robots.length; x++) {
			int prior = (int) pri[x][0];
			if(prior == -1){
				continue;
			}
			prio[index] = (int) pri[x][1];
			index++;
		}
		return prio;
	}
	/**
	 * Method to updates the position of a robot on the board
	 * @param start the position where the robot was placed
	 * @param end the position where to robot is moved
	 */
	private void updateBoard(Position start, Position end) {
		Robot rob = board.getRobot(start);
        if(rob == null) {
            return;
        }
		if(rob.isDestroyed()){
		    board.removeRobot(start);
        } else {
            board.removeRobot(start);
            board.insertRobot(end, rob);
        }
		GameScreen.updateBoard(rob);
	}

	public void addR(Robot robot) {
	    robots = new Robot[1];
        robots[0] = robot;
    }

    public void initializePlayers(int numb, ArrayList<String> nameOfPlayers) {
	    if(nameOfPlayers.size() == 0 && numb > 0){
	        return;
        }


        Position[] startDocks = board.getDockPositions();
        this.robots = new Robot[numb];
        for(int x = 0; x < nameOfPlayers.size(); x++) {
            Position pos = startDocks[x];


            String filePath = AssetManager.getTextureByIndex(x);

            Robot player = new Player(x, Direction.NORTH, pos.getX(),pos.getY(), nameOfPlayers.get(x), filePath);
            robots[x] = player;
            board.insertRobot(pos,player);
        }
        for(int y = 0; y < numb - nameOfPlayers.size(); y++){
            int index = y + nameOfPlayers.size();
            Position pos = startDocks[index];
            String filePath = AssetManager.getTextureByIndex(index);
            Robot ai = new AI(index, Direction.NORTH, pos.getX(),pos.getY(), "Destroyer" + (y+1), filePath);
            robots[index] = ai;
            board.insertRobot(pos,ai);
        }
    }
    /**
     * Method used to get the robots
     * @return array of robots
     */
    public Robot[] getRobots() {
        return this.robots;
    }
}