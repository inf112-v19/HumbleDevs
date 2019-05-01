package inf112.skeleton.app.graphics;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.game.Game;
import inf112.skeleton.app.gameObjects.Player;
import inf112.skeleton.app.graphics.screens.MainScreen;

public class GUI extends com.badlogic.gdx.Game {
    public SpriteBatch batch;
    public BitmapFont font;
    private Game game;

    public GUI (Game game) {
        this.game = game;


    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        //For testing purposes
        Player player = new Player(0, null, 0, 0, "Joachim", null);
        Player[] arr = player.getSomePlayers();
        //

        this.setScreen(new MainScreen(this, game));
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