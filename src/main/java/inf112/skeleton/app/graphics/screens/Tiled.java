package inf112.skeleton.app.graphics.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.board.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Tiled {
    TiledMap tiledMap;
    MapLayer objectLayer;
    private final int NUMBER_OF_ROBOTS;
    //TILE_SIZE = pixel size of one tile (width and height)
    private final int TILE_SIZE = 64;
    //DOCK_ID needs to correspond to the eight tile IDs of the starting docks for each robot (see ItemFactory)
    private final ArrayList<Integer> DOCK_ID = new ArrayList<>(Arrays.asList(85, 86, 87, 88, 89, 90, 91, 92));
    private final HashMap<Integer, Position> DOCK_POSITIONS;

    public Tiled(TiledMap tiledMap, int robots) {
        this.tiledMap = tiledMap;
        this.NUMBER_OF_ROBOTS = robots;
        //Initiate dock positions
        TiledMapTileLayer bg = (TiledMapTileLayer) tiledMap.getLayers().get("background");
        this.DOCK_POSITIONS = new HashMap<>();
        for (int x = 0; x < bg.getWidth(); x++) {
            for (int y = 0; y < bg.getHeight(); y++) {
                int tileId = bg.getCell(x, y).getTile().getId();
                if (DOCK_ID.contains(tileId)) {
                    DOCK_POSITIONS.put(tileId, new Position(x, y));
                }
            }
        }
        // TODO: Cast error if the dock-positions necessary are not set?
        for (int i = 0; i < NUMBER_OF_ROBOTS; i++) {
            if (DOCK_POSITIONS.get(DOCK_ID.get(i)) == null) {
                throw new IllegalStateException("Can't find dock (starting position) for all robots");
            }
        }

        initiateObjectLayer();
    }

    private void initiateObjectLayer() {
        objectLayer = tiledMap.getLayers().get("objects");
        for (int i = 0; i < NUMBER_OF_ROBOTS; i++) {
            Texture texture = new Texture(Gdx.files.internal("texture/robot" + (i + 1) + ".png"));
            TextureRegion textureRegion = new TextureRegion(texture, TILE_SIZE, TILE_SIZE);
            TextureMapObject tmo = new TextureMapObject(textureRegion);
            // TODO: Get DOCK positions
            System.out.println("Dock#, x, y = (" + DOCK_ID.get(i) + ", " + DOCK_POSITIONS.get(DOCK_ID.get(i)).getX() + ", " + DOCK_POSITIONS.get(DOCK_ID.get(i)).getY() + ")");
            tmo.setX(DOCK_POSITIONS.get(DOCK_ID.get(i)).getX() * TILE_SIZE);
            tmo.setY(DOCK_POSITIONS.get(DOCK_ID.get(i)).getY() * TILE_SIZE);
            objectLayer.getObjects().add(tmo);
        }
    }

    /**
     * @param robotNumber must be 0-indexed
     * @param x
     * @param y
     */
    public void moveRobot(int robotNumber, int x, int y) {
        TextureMapObject robot = (TextureMapObject) tiledMap.getLayers().get("objects").getObjects().get(robotNumber);
        robot.setX(x * TILE_SIZE);
        robot.setY(y * TILE_SIZE);
//        robot.setRotation(180);
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

}