package inf112.skeleton.app;

import java.util.Arrays;
import java.util.Random;

import inf112.skeleton.app.board.Position;

public abstract class Robot implements IRobot {

    private Direction dir;
    private Position pos;
    private int health;
    private Position backup;
    private Card[] cards;
    private int visitedFlags = 0;

    public Robot (Direction dir, int xPos, int yPos, int health){

        this.dir = dir;
        this.pos = new Position(xPos, yPos);
        this.health = health;
        this.backup = new Position(xPos, yPos);
    }
    
    public void chooseCards(Card[] pos_cards) {
    	cards = new Card[5];
    	Random rn = new Random();
    	for(int x = 0; x < 5; x++) {
    		Card s = pos_cards[rn.nextInt(9)];
    		this.cards[x] = s;
    	}
    	
    }

    // Må sjekke at det er et lovlig trekk
    @Override
    public void move(int i){
        for(int j = 0; j < i; j++){
            switch(this.dir){
                case NORTH: this.pos.moveNorth();
                break;
                case SOUTH: this.pos.moveSouth();
                break;
                case EAST: this.pos.moveEast();
                break;
                case WEST: this.pos.moveWest();
            }
        }
    }
    
    public void move(Direction dir) {
    	switch(dir) {
    	case NORTH: this.pos.moveNorth();
        break;
        case SOUTH: this.pos.moveSouth();
        break;
        case EAST: this.pos.moveEast();
        break;
        case WEST: this.pos.moveWest();
    	}
    }

    @Override
    public Direction getDirection(){
        return this.dir;
    }
    
    public Position getPosition() {
    	return pos;
    }
    
    
    public void makeBackup(Position newBackup) {
    	this.backup = newBackup;
    }

    @Override
    public void rotateLeft(){
    	switch(this.dir) {
    	case NORTH: this.dir = Direction.WEST;
        break;
        case SOUTH: this.dir = Direction.EAST;
        break;
        case EAST: this.dir = Direction.NORTH;
        break;
        case WEST: this.dir = Direction.SOUTH;
    	}
    }

    @Override
    public void rotateRight() {
    	switch(this.dir) {
    	case NORTH: this.dir = Direction.EAST;
        break;
        case SOUTH: this.dir = Direction.WEST;
        break;
        case EAST: this.dir = Direction.SOUTH;
        break;
        case WEST: this.dir = Direction.NORTH;
    	}
    }

    @Override
    public int getX(){
        return this.pos.getX();
    }

    @Override
    public int getY(){
        return this.pos.getY();
    }

    @Override
    public void takeDamage(){
        this.health--;
    }

    @Override
    public void repairHealth(){
        this.health++;
    }

    @Override
    public boolean isAlive(){
        return this.health > 0;
    }
    
    public Card[] getCards() {
    	return cards;
    }
    public void die() {
    	this.health = 0;
    }
}