package inf112.skeleton.app.gameObjects.Items;

import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.Action;

public class ConveyorBelt implements IItem {

    private Direction direction;
    private String name;
    private Action rotation;
    private int speed;

    // Constructor for straight conveyorbelts
    public ConveyorBelt(Direction direction, int speed) {
        this.direction = direction;
        this.speed = speed;
        this.name = "ConveyorBelt";
    }

    // Constructor for "bending" conveyorbelts
    public ConveyorBelt(Direction direction, int speed, boolean clockwise) {
        this.direction = direction;
        this.speed = speed;
        this.name = "BendingConveyorBelt";

        if(clockwise) rotation = Action.RIGHTTURN;
        else rotation = Action.LEFTTURN;
    }

    /**
     * Speed is a step speed multiplier, usually 1 or 2
     * @return the step speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Get the rotation of the conveyor belt
     * @return the rotation, RIGHTTURN or LEFTTURN
     */
    public Action getRotation() {
        return rotation;
    }

    /**
     * Get the direction that the conveyor belt points
     * @return direction of the conveyor belt
     */
    public Direction getDirection() {
        return direction;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
