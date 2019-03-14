package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.board.Direction;

public class ConveyorBelt implements IItem {

    private Direction direction;

    //Bending
    private boolean clockwise;
    private int speed;

    // Constructor for straight converyorbelts
    public ConveyorBelt(Direction direction, int speed) {
        this.direction = direction;
        this.speed = speed;
    }

    // Constructor for "bending" conveyorbelts
    public ConveyorBelt(Direction direction, int speed, boolean clockwise) {
        this.direction = direction;
        this.clockwise = clockwise;
        this.speed = speed;
    }

    public boolean isClockwise() {
        return clockwise;
    }

    public void setClockwise(boolean clockwise) {
        this.clockwise = clockwise;
    }

    public int isDoubleSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
     
    @Override
    public int tileId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public char getSymbol() {
        return 0;
    }
}
