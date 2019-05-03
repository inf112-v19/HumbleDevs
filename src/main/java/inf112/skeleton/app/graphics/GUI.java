package inf112.skeleton.app.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.game.Game;
import inf112.skeleton.app.graphics.screens.MainScreen;

public class GUI extends com.badlogic.gdx.Game {
    public SpriteBatch batch;
    private Game game;

    public GUI (Game game) {
        this.game = game;
    }

    public void newGame(Game game) {
        this.game = game;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainScreen(this, game));
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