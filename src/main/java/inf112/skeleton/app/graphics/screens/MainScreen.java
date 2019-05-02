package inf112.skeleton.app.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.game.Game;
import inf112.skeleton.app.graphics.GUI;

import java.util.ArrayList;

public class MainScreen implements Screen {

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    protected Skin defaultSkin;
    protected Skin altSkin;
    final GUI gui;
    private Texture texture;
    Table mainTable;
    ArrayList<String> playerNames;
    Game game;


    public MainScreen(final GUI gui, Game game) {
        this.game = game;
        this.gui = gui;
        this.playerNames = new ArrayList<>();

        skin = new Skin(Gdx.files.internal("assets/UI/skin/star-soldier-ui.json"));
        defaultSkin = new Skin(Gdx.files.internal("assets/UI/uiskin.json"));
        //altSkin = new Skin(Gdx.files.internal("assets/UI/alt/uiskin.json"));

        texture = new Texture(Gdx.files.internal("assets/UI/frontRobot.png"));


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
        Label hey = new Label("Welcome to the RoboRally Game!", skin);
        final Dialog di = new Dialog("Max 6 players", defaultSkin);
        final Dialog dplayer = new Dialog("Nr of Players", defaultSkin);
        final Dialog dcomputer = new Dialog("Nr of AIs", defaultSkin);

        di.setPosition(60, 195);
        di.setHeight(20);
        di.setWidth(110);
        di.setColor(Color.GREEN);

        hey.setPosition(10, 300);
        hey.setHeight(200);
        hey.setWidth(310);
        hey.setFontScale(1,1);

        dplayer.setPosition(45, 280);
        dplayer.setHeight(20);
        dplayer.setWidth(100);
        dplayer.setColor(Color.BLUE);

        dcomputer.setPosition(145, 280);
        dcomputer.setHeight(20);
        dcomputer.setWidth(90);
        dcomputer.setColor(Color.PINK);


        stage.addActor(hey);
        stage.addActor(di);
        stage.addActor(dplayer);
        stage.addActor(dcomputer);

        //Create buttons
        TextButton exitButton = new TextButton("Exit", skin);
        TextButton playButton = new TextButton("Play", skin);

        //Add buttons to table
        mainTable.row();
        // Set row in center for better GUI
        mainTable.center();
;       final SelectBox<Integer> selectBoxPlayers = new SelectBox<Integer>(skin);
        final SelectBox<Integer> selectBoxRobots = new SelectBox<Integer>(skin);


        // Selection box
        selectBoxPlayers.setItems(1, 2, 3, 4, 5, 6);
        selectBoxRobots.setItems(0, 1, 2, 3, 4, 5, 6);

        // Add boxes to mainTable
        mainTable.add(selectBoxPlayers);
        mainTable.add(selectBoxRobots);
        mainTable.add(playButton);
        mainTable.add(exitButton);

        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectBoxPlayers.getSelected() + selectBoxRobots.getSelected() > 6) {
                    System.out.println("Max 6 players");
                } else {
                    di.remove();
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
    }



    public void helper(int idx, final int players, final int robots) {
        mainTable.clear();
        // OVERSKRIFT: VELG NAVN TIL SPILLER i
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
            // Her kan vi starte spillet evt

            customCreate(robots);
        } else {
            helper(index, players, robots);
            index++;
        }
    }

    public void customCreate(int robots) {
        gui.setScreen(new GameScreen(gui, game, playerNames, robots));
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