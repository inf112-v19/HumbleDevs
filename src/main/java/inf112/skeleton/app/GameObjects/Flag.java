package inf112.skeleton.app.GameObjects;

import inf112.skeleton.app.board.Direction;

public class Flag implements IItem {

    private Direction direction = Direction.NORTH;
    private int id;
    private final String NAME = "Flag";
    private boolean captured;


    public Flag(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public IItem getType() {
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public char getSymbol() {
        return 'F';
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    public Direction getRotation() {
        return direction;
    }

    public boolean captured () {
        return captured;
    }

    public void setCaptured() {
        captured = true;
    }
}