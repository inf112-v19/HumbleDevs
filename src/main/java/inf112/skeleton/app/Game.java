package inf112.skeleton.app;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import inf112.skeleton.app.board.Board;

public class Game {
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
			robots[robot].doTurn(nr);
		}
		// gjør alle aktivitetene på brettet
		// Sjekk om noen roboter står på noe, hvis dette er tilfellet må aktiviteten utføres
		// Robotene skyter, når???
		// Sjekk om noen roboter står noe som gir en ny backup
		// Registrere flagg
		// Sjekk om noen har vært på alle flagg, hvis ja --> den har vunnet
	}
	
	// Find rekkefølgen på robotenes doTur()
	public List findPriority(int cardnr) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for(int x = 0; x < robots.length; x++) {
			map.put(x, robots[x].getCards()[cardnr].getPriority());
			System.out.println(robots[x].getCards()[cardnr].getPriority());
		}
		List<Integer> priority = new ArrayList<Integer>(map.values());
		Collections.sort(priority);
		return priority;
		
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
