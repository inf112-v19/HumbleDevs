package inf112.skeleton.app.GameObjects;

import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.ProgramCard;

public class Player extends Robot {
	private String name;
	private ProgramCard[] cards;
	
	
    public Player(Direction dir, int xPos, int yPos, int health, String name) {
		super(dir, xPos, yPos);
		this.name = name;
	}
    
    @Override
    public void chooseCards(ProgramCard[] cards) {
    	
    }
}
