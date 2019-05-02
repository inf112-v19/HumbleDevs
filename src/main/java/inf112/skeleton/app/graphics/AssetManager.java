package inf112.skeleton.app.graphics;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.card.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private Map<String, Texture> stringMap;
    private static Map<Integer, String> intMap;
    private static ArrayList<String> randomNames;

    public AssetManager () {
        this.stringMap = new HashMap<>();

        // robots and their colors
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

        // Replacement card when a player powers down
        //this.stringMap.put("null0", new Texture("texture/katt.jpg"));

        // Life icon
        this.stringMap.put("lifeIcon", new Texture("texture/lifeicon.png"));

        // Power down icon
        //this.stringMap.put("powerDown", new Texture("texture/katt.jpg"));

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

    public static String getTextureByIndex (int index) {
        return intMap.get(index);
    }

    public Texture getTexture (String name) {
        return stringMap.get(name);
    }

    public static String getNewRandomName() {
        if (randomNames.isEmpty()) return "NoMoreNames:(";
        int idx = (int) (Math.random() * randomNames.size());
        String name = randomNames.get(idx);
        randomNames.remove(idx);
        return name;


    }


}