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

            Robot gameFinished = game.finished();
            if(gameFinished != null) {
                System.out.println("Winner is " + gameFinished.getName());
                break;
            }

            if (!readyToLaunch) {
                game.startRobots();
                GameScreen.startNewRound();

            }



        }
        System.out.println("YIHA");
        //game.round(); //LOL


    }
}