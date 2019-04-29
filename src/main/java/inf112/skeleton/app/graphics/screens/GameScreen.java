package inf112.skeleton.app.graphics.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.ProgramCard;
import inf112.skeleton.app.card.ProgramCardDeck;
import inf112.skeleton.app.gameObjects.Player;
import inf112.skeleton.app.gameObjects.Robot;
import inf112.skeleton.app.graphics.AssetManager;
import inf112.skeleton.app.graphics.GUI;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 This class will represent the gamescreen (board and HUD)
 */

public class GameScreen extends ApplicationAdapter implements Screen {
    final GUI game;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRendererWithSprites mapRenderer;
    private Stage stage;
    public BitmapFont font;
    public Table table;
    private Player[] players;
    private ProgramCardDeck programCardDeck;
    private Map<Player, ArrayList> map;
    private int playerCounter;
    private ArrayList<ProgramCard> selectedCards = new ArrayList<>();
    private Skin skin;
    private AssetManager assetManager;
    //TILE_SIZE = pixel size of one tile (width and height)
    private final int TILE_SIZE = 64;
    private Tiled tiledEditor;
    private final int STEP_DELAY = 1; // in seconds
    private SequenceAction sequenceActions;

    private class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {
        public OrthogonalTiledMapRendererWithSprites(TiledMap map) {
            super(map);
        }

        @Override
        public void renderObject(MapObject object) {
            if(object instanceof TextureMapObject) {
                TextureMapObject textureObject = (TextureMapObject) object;

                if(object.isVisible()) {
                    // arguments: (texture region, x, y, originX, originY, width, height, scaleX, scaleY, the angle of counter clockwise rotation of the rectangle around originX/originY)
                    batch.draw(textureObject.getTextureRegion(), textureObject.getX(), textureObject.getY(), TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE, TILE_SIZE, 1, 1, textureObject.getRotation());
                }
            }
        }
    }

    public GameScreen(final GUI game, Player[] players) {
        this.game = game;
        this.assetManager = new AssetManager();
        this.stage = new Stage();
        this.table = new Table();
        this.skin = new Skin(Gdx.files.internal("assets/UI/uiskin.json"));
        this.players = players;
        this.playerCounter = 0;
        this.programCardDeck = new ProgramCardDeck();
        this.sequenceActions = new SequenceAction();
        font = new BitmapFont();
        //Important: makes us able to click on our stage and process inputs/events
        Gdx.input.setInputProcessor(stage);


        tiledMap = new TmxMapLoader().load("assets/maps/layeredTestMap.tmx");
        mapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
        tiledEditor = new Tiled(tiledMap, TILE_SIZE, players);

        camera = new OrthographicCamera();

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();


        map = new HashMap<>();

        createCardTable(table);
    }


    public void createCardTable(Table table) {
        table.setWidth(332);
        table.setHeight(600); //temp
        table.setPosition(768, 100);

        presentCards();
        stage.addActor(table);
    }

    public void addCardToSelected(ProgramCard card) {

        selectedCards.add(card);
        if (selectedCards.size() == 5) {
            //Deep copy av listen
            ArrayList<ProgramCard> newList = new ArrayList<>();
            for (ProgramCard pc : selectedCards) {
                newList.add(pc);
            }
            addPlayerWithCardsToHashmap(newList);
            selectedCards.clear();


            if (playerCounter == players.length) {
                table.clear();
                for (int i = 0; i < playerCounter; i++) {
                    ProgramCard[] cards = (ProgramCard[]) map.get(players[i]).toArray(new ProgramCard[5]);
                    players[i].setCards(cards);
                }
                drawHUD(map);
                return;
            }
            presentCards();
        }
    }

    public void addPlayerWithCardsToHashmap (ArrayList<ProgramCard> list) {
        map.put(players[playerCounter], list);

        playerCounter++;
    }


