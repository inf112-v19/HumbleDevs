package inf112.skeleton.app.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to easily manage and retrieve assets
 */

public class AssetManager {
    private static Map<String, Texture> textureMap;
    private static Map<Integer, String> intMap;
    private static Map<String, String> tmxMap;
    private static ArrayList<String> randomNames;

    public AssetManager () {
        this.textureMap = new HashMap<>();

        // robots and their colors
        this.textureMap.put("darkPinkRobot", new Texture("texture/robot1.png"));
        this.textureMap.put("blueRobot", new Texture("texture/robot2.png"));
        this.textureMap.put("greenRobot", new Texture("texture/robot3.png"));
        this.textureMap.put("redRobot", new Texture("texture/robot4.png"));
        this.textureMap.put("lightPinkRobot", new Texture("texture/robot5.png"));
        this.textureMap.put("blueRobot", new Texture("texture/robot6.png"));
        this.textureMap.put("blackRobot", new Texture("texture/robot7.png"));
        this.textureMap.put("yellowRobot", new Texture("texture/robot8.png"));

        this.textureMap.put("MOVEFORWARD1", new Texture("texture/movementCards/move1.png"));
        this.textureMap.put("MOVEFORWARD2", new Texture("texture/movementCards/move2.png"));
        this.textureMap.put("MOVEFORWARD3", new Texture("texture/movementCards/move3.png"));

        this.textureMap.put("RIGHTTURN0", new Texture("texture/movementCards/rotateRight.png"));
        this.textureMap.put("LEFTTURN0", new Texture("texture/movementCards/rotateLeft.png"));
        this.textureMap.put("UTURN0", new Texture("texture/movementCards/uTurn.png"));
        this.textureMap.put("MOVEBACKWARD1", new Texture("texture/movementCards/backUp.png"));

        // Map thumbnails
        this.textureMap.put("level1", new Texture(Gdx.files.internal("texture/thumbnails/level1.png")));
        this.textureMap.put("level2", new Texture(Gdx.files.internal("texture/thumbnails/level2.png")));
        this.textureMap.put("level3", new Texture(Gdx.files.internal("texture/thumbnails/level3.png")));

        // Tmx paths
        this.tmxMap = new HashMap<>();

        this.tmxMap.put("level1", "assets/maps/level1.tmx");
        this.tmxMap.put("level2", "assets/maps/level2.tmx");
        this.tmxMap.put("level3", "assets/maps/level3.tmx");

        // Life icon
        this.textureMap.put("lifeIcon", new Texture("texture/lifeicon.png"));


        this.intMap = new HashMap<>();
        this.intMap.put(0, "texture/robot1.png");
        this.intMap.put(1, "texture/robot2.png");
        this.intMap.put(2, "texture/robot3.png");
        this.intMap.put(3, "texture/robot4.png");
        this.intMap.put(4, "texture/robot5.png");
        this.intMap.put(5, "texture/robot6.png");
        this.intMap.put(6, "texture/robot7.png");
        this.intMap.put(7, "texture/robot8.png");
        this.randomNames = new ArrayList<>();
        this.randomNames.add("Feisty");
        this.randomNames.add("Dorky");
        this.randomNames.add("Triana");
        this.randomNames.add("Reidu");
        this.randomNames.add("Jupill");
        this.randomNames.add("Tutti");
        this.randomNames.add("Frutti");
        this.randomNames.add("Geir");
        this.randomNames.add("Leet");
        this.randomNames.add("M&M");
        this.randomNames.add("8008");
        this.randomNames.add("Mann1");
        this.randomNames.add("Dam1");
        this.randomNames.add("Trump");
        this.randomNames.add("Ivanka");
        this.randomNames.add("M&M");
        this.randomNames.add(":--)");
        this.randomNames.add("iCAN");
        this.randomNames.add("Tine");
        this.randomNames.add("Tina");
        this.randomNames.add("Tinø");
        this.randomNames.add("Tino");
        this.randomNames.add("Dino");
        this.randomNames.add("EttEtt");
        this.randomNames.add("Fyra");
        this.randomNames.add("Erna");
        this.randomNames.add("Jeens");
        this.randomNames.add("Joachim");
        this.randomNames.add("Even");
        this.randomNames.add("Linh");
        this.randomNames.add("Amalie");
        this.randomNames.add("Bendor");
        this.randomNames.add("Fyra");
        this.randomNames.add("Justin");
        this.randomNames.add("GGlads");
        this.randomNames.add("u-hu");
        this.randomNames.add("Adam");
        this.randomNames.add("Eve");
        this.randomNames.add("Ripped");
        this.randomNames.add("Stank");
        this.randomNames.add("Tshawe");
        this.randomNames.add("Bonaqua");
        this.randomNames.add("Dogdog");

    }

    /**
     * @param index
     * @return the path to the index specified
     */
    public static String getTextureByIndex (int index) {
        return intMap.get(index);
    }

    /**
     *
     * @param name
     * @return the Texture from a given string
     */
    public static Texture getTexture (String name) {
        return textureMap.get(name);
    }

    /**
     *
     * @param name
     * @return the path to a given Tiled-map
     */
    public static String getTmxMap (String name) {
        return tmxMap.get(name);
    }

    /**
     *
     * @return a random name (String) to be used by an AI
     */
    public static String getNewRandomName() {
        if (randomNames.isEmpty()) return "NoMoreNames:(";
        int idx = (int) (Math.random() * randomNames.size());
        String name = randomNames.get(idx);
        randomNames.remove(idx);
        return name;
    }
}