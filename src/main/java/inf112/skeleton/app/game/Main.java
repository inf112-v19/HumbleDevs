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
        GUI gui = new GUI(game);

        LwjglApplication lw = new LwjglApplication(gui, cfg);

        while(true) {
            // Loop for one whole game
            while(true) {
                while (true) {
                    System.out.print("");
                    if (readyToLaunch) break;
                }
                Robot gameFinished = game.finished();
                if(gameFinished != null) {
                    System.out.println("Winner is " + gameFinished.getName());
                    GameScreen.checkForEndScreen();
                    readyToLaunch = false;
                    break;
                }
                game.round();
                readyToLaunch = false;

                if (!readyToLaunch) {
                    GameScreen.startNewRound();
                }
            }

            // Wait for user to start new game
            while(true) {
                System.out.print("");
                if(GameScreen.newGame) break;
            }
            // Start new game
            game = new Game();
            gui.newGame(game);

        }
    }
}