package inf112.skeleton.app.board;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;
    
    private Direction opposite;
    private Direction left;
    private Direction right;
	
	static {
		NORTH.opposite = SOUTH;
		SOUTH.opposite = NORTH;
		EAST.opposite = WEST;
		WEST.opposite = EAST;

		NORTH.left = WEST;
		SOUTH.left = EAST;
		EAST.left = NORTH;
		WEST.left = SOUTH;
		
		NORTH.right = EAST;
		SOUTH.right = WEST;
		EAST.right = SOUTH;
		WEST.right = NORTH;
	}
	/**
	 * Get the opposite direction
	 * @return the opposite direction
	 */
	public Direction getOppositeDirection() {
		return opposite;
	}

	/**
	 * Get the direction to the left
	 * @return direction to the left
	 */
	public Direction left() {
		return left;
	}

	/**
	 * Get the direction to the right
	 * @return direction to the right
	 */
	public Direction right() {
		return right;
	}

}