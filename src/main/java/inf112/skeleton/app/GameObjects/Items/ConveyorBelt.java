package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.Action;

public class ConveyorBelt implements IItem {

    private Direction direction;

    //Bending
    private Action rotation;
    private int speed;

    // Constructor for straight converyorbelts
    public ConveyorBelt(Direction direction, int speed) {
        this.direction = direction;
        this.speed = speed;
    }

    // Constructor for "bending" conveyorbelts
    public ConveyorBelt(Direction direction, int speed, boolean clockwise) {
        this.direction = direction;
        this.speed = speed;

        if(clockwise) rotation = Action.RIGHTTURN;
        else rotation = Action.LEFTTURN;
    }

    /**
     * Speed is a step speed multiplier, usually 1 or 2
     * @return
     */
    public int getSpeed() {
        return speed;
    }

    public Action getRotation() {
        return rotation;
    }

    public Direction getDirection() {
        return direction;
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
