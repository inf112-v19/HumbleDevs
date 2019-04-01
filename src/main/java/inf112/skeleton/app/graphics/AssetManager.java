package inf112.skeleton.app.graphics;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private Map<String, Texture> map;

    public AssetManager () {
        this.map = new HashMap<>();
        this.map.put("darkPinkRobot", new Texture("texture/robot1.png"));
        this.map.put("blueRobot", new Texture("texture/robot2.png"));
        this.map.put("greenRobot", new Texture("texture/robot3.png"));
        this.map.put("redRobot", new Texture("texture/robot4.png"));
        this.map.put("lightPinkRobot", new Texture("texture/robot5.png"));
        this.map.put("blueRobot", new Texture("texture/robot6.png"));
        this.map.put("blackRobot", new Texture("texture/robot7.png"));
        this.map.put("yellowRobot", new Texture("texture/robot8.png"));



        this.map.put("robot", new Texture("texture/robot.png"));
        this.map.put("testCards", new Texture("texture/test.jpg"));
    }

    public void addTexture(String name, Texture texture) {
        if (!map.containsKey(name)) {
            map.put(name, texture);
        }
    }

    public Texture getTexture (String name) {
        return map.get(name);
    }


}
