package inf112.skeleton.app;

import java.util.Random;

public class Player extends Robot {
	private String name;
	private Card[] cards;
	
	
    Player(Direction dir, int xPos, int yPos, int health, String name) {
		super(dir, xPos, yPos, health);
		this.name = name;
	}
    //Antall liv vil også påvirke antall kort


}
