package inf112.skeleton.app.board;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GUi_Board implements ApplicationListener {
    private SpriteBatch batch;
    private Sprite sprite;
    private Texture texture;
    private TextureRegion mainBackground;
    
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("Assets/board1.png"));
        mainBackground = new TextureRegion(texture, 0, 0, 1800, 1800);
        batch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(mainBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
