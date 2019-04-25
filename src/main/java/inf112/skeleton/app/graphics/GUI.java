package inf112.skeleton.app.graphics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.gameObjects.Player;
import inf112.skeleton.app.graphics.screens.GameScreen;

public class GUI extends Game {
    GameScreen gameScreen;
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //For testing purposes
        Player player = new Player(0, null, 0, 0, "Joachim", null);
        Player[] arr = player.getSomePlayers();
        //
        this.setScreen(new GameScreen(this, arr));
        font = new BitmapFont();
        //this.setScreen(new GameScreen(this));
        //this.setScreen(new MenuScreen(this));
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