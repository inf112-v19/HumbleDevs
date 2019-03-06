package inf112.skeleton.app.graphics;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GUI implements ApplicationListener {
    private SpriteBatch batch;
    private Texture mainBackgroundImg;
    private Texture robotImg;
    private TextureRegion mainBackground;
    private TextureRegion robot;

    
    @Override
    public void create() {
        batch = new SpriteBatch();
        mainBackgroundImg = new Texture(Gdx.files.internal("texture/board1.png"));
        robotImg = new Texture((Gdx.files.internal("texture/robot.png")));
        mainBackground = new TextureRegion(mainBackgroundImg, 0, 0, 1800, 1800);
        robot = new TextureRegion(robotImg, 0, 0, 64, 64);
    }

    @Override
    public void dispose() {
        batch.dispose();
        mainBackgroundImg.dispose();
        robotImg.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(mainBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(robot, 400, 400, 64, 64);
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
