package inf112.skeleton.app.GameObjects;
import inf112.skeleton.app.board.Direction;

import java.util.Random;

import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.card.ProgramCard;

/**
 * The class that represents a robot. It's abstract because this makes it easier to make a robot
 * that is controlled by the computer.
 *
 * Note to assignment 3:
 * 		- The chooseCards() - method is just to make the robot get som cards when we are testing the
 * 		  other methods. We may want to have this implentation for the "stupid" version of the robots
 * 		  that are controlled by the computer.
 * @author Even Kolsgaard
 *
 */
public abstract class Robot implements IRobot {

    private Direction dir;
    private Position pos;
    private int lifeTokens;
    private Position backup;
    private ProgramCard[] cards;
    private int visitedFlags = 0;
    private int damageTokens;
    private boolean destroyed;
    private boolean poweredDown;

    public Robot (Direction dir, int xPos, int yPos){
        this.dir = dir;
        this.pos = new Position(xPos, yPos);
        this.lifeTokens = 3;
        this.backup = new Position(xPos, yPos);
        this.damageTokens = 0;
        this.poweredDown = false;
    }

    public void chooseCards(ProgramCard[] pos_cards) {
        cards = new ProgramCard[5];
        Random rn = new Random();
        for(int x = 0; x < 5; x++) {
            ProgramCard s = pos_cards[rn.nextInt(pos_cards.length)];
            this.cards[x] = s;
        }
    }

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
    @Override
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

    @Override
    public Position getPosition() {
        return pos;
    }

    @Override
    public void makeBackup(Position newBackup) {
        this.backup = newBackup;
    }

    @Override
    public void rotateLeft(){
        this.dir = dir.left();
    }

    @Override
    public void rotateRight() {
        this.dir = dir.right();
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
        this.damageTokens++;
        if(this.damageTokens == 10) {
            this.die();
            damageTokens = 0;
        }
    }

    @Override
    public void repairDamage(){
        this.damageTokens--;
    }

    @Override
    public boolean gameOver(){
        return !(this.lifeTokens > 0);
    }
    @Override
    public ProgramCard[] getCards() {
        return cards;
    }
    @Override
    public void die() {
        this.destroyed = true;
        this.lifeTokens--;
    }
    @Override
    public void visitFlag() {
        this.visitedFlags++;
        makeBackup(this.pos);
    }
    @Override
    public void respawn() {
        this.destroyed = false;
        this.pos = backup;
    }
    @Override
    public int visitedFlags() {
        return this.visitedFlags;
    }
    @Override
    public void powerDown() {
        this.damageTokens = 0;
        this.poweredDown = true;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

}