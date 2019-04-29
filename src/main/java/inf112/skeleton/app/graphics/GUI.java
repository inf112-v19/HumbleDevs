package inf112.skeleton.app.graphics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.gameObjects.Player;
import inf112.skeleton.app.graphics.screens.MainScreen;

public class GUI extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //For testing purposes
        Player player = new Player(null, 0, 0, "Joachim", null);
        Player[] arr = player.getSomePlayers();
        //

        this.setScreen(new MainScreen(this));
        //this.setScreen(new MainScreen(this));


        //this.setScreen(new GameScreen(this, arr)); //Original screen, working
    }



    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}