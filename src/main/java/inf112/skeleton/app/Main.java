package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import inf112.skeleton.app.board.GUI;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "hello-world";
        cfg.width = 800;
        cfg.height = 800;
        new LwjglApplication(new GUI(), cfg);
    }
    
    
    
    public void setUpRound() {
    	// Del ut kort og hver spiller må velge fem av de
    }
    
    public void round() {
    	for(int x = 0; x < 5; x++) {
    		// Hver spiller gjør sitt trekk
    	}
    }
}