    private void drawHUD(Map<Player, ArrayList> map) {
        table.top();
        table.pad(0, 0, 0, 0);
        for (int i = 0; i < players.length; i++) {
            Image robot = new Image(new Texture(players[i].getPath()));
            table.add(robot);
            Label nameLabel = new Label(players[i].getName(), skin);
            table.add(nameLabel);

            for (int j = 0; j < players[i].getLifeTokens(); j++) {
                Image lifetoken = new Image(assetManager.getTexture("lifeIcon"));
                table.add(lifetoken);
            }


            table.row();
            ArrayList cardList = map.get(players[i]);
            for (int j = 0; j < cardList.size(); j++) {
                table.pad(10, 10, 10, 10);

                ProgramCard card = (ProgramCard) cardList.get(j);
                Texture texture = assetManager.getTexture(card.getActionAndMovement(card.getAction(), card.getMove()));
                Image img = new Image(texture);
                table.add(img);

            }
            table.row();
        }
        //Test updateBoard here
//        updateBoard(players[0]);
//        players[0].move(Direction.NORTH);
//        updateBoard(players[0]);
//        players[0].move(Direction.NORTH);
//        updateBoard(players[0]);
//        players[0].move(Direction.NORTH);
//        updateBoard(players[0]);
//        players[0].move(Direction.NORTH);
//        updateBoard(players[0]);
//        players[0].move(Direction.EAST);
//        players[0].move(Direction.EAST);
//        players[0].move(Direction.EAST);

//        updateBoard(players[0]);
//        players[0].move(Direction.EAST);
//        updateBoard(players[0]);
//        players[0].move(Direction.EAST);
//        updateBoard(players[0]);
//        players[0].move(Direction.EAST);
    }

    public void presentCards() {
        table.clear();
        final ProgramCard[] cards = programCardDeck.getRandomCards(9 - players[playerCounter].getDamageTokens()); // 9 cards here
        final Set<ProgramCard> pickedCards = new HashSet<>();
        Label infoLabel = new Label("Velg 5 kort", skin);
        Label playerLabel = new Label("Det er " + players[playerCounter].getName() + " sin tur", skin);
        table.add(infoLabel); table.row(); table.add(playerLabel); table.row();

        for (int i = 0; i < cards.length; i++) {
            // Her kan vi hente retning av kort og bruke assetmanager til å hente riktig bilde cards[i].getMove();
            Texture cardTexture = assetManager.getTexture(cards[i].getActionAndMovement(cards[i].getAction(), cards[i].getMove()));
            final Image img = new Image(cardTexture);

            final int finalI = i;
            img.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!pickedCards.contains(cards[finalI])) {
                        addCardToSelected(cards[finalI]);
                        pickedCards.add(cards[finalI]);
                    } // Kan gi beskjed til brukeren her at han/hun ikke kan velge det samme kortet
                    img.setColor(Color.GREEN);
                }
            });
            table.add(img).padBottom(20);
            table.row();
        }
    }



    /**
     * This method will update the position and direction of a robot on the board
     *
     * */
    public void updateBoard(final Robot robot) {

        // Using threads

//        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        executorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                moveRobot(robot);
//            }
//        }, 1, 1, TimeUnit.SECONDS);


        //Using regular action

//        sequenceActions.addAction(new Action() {
//            @Override
//            public boolean act(float v) {
//                if(robot.isDestroyed())
//                    tiledEditor.setRobotVisible(robot.getId(), false);
//                else
//                    tiledEditor.setRobotVisible(robot.getId(), true);
//                tiledEditor.moveRobot(robot.getId(), robot.getX(), robot.getY(), robot.getDirection());
//                System.out.println(counter);
//                return true;
//            }
//        });

        //SequenceAction: Using Action.delay

//        RunnableAction action = new RunnableAction() {
//            @Override
//            public boolean act(float v) {
//                if(robot.isDestroyed())
//                    tiledEditor.setRobotVisible(robot.getId(), false);
//                else
//                    tiledEditor.setRobotVisible(robot.getId(), true);
//                tiledEditor.moveRobot(robot.getId(), robot.getX(), robot.getY(), robot.getDirection());
//                System.out.println(counter);
//                counter ++;
//                return true;
//            }
//        };
//        sequenceActions.addAction(Actions.delay(1, action));

        // Create the action that will be executed
        RunnableAction action = new RunnableAction() {
            public void run() {
                if(robot.isDestroyed())
                    tiledEditor.setRobotVisible(robot.getId(), false);
                else
                    tiledEditor.setRobotVisible(robot.getId(), true);
                tiledEditor.moveRobot(robot.getId(), robot.getX(), robot.getY(), robot.getDirection());
            }
        };
        // Add the action to the sequence
        sequenceActions.addAction(action);
        // Add a delay to the sequence
        sequenceActions.addAction(Actions.delay(1));


        for (int i = 0; i < sequenceActions.getActions().size; i++) {
            System.out.print(sequenceActions.getActions().get(i).toString() + " ");

        }
        System.out.println();
    }



    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        mapRenderer.setView(camera);
        mapRenderer.render();

//        sequenceActions.reset();
        if(sequenceActions.act(delta));


        //stage
        update(delta);
        stage.draw(); // important

        game.batch.begin();
        //game.font.draw(game.batch, "PLEASE", 800, 300);

        // Her kan vi tegne :D
        game.batch.end();
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

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}