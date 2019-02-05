package inf112.skeleton.app;

public interface IRobot {

    void move(int i);

    Direction getDirection();

    void rotate();

    int getX();

    int getY();

    void makeDamage();

    void repairHealth();

    boolean isAlive();



}
