package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import inf112.skeleton.app.board.GUi_Board;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "hello-world";
        cfg.width = 1800;
        cfg.height = 1800;
        new LwjglApplication(new GUi_Board(), cfg);
    }
}