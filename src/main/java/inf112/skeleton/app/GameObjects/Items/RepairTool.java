package inf112.skeleton.app.GameObjects.Items;

public class RepairTool implements IItem {



    //Only wrench
    //Removes one damage token
    public RepairTool() {

    }

    //Wrench and hammer
    //Removes one damage token and recieves an option card
//    public RepairTool() {
//
//    }




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
