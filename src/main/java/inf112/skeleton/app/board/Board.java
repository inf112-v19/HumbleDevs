package inf112.skeleton.app.board;

import java.util.ArrayList;
import java.util.List;

import inf112.skeleton.app.IItem;

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
    protected ArrayList<Square<T>> map;


    public Board(int width, int height, ArrayList<String> outline) {
        this.height = height;
        this.width = width;
        this.size = height * width;
        // Må lese inn instruksene til mapen som skal brukes
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

    @Override
    public T getElement(IPosition position) {
    	Square<T> sq = map.get(position.getIndex());
    	return (T) sq.getElement();
    }

    @Override
    public T getElement(int x, int y) {
    	int index = toIndex(x,y);
    	Square<T> sq = map.get(index);
    	return (T) sq.getElement();
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
