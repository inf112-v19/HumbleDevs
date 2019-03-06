package inf112.skeleton.app.gameObjects;

public class Items implements IItem {
    int id;

    public Items(int id) {
        this.id = id;
        createItem(id);
    }

    public Items() {

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void createItem(int id) {
        switch(id) {
            case 0:
                //nothing
            case 1:
                Floor floor = new Floor();
        }
    }
}
