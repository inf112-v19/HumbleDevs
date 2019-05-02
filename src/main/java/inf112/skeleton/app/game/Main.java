package inf112.skeleton.app.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import inf112.skeleton.app.gameObjects.Robot;
import inf112.skeleton.app.graphics.GUI;
import inf112.skeleton.app.graphics.screens.GameScreen;


public class Main {
    public static boolean readyToLaunch = false;

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally";
        cfg.width = 1100;
        cfg.height = 768;
        Game game = new Game();

        LwjglApplication lw = new LwjglApplication(new GUI(game), cfg);
        while(true) {
            while (true) {
                System.out.print("");
                if (readyToLaunch) break;
            }
            game.round();
            readyToLaunch = false;

            if (!readyToLaunch) {
                GameScreen.startNewRound();
            }

            Robot gameFinished = game.finished();
            if(gameFinished != null) {
                System.out.println("Winner is " + gameFinished.getName());
                break;
            }
        }
        //System.out.println("YIHA");
        //game.round(); //LOL





//        Board board = new Board(map);
//
//        Game game = new Game(board);
//        Robot[] robots = game.getRobots();

//        boolean done = false;
//        while (!done){
//            Robot rob = game.round();
//            if (rob != null) {
//                done = true;
//                // Annonser rob som vinner
//            }
//        }
    }
}