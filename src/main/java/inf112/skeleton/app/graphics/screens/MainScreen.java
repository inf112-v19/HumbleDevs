package inf112.skeleton.app.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.game.Game;
import inf112.skeleton.app.graphics.AssetManager;
import inf112.skeleton.app.graphics.GUI;

import java.util.ArrayList;

public class MainScreen implements Screen {
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Texture texture;
    protected static Skin skin;
    protected static Skin defaultSkin;
    final GUI gui;
    Table mainTable;
    Table nextTable;
    ArrayList<String> playerNames;
    Game game;
    SelectBox<String> levelSelected;

    public MainScreen(final GUI gui, Game game) {
        this.game = game;
        this.gui = gui;
        this.playerNames = new ArrayList<>();

        skin = new Skin(Gdx.files.internal("assets/UI/skin/star-soldier-ui.json"));
        defaultSkin = new Skin(Gdx.files.internal("assets/UI/uiskin.json"));

        AssetManager assetManager = new AssetManager();
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
        final Dialog maxRobots = new Dialog("Max 6 players!", defaultSkin);
        final Dialog dplayer = new Dialog("Players", defaultSkin);
        final Dialog dcomputer = new Dialog("AIs", defaultSkin);

        //Stage should control input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.top();
        Label title = new Label("Welcome to the Robo Rally Game!", skin);
        title.addAction(Actions.forever(new SequenceAction(Actions.fadeOut(1), Actions.fadeIn(1))));

        nextTable = new Table();
        nextTable.setFillParent(true);
        nextTable.bottom();

        // Map thumbnails
        Image level1 = new Image(AssetManager.getTexture("level1"));
        Image level2 = new Image(AssetManager.getTexture("level2"));
        Image level3 = new Image(AssetManager.getTexture("level3"));

        // Adding the thumbnails to the table
        Table thumbnailTable = new Table();
        thumbnailTable.add(level1).height(level1.getPrefHeight() / 10).width(level1.getPrefWidth() / 10).size(90).padBottom(3).padTop(100);
        thumbnailTable.row();
        thumbnailTable.add(level2).height(level2.getPrefHeight() / 10).width(level2.getPrefWidth() / 10).size(90).padBottom(3);
        thumbnailTable.row();
        thumbnailTable.add(level3).height(level3.getPrefHeight() / 10).width(level3.getPrefWidth() / 10).size(90).padBottom(3);
        thumbnailTable.row();

        // Creating the dropdown for levels
        levelSelected = new SelectBox<>(skin);
        levelSelected.setItems("level1", "level2", "level3");
        levelSelected.setSelected("level1");
        levelSelected.setPosition(100, 100);

        thumbnailTable.add(levelSelected);

        maxRobots.setPosition(35, 205);
        maxRobots.setHeight(20);
        maxRobots.setWidth(120);
        maxRobots.setColor(Color.GREEN);

        title.setPosition(10, 300);
        title.setHeight(200);
        title.setWidth(310);
        title.setFontScale(1,1);

        dplayer.setPosition(35, 275);
        dplayer.setHeight(20);
        dplayer.setWidth(100);
        dplayer.setColor(Color.BLUE);

        dcomputer.setPosition(100, 275);
        dcomputer.setHeight(20);
        dcomputer.setWidth(90);
        dcomputer.setColor(Color.PINK);

        stage.addActor(dplayer);
        stage.addActor(dcomputer);
        stage.addActor(title);
        stage.addActor(maxRobots);

        //Create buttons
        TextButton exitButton = new TextButton("Exit", skin);
        TextButton playButton = new TextButton("Play", skin);

        //Add buttons to table
        mainTable.row();
        // Set row in center for better GUI
        mainTable.center();
        final SelectBox<Integer> selectBoxPlayers = new SelectBox<>(skin);
        final SelectBox<Integer> selectBoxRobots = new SelectBox<>(skin);

        // Selection box
        selectBoxPlayers.setItems(1, 2, 3, 4, 5, 6);
        selectBoxRobots.setItems(0, 1, 2, 3, 4, 5, 6);

        // Add boxes to mainTable
        mainTable.add(selectBoxPlayers);
        mainTable.add(selectBoxRobots);
        mainTable.add(playButton);
        mainTable.add(thumbnailTable);
        nextTable.add(exitButton);

        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectBoxPlayers.getSelected() + selectBoxRobots.getSelected() > 6) {
                    System.out.println("Max 6 players");
                } else {
                    maxRobots.remove();
                    dcomputer.remove();
                    dplayer.remove();
                    setNames(selectBoxPlayers.getSelected(), selectBoxRobots.getSelected());
                }
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
        stage.addActor(nextTable);
    }


    /**
     * Used to get the name from a player
     * @param idx
     * @param players
     * @param robots
     */
    public void helper(int idx, final int players, final int robots) {
        mainTable.clear();
        Label label = new Label("Skriv inn navnet til spiller " + idx, skin);
        final TextField tf = new TextField("", skin);
        tf.setMaxLength(6);
        final TextButton submitButton = new TextButton("Submit", skin);
        mainTable.add(label);
        mainTable.row();
        mainTable.add(tf);
        mainTable.row();
        mainTable.add(submitButton);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerNames.add(tf.getText());
                setNames(players, robots);
            }
        });
    }

    private int index = 1;
    public void setNames(int players, int robots) {

        if (index - 1 == players) {
            // If every player has entered their name, start the game!
            customCreate(robots);
        } else {
            helper(index, players, robots);
            index++;
        }
    }

    /**
     * Called when every player has entered their name
     * @param robots
     */
    public void customCreate(int robots) {
        gui.setScreen(new GameScreen(gui, game, playerNames, robots, levelSelected.getSelected()));
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
    }
}