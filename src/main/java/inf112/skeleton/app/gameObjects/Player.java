package inf112.skeleton.app.gameObjects;

import inf112.skeleton.app.board.Direction;


public class Player extends Robot {



    public Player(int id, Direction dir, int xPos, int yPos, String name, String filePath) {
        super(id, dir, xPos, yPos, name, filePath);

    }


    public Player[] getSomePlayers() {
        Player[] arr = new Player[1];
        Player player1 = new Player(0, Direction.NORTH, 0, 1, "Joa", "texture/robot1.png");
//        Player player2 = new Player(1, Direction.NORTH, 2, 2, "Jo", "texture/robot2.png");
//        Player player3 = new Player(2, Direction.NORTH, 3, 0, "J", "texture/robot3.png");
        arr[0] = player1;
//        arr[1] = player2;
//        arr[2] = player3;
        return arr;
    }


}