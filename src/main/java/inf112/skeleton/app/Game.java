package inf112.skeleton.app;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Position;

/**
 * The game class that controls most of the game. The game class is the class that always
 * keeps track of everything that happens.
 * @author Even Kolsgaard
 *
 * @param <T>
 */
public class Game<T> {
	private static Random rng;
	private static Board board;
	private static Robot[] robots;
	private static Card[] cardPack;
	private static int round;
	
	
	public Game(Board board, Card[] cards, int players) {
		this.board = board;
		this.cardPack = cards;
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
			Card[] newCards = shuffleCards();
			robots[x].chooseCards(newCards);
		}
	}
	/**
	 * Starts one phase. The robots are first sorted with respect to the priority of the card that
	 * is going to be used this round. For every robot the robotDoTurn - method is called.
	 * @param nr the number of the phase in this round
	 */
	public void phase(int nr) {
		ArrayList prio = (ArrayList) findPriority(nr);
		for(int x = 0; x < robots.length; x++) {
			int robot = (int) prio.get(x);
			robotDoTurn(robots[robot],nr);
		}
		// gjør alle aktivitetene på brettet
		// Sjekk om noen roboter står på noe, hvis dette er tilfellet må aktiviteten utføres
		// Robotene skyter, når???
		// Sjekk om noen roboter står noe som gir en ny backup
		// Registrere flagg
		// Sjekk om noen har vært på alle flagg, hvis ja --> den har vunnet
	}
	
	/**
	 * This method makes the robot do one turn. It checks the card and does what it says.
	 * If it's a move card, then it's created a temporary position which is the robots new
	 * position if it moves. This position is checked for items and other robots.
	 * @param rob The robot that are going to do its turn
	 * @param nr The phase number
	 */
	public void robotDoTurn(Robot rob,int nr) {
		Card c = rob.getCards()[nr];
		if(card instanceof /***/) {
			Direction dir = card.getRotate();
			rob.rotateLeft();
		} else if (card instanceof /***/) {
			int move = card.getMove();
			while(move > 0) {
				if(!rob.isAlive()) {
					break;
				}
				if(!robotMove(rob,rob.getDirection())) {
					break;
				}
			}
		}
	}
	// Problem: pos peker på den virkelige posisjonen til robot ? ev lag ny posisjon
	/**
	 * This method 
	 * @param rob
	 * @param dir
	 * @return
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
		T it = (T) board.getElement(pos);
		if(it instanceof /* hull */) {
			//roboten dør
			return true;
		}
		if(it instanceof /* vegg*/) {
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
	
	// Find rekkefølgen på robotenes doTurn()
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
	
	/**
	 * Chooses cards from the card pack
	 * @return array with 9 random playing cards
	 */
	public Card[] shuffleCards() {
		Card[] newCards = new Card[9];
		for(int x = 0; x < 9; x++) {
			int rand = rng.nextInt(cardPack.length);
			newCards[x] = cardPack[rand];
		}
		return newCards;
	}
	
	// Må gjøres bedre
	public void initializePlayers(int numb) {
		for(int x = 0; x < numb; x++) {
			Player per = new Player(Direction.NORTH, 2, 2, 10, "Robot");
			robots[x] = per;
		}
	}

}
