package inf112.skeleton.app.board;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.GameObjects.Items.IItem;
import inf112.skeleton.app.GameObjects.Items.ItemFactory;
import inf112.skeleton.app.GameObjects.Robot;


/**
 * Board represented as a grid with width*height size.
 * Contains an element for each position.
 *
 * The list of cells contain the elements, while the positions-list is an immutable list for position objects.
 *
 * An empty cell is currently null.
 *
 * @param <T>
 */
public class Board<T> implements IBoard<T> {
    protected final int width;
    protected final int height;
    protected final int size;
    private ArrayList<Square<T>> map;

    public Board(int width, int height) {
        this.height = height;
        this.width = width;
        this.size = height * width;
    }

    /**
     * Creates a board representation of a TiledMap object (a loaded .tmx file)
     * ItemFactory is used to create the items that is in a square/cell,
     * based on the tile id from the .tmx file. The tile id is 1-indexed and corresponds to the
     * position it has in the tileSetLarge64.png (top left is id 1)
     * @param tiledMap
     */
    public Board(TiledMap tiledMap) {
        this.height = (int) tiledMap.getProperties().get("height");
        this.width = (int) tiledMap.getProperties().get("width");
        this.size = height * width;
        map = new ArrayList<>();


        for (int i = 0; i < height;i++) {
            for (int j = 0; j < width; j++) {
                map.add(new Square<T>(null));
            }
        }


        for (int l = 0; l < tiledMap.getLayers().size(); l++) {
            TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(l);
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    if(layer.getCell(r, c) == null) {
                        continue;
                    }
                    int id = layer.getCell(r, c).getTile().getId();
                    IItem item = createItemFromId(id);

                    if (item != null) {
                        getSquare(r, c).addElement(item);
                    }
                    System.out.println(getSquare(r,c).getListOfItems().size());
                }
            }
        }
    }

    private IItem createItemFromId(int id) {
        return ItemFactory.getItem(id);
    }


    @Override
    public void insertElement(IPosition position, T element) {
    	Square<T> sq = map.get(position.getIndex());
    	sq.addElement(element);
    }

    @Override
    public void insertElement(int x, int y, T element) {
    	int index = toIndex(x,y);
    	Square<T> sq = map.get(index);
    	sq.addElement(element);
    }

    public void insertElement(int index, T element) {
    	Square<T> sq = map.get(index);
    	sq.addElement(element);
    }

    public Square<IItem> getSquare(int x, int y) {
        Square<IItem> sq = (Square<IItem>) map.get(toIndex(x, y));
        return sq;
    }

    @Override
    public T getElement(IPosition position) {
    	Square<T> sq = map.get(position.getIndex());
    	return (T) sq.getListOfItems();
    }

    @Override
    public T getElement(int x, int y) {
    	int index = toIndex(x,y);
    	Square<T> sq = map.get(index);
    	return (T) sq.getListOfItems();
    }
    
    public Robot getRobot(IPosition position) {
    	if(!isFree(position)) {
    		Square<T> sq = map.get(position.getIndex());
    		return sq.getRobot();
    	}
    	return null;
    }

    @Override// Sjekker for robot
    public boolean isFree(IPosition position) {
    	Square<T> sq = map.get(position.getIndex());
        return !sq.occupied();
    }
    
    @Override// Sjekker for robot
    public boolean isFree(int x, int y) {
    	int index = toIndex(x,y);
    	Square<T> sq = map.get(index);
        return !sq.occupied();
    }

    @Override
    public int toIndex(int x, int y) {
        return (y * width) + x;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
    	map.clear();
    }
}
