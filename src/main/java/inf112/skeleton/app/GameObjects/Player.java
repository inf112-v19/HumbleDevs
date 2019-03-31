package inf112.skeleton.app.gameObjects;

import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.ProgramCard;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class Player extends Robot {
	private String name;

    //This will be the 5 cards the player chooses
	private ProgramCard[] cards;

	// ProgramCards will be a priorityqueue so cards are popped in correct order
	private Queue<ProgramCard> q;

    public Player(Direction dir, int xPos, int yPos, String name) {
		super(dir, xPos, yPos);
		this.name = "Joachim";
		q = new PriorityQueue<>();
	}

    @Override
    public ProgramCard[] getCards() {
        return cards;
    }

    public void setCards(ProgramCard[] cards) {
        this.cards = cards;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
