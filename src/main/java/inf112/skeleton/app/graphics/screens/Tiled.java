package inf112.skeleton.app.graphics.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.gameObjects.Robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * An object used to edit a TiledMap.
 * Specifically for manipulating robots(players/nonplayers)
 *
 * Pre-condition:
 *  - A TiledMap that contains enough docks for the chosen number of robots.
 *  - Correct dock IDs to be set in the DOCK_ID list (according to the given tileId in the tile set).
 *
 */
public class Tiled {
    TiledMap tiledMap;
    MapLayer objectLayer;
    private final int NUMBER_OF_ROBOTS;
    //TILE_SIZE = pixel size of one tile (width and height)
    private final int TILE_SIZE;
    //DOCK_ID = the eight tile IDs of the starting docks for the robots (see gameObjects\Items\ItemFactory for tileIDs)
    private final ArrayList<Integer> DOCK_ID = new ArrayList<>(Arrays.asList(85, 86, 87, 88, 89, 90, 91, 92));
    private final HashMap<Integer, Position> DOCK_POSITIONS;

    public Tiled(TiledMap tiledMap, int tileSize, Robot[] robots) {
        this.TILE_SIZE = tileSize;
        this.tiledMap = tiledMap;
        this.NUMBER_OF_ROBOTS = robots.length;

        //Initiate dock positions
        this.DOCK_POSITIONS = new HashMap<>();
        TiledMapTileLayer bg = (TiledMapTileLayer) tiledMap.getLayers().get("background");
        for (int x = 0; x < bg.getWidth(); x++) {
            for (int y = 0; y < bg.getHeight(); y++) {
                int tileId = bg.getCell(x, y).getTile().getId();
                if (DOCK_ID.contains(tileId)) {
                    DOCK_POSITIONS.put(tileId, new Position(x, y));
                }
            }
        }

        if(DOCK_ID.size() < NUMBER_OF_ROBOTS) throw new IllegalStateException("Can't find dock (starting position) for all robots in the tiledMap");
        for (int i = 0; i < NUMBER_OF_ROBOTS; i++) {
            if (DOCK_POSITIONS.get(DOCK_ID.get(i)) == null) {
                throw new IllegalStateException("Can't find dock number (zero indexed) " + i);
            }
        }

        // Initiate robots in docks
//        objectLayer = tiledMap.getLayers().get("objects");
//        for (int i = 0; i < NUMBER_OF_ROBOTS; i++) {
//            int rotation = directionToRotation(robots[i].getDirection());
//            Texture robotTexture = new Texture(Gdx.files.internal(robots[i].getPath()));
//            TextureRegion robotTR = new TextureRegion(robotTexture, TILE_SIZE, TILE_SIZE);
//            TextureMapObject robotObject = new TextureMapObject(robotTR);
//            robotObject.setX(DOCK_POSITIONS.get(DOCK_ID.get(i)).getX() * TILE_SIZE);
//            robotObject.setY(DOCK_POSITIONS.get(DOCK_ID.get(i)).getY() * TILE_SIZE);
//            robotObject.setRotation(rotation);
//            objectLayer.getObjects().add(robotObject);
//        }
    }

    public HashMap<Integer, Position> getDockPositions(){return null;}



    /**
     * Moves a robot to a final x,y position on the tiled board (0,0 = bottom left)
     *
     * @param robotId 0-indexed
     * @param x
     * @param y
     * @param dir direction the robot should face (assuming the robot texture by default faces north)
     */
//    public void moveRobot(int robotId, int x, int y, Direction dir) {
//        TextureMapObject robot = (TextureMapObject) tiledMap.getLayers().get("objects").getObjects().get(robotId);
//        robot.setX(x * TILE_SIZE);
//        robot.setY(y * TILE_SIZE);
//        // Note: The rotation should actually be stored in radians according to the TextureMapObject doc,
//        // but since we only use this in the drawing function (in GameScreen), which represents rotation as counter clockwise degrees,
//        // we store it this way for convenience and less complicated computations.
//        robot.setRotation(directionToRotation(dir));
//    }
//
//    /**
//     * Rotates a robot
//     * @param robotId 0-indexed
//     * @param dir direction
//     *
//     */
//    public void rotateRobot(int robotId, Direction dir) {
//        TextureMapObject robot = (TextureMapObject) tiledMap.getLayers().get("objects").getObjects().get(robotId);
//        robot.setRotation(directionToRotation(dir));
//    }


    /**
     * Set the visibility for a robot
     *
     * @param robotId
     * @param visible
     */
    public void setRobotVisible(int robotId, boolean visible) {
        TextureMapObject robot = (TextureMapObject) tiledMap.getLayers().get("objects").getObjects().get(robotId);
        robot.setVisible(visible);
    }

}