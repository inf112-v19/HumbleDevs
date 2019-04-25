package inf112.skeleton.app.gameObjects;

import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.ProgramCard;


public class Player extends Robot {
    private String name;
    private String path;


    public Player(int id, Direction dir, int xPos, int yPos, String name, String path) {
        super(id, dir, xPos, yPos, name, path);
        this.name = name;
        this.path = path;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Player[] getSomePlayers() {
        Player[] arr = new Player[3];
        Player player1 = new Player(0, Direction.EAST, 0, 0, "Joa", null);
        Player player2 = new Player(1, Direction.EAST, 1, 0, "Jo", null);
        Player player3 = new Player(2, Direction.EAST, 2, 0, "J", null);
        arr[0] = player1;
        arr[1] = player2;
        arr[2] = player3;
        return arr;
    }


}