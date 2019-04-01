package inf112.skeleton.app.graphics;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private Map<String, Texture> map;

    public AssetManager () {
        this.map = new HashMap<>();
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
