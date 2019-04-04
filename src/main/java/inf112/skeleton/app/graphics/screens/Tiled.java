package inf112.skeleton.app.graphics.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.graphics.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Tiled extends ApplicationAdapter implements Screen {
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private final GUI game;
    TiledMap tiledMap;
    MapLayer objectLayer;
    private final int NUMBER_OF_ROBOTS;
    //DOCK_ID needs to correspond to the eight tile IDs of the starting docks for each robot (see ItemFactory)
    private final ArrayList<Integer> DOCK_ID = new ArrayList<>(Arrays.asList(85,86,87,88,89,90,91,92));
    private final HashMap<Integer, Position> DOCK_POSITIONS;

    public Tiled(final GUI game, int robots) {
        this.game = game;
        this.NUMBER_OF_ROBOTS = robots;
        //Initiate dock positions
        TiledMapTileLayer bg = (TiledMapTileLayer) tiledMap.getLayers().get("background");
        this.DOCK_POSITIONS = new HashMap<>();
        for (int x = 0; x < bg.getWidth(); x++) {
            for (int y = 0; y < bg.getHeight(); y++) {
                int tileId = bg.getCell(x,y).getTile().getId();
                if (DOCK_ID.contains(tileId)) {
                    DOCK_POSITIONS.put(tileId, new Position(x, y));
                }
            }
        }
        // TODO: Cast error if the dock-positions necessary are not set?
        for (int i = 0; i < NUMBER_OF_ROBOTS; i++) {
            if(DOCK_POSITIONS.get(DOCK_ID.get(i)) == null) {
                throw new IllegalStateException("Can't find dock (starting position) for all robots");
            }
        }
        camera = new OrthographicCamera();

    }

    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();
        tiledMap = new TmxMapLoader().load("Assets/maps/layeredTestMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        initiateObjectLayer();

        Gdx.input.setInputProcessor((InputProcessor) this);
    }

    private void initiateObjectLayer() {
        objectLayer = tiledMap.getLayers().get("objects");
        for (int i = 0; i < NUMBER_OF_ROBOTS; i++) {
            Texture texture = new Texture(Gdx.files.internal("robot" + (i+1) + ".png"));
            TextureRegion textureRegion = new TextureRegion(texture,64,64);
            TextureMapObject tmo = new TextureMapObject(textureRegion);
            // TODO: Get DOCK positions
            tmo.setX(DOCK_POSITIONS.get(DOCK_ID.get(i)).getX());
            tmo.setY(DOCK_POSITIONS.get(DOCK_ID.get(i)).getY());
            objectLayer.getObjects().add(tmo);
        }
    }

    /**
     *
     * @param robotNumber must be 0-indexed
     * @param x
     * @param y
     */
    public void moveRobot(int robotNumber, int x, int y) {
        TextureMapObject robot = (TextureMapObject) tiledMap.getLayers().get("objects").getObjects().get(robotNumber);
        robot.setX(x);
        robot.setY(y);
    }

//    /**
//     * Insert a player/robot texture in a cell on the board.
//     * The (x,y) position (0,0) is bottom left
//     *
//     * drawPlayer(0, 0, "texture/robot1.png"); //should place the robot texture in bottom left corner, facing North
//     * @param x position
//     * @param y position
//     * @param texturePath the path to the texture
//     */
//    public void drawPlayer(int x, int y, String texturePath) {
//        drawPlayer(x, y, texturePath, Direction.NORTH);
//    }
//    public void drawPlayer(int x, int y, String texturePath, Direction facingDir) {
//        //Placing a player on the board
//        //Create a new texture with the robot picture
//        Texture robotTexture = new Texture(Gdx.files.internal(texturePath));
//        //Create a TextureRegion that is the entire size of the texture
//        TextureRegion textureRegion = new TextureRegion(robotTexture, 64, 64);
//        //Create a cell(tile) to add to the layer
//        Cell cell = new Cell();
//        //Set the graphic for the new cell
//        cell.setTile(new StaticTiledMapTile(textureRegion));
//        //Rotate
//        if(facingDir == Direction.WEST) cell.setRotation(1);
//        else if(facingDir == Direction.SOUTH) cell.setRotation(2);
//        else if(facingDir == Direction.EAST) cell.setRotation(3);
//        else cell.setRotation(0); //By default no rotation (should be facing NORTH)
//        //Get layer to put cell in
//        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("players");
//        //place cell on layer in (x,y) coordinate
//        layer.setCell(x, y, cell);
//    }

    public void render() {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

    }

    public void draw() {

    }

    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT)
            camera.translate(-32, 0);
        if (keycode == Input.Keys.RIGHT)
            camera.translate(32, 0);
        if (keycode == Input.Keys.UP)
            camera.translate(0, -32);
        if (keycode == Input.Keys.DOWN)
            camera.translate(0, 32);
        if (keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if (keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        return false;
    }

    public boolean keyTyped(char character) {

        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void hide() {

    }
}