package inf112.skeleton.app.GameObjects;

import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.ProgramCard;

public class Player extends Robot {

	public Player(Direction dir, int xPos, int yPos, String name, String filepath) {
		super(dir, xPos, yPos, name, filepath);
	}
}