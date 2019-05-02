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
            while(true) {
                while (true) {
                    System.out.print("");
                    if (readyToLaunch) break;
                }
                Robot gameFinished = game.finished();
                if(gameFinished != null) {
                    System.out.println("Winner is " + gameFinished.getName());
                    GameScreen.checkForEndScreen();
                    break;
                }
                game.round();
                readyToLaunch = false;

                if (!readyToLaunch) {
                    GameScreen.startNewRound();
                }
            }

            while(true) {
                if(GameScreen.click) break;
                System.out.println("ajsfdsdfsd");
            }
            System.out.println("ajsfdsdfsd");
            if (!GameScreen.newGame) break;
        }
    }
}