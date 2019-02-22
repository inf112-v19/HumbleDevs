package inf112.skeleton.app;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Position;

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
	 * Starts one phase
	 * @param nr the number of the phase in this round
	 */
	public void phase(int nr) {
		ArrayList prio = (ArrayList) findPriority(nr);
		for(int x = 0; x < robots.length; x++) {
			int robot = (int) prio.get(x);
			robotDoTurn(robots[robot]);
		}
		// gjør alle aktivitetene på brettet
		// Sjekk om noen roboter står på noe, hvis dette er tilfellet må aktiviteten utføres
		// Robotene skyter, når???
		// Sjekk om noen roboter står noe som gir en ny backup
		// Registrere flagg
		// Sjekk om noen har vært på alle flagg, hvis ja --> den har vunnet
	}
	
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
		T it = (T) board.getElement(pos);
		if(it instanceof /* hull */) {
			//roboten dør
			return true;
		}
		if(it instanceof /* vegg*/) {
			return false;
		}
		if(board.isFree(pos)) {
			rob.move(1);
			return true;
		}
		Robot rob2 = board.getRobot(pos);
		boolean moved = robotMove(rob2,dir);
		if(moved) {
			rob.move(1);
		}
		return true;
	}
	
	// Find rekkefølgen på robotenes doTurn()
	public List findPriority(int cardnr) {
		class pri{
			public int priority;
			public int robot;
			public pri(int pri, int rob) {
				this.priority = pri;
				this.robot = rob;
			}
			public int getPri() {
				return priority;
			}
		}
		Map<Integer, pri> pri_card = new HashMap<>();
		for(int x = 0; x < robots.length; x++) {
			pri d = new pri(robots[x].getCards()[cardnr].getPriority(),x);
			pri_card.put(robots[x].getCards()[cardnr].getPriority(), d);
		}
		List<pri> sdf = new ArrayList<>(pri_card.values());
		Collections.sort(sdf, Comparator.comparing(pri::getPri));
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
