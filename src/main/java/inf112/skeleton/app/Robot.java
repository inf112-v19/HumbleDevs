package inf112.skeleton.app;

public class Robot implements IRobot {

    private Direction dir;
    private int xPos;
    private int yPos;
    private int health;
    private int getLife;

    public Robot (Direction dir, int xPos, int yPos, int health, int getLife){

        this.dir = dir;
        this.xPos = xPos;
        this.yPos = yPos;
        this.health=health;
        this.getLife=getLife;

    }


    @Override
    public void move(int i){
        for(int j = 0; j < i; j++){
            switch(this.dir){
                case NORTH: this.yPos--;
                break;
                case SOUTH: this.yPos++;
                break;
                case EAST: this.xPos++;
                break;
                case WEST: this.xPos--;

            }
        }
    }


    @Override
    public Direction getDirection(){
        return this.dir;
    }

    @Override
    public void rotate(){



    }

    @Override
    public int getX(){
        return this.xPos;
    }

    @Override
    public int getY(){
        return this.yPos;
    }

    @Override
    public void makeDamage(){
        this.health--;
    }

    @Override
    public void repairHealth(){
        this.health=getLife;
    }

    @Override
    public boolean isAlive(){
        return this.health > 0;
    }



}
