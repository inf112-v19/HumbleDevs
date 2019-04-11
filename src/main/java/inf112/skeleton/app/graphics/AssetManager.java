package inf112.skeleton.app.graphics;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.card.Action;

import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private Map<String, Texture> stringMap;
    private Map<Integer, Texture> intMap;

    public AssetManager () {
        this.stringMap = new HashMap<>();

        //robots and their colors
        this.stringMap.put("darkPinkRobot", new Texture("texture/robot1.png"));
        this.stringMap.put("blueRobot", new Texture("texture/robot2.png"));
        this.stringMap.put("greenRobot", new Texture("texture/robot3.png"));
        this.stringMap.put("redRobot", new Texture("texture/robot4.png"));
        this.stringMap.put("lightPinkRobot", new Texture("texture/robot5.png"));
        this.stringMap.put("blueRobot", new Texture("texture/robot6.png"));
        this.stringMap.put("blackRobot", new Texture("texture/robot7.png"));
        this.stringMap.put("yellowRobot", new Texture("texture/robot8.png"));

        this.stringMap.put("MOVEFORWARD1", new Texture("texture/movementCards/move1.png"));
        this.stringMap.put("MOVEFORWARD2", new Texture("texture/movementCards/move2.png"));
        this.stringMap.put("MOVEFORWARD3", new Texture("texture/movementCards/move3.png"));

        this.stringMap.put("RIGHTTURN0", new Texture("texture/movementCards/rotateRight.png"));
        this.stringMap.put("LEFTTURN0", new Texture("texture/movementCards/rotateLeft.png"));
        this.stringMap.put("UTURN0", new Texture("texture/movementCards/uTurn.png"));
        this.stringMap.put("MOVEBACKWARD1", new Texture("texture/movementCards/backUp.png"));

        //Life icon
        this.stringMap.put("lifeIcon", new Texture("texture/lifeicon.png"));

        this.intMap = new HashMap<>();
        this.intMap.put(0, new Texture("texture/robot1.png"));
        this.intMap.put(1, new Texture("texture/robot2.png"));
        this.intMap.put(2, new Texture("texture/robot3.png"));
        this.intMap.put(3, new Texture("texture/robot4.png"));
        this.intMap.put(4, new Texture("texture/robot5.png"));
        this.intMap.put(5, new Texture("texture/robot6.png"));
        this.intMap.put(6, new Texture("texture/robot7.png"));
        this.intMap.put(7, new Texture("texture/robot8.png"));



    }

    public Texture getTextureByIndex (int index) {
        return intMap.get(index);
    }

    public Texture getTexture (String name) {
        return stringMap.get(name);
    }


}