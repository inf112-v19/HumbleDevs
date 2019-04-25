package inf112.skeleton.app.gameObjects;

import inf112.skeleton.app.board.Direction;


public class Player extends Robot {



    public Player(int id, Direction dir, int xPos, int yPos, String name, String filePath) {
        super(id, dir, xPos, yPos, name, filePath);

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