package inf112.skeleton.app.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import inf112.skeleton.app.graphics.Tiled;


public class Main {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "hello-robot";
        cfg.width = 792;
        cfg.height = 792;
        new LwjglApplication(new Tiled(), cfg);
    }
}