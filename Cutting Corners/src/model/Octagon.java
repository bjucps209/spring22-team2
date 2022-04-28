//--------------------------------
// Octagon.java
// One of the many enemies
// Carries a sword and does high damage
//----------------------------------
package model;

import javafx.util.Duration;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Octagon extends Enemy {
    static Equipment weapon = new MeleeWeapon("Basic Sword", 1, 1, 0, 0, 150, "media/Player/swordwalk.gif");
    static String image = "media/Enemies/octagon.png";
    static String walking = "media/Enemies/octagonwalk.gif";
    static String attacking = "media/Enemies/octagonattack.gif";

    public Octagon(int size, int xCoord, int yCoord, Screen homeScreen) {
        super(3, size, xCoord, yCoord, image, homeScreen, 600, weapon, sizeToStats(size), walking, attacking, size * 3,
                size * 8, 30);
    }

    // translates the @size to Stats @speed, @strength, and @health, through three
    // separate formulae
    public static Stats sizeToStats(int size) {

        // int speed = (int) 30 / size;
        int speed = 5;
        int strength = (int) (size / 1.5);
        int health = (int) size * 3;
        return new Stats(strength, speed, health);
    }

    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Octagon");
        file.writeInt(getSize());
        file.writeInt(getX());
        file.writeInt(getY());
    }

    public static Octagon deserialize(DataInputStream file, Screen homeScreen) throws IOException {
        int size = file.readInt();
        int x = file.readInt();
        int y = file.readInt();

        Octagon t = new Octagon(size, x, y, homeScreen);
        return t;
    }

    // resets the player and gives @experience/@score to the player
    @Override
    public void performDie() {

        if (!World.instance().getCamapign() && World.instance().getCurrentLevel().getCurrentLevel() != 0) {
            World.instance().setCurrentLevel(-1);
            World.instance().passLevel();
            World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
        }
        super.performDie();
    }
}
