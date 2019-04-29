package inf112.skeleton.app.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.graphics.GUI;

public class MainScreen implements Screen {

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    final GUI game;
    Table mainTable;

    public MainScreen(final GUI game) {
        this.game = game;
        skin = new Skin(Gdx.files.internal("assets/UI/uiskin.json"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(500, 500, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }


    @Override
    public void show() {
        //Stage should control input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.top();

        //Create buttons
        TextButton playButton = new TextButton("Play", skin);
        //TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        //Add buttons to table
        mainTable.add(playButton);
        mainTable.row();
        final SelectBox<Integer> selectBoxPlayers = new SelectBox<Integer>(skin);
        final SelectBox<Integer> selectBoxRobots = new SelectBox<Integer>(skin);

        selectBoxPlayers.setItems(1, 2, 3, 4, 5, 6);
        selectBoxRobots.setItems(1, 2, 3, 4, 5, 6);

        mainTable.add(selectBoxPlayers);
        mainTable.add(selectBoxRobots);

        mainTable.add(exitButton);

        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectBoxPlayers.getSelected() + selectBoxRobots.getSelected() > 6) {
                    System.out.println("Max 6 players");
                }
                setNames(selectBoxPlayers.getSelected(), selectBoxRobots.getSelected());
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        //Add table to stage
        stage.addActor(mainTable);
    }

    public void setNames(int players, int robots) {
        mainTable.clear();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.top();

        mainTable.setColor(Color.GREEN);
        //TODO get random names for the robots
//
//        for (int i = 0; i < players; i++) {
//            TextField tf = new TextField("", skin);
//            mainTable.addActor(tf);
//
//        }


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();
        atlas.dispose();
    }
}