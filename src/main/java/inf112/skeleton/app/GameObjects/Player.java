package inf112.skeleton.app.GameObjects;

import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.ProgramCard;

import java.util.PriorityQueue;
import java.util.Queue;

public class Player extends Robot {
	private String name;
	private ProgramCard[] cards;
	private Queue<ProgramCard> q;
	
	
    public Player(Direction dir, int xPos, int yPos, String name) {
		super(dir, xPos, yPos);
		this.name = name;
		q = new PriorityQueue<>();
	}



}
