//--------------------------------
// Hexagon.java
// Child class of enemy, one of the many
// enemies the player must face
//----------------------------------
package model;

import javafx.util.Duration;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Hexagon extends Enemy {
    static Equipment weapon = new MeleeWeapon("Magic Wand", 1, 1, 0, 0, 500, "media/Player/swordwalk.gif");
    static String image = "media/Enemies/hexagon.png";
    static String walking = "media/Enemies/hexagonwalk.gif";
    static String attacking = "media/Enemies/hexagonattack.gif";

    public Hexagon(int size, int xCoord, int yCoord, Screen homeScreen) {
        super(3, size, xCoord, yCoord, image, homeScreen, 600, weapon, sizeToStats(size), walking, attacking, size * 2,
                size * 6, 20);
    }

    // creates a Projectile object, @fireball, and adds it to the @currentScreen
    @Override
    public void performAttack() {
        if (World.instance().getPlayer() != null) {
            super.getObserver().changeImage(attacking, Direction.left);
            Projectile fireball = new Projectile(super.getStats().getStrength(), 5, super.getX(), super.getY(), 500,
                    "media/Player/fireball.png", 2, World.instance().getPlayer().getX()+100,
                    World.instance().getPlayer().getY()+100, 2);
            World.instance().getCurrentLevel().getCurrentScreen().addEntity(fireball);
            World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
            super.setAttackCount(150);
        }
    }

    // translates the @size to Stats @speed, @strength, and @health, through three
    // separate formulae
    public static Stats sizeToStats(int size) {

        // int speed = (int) 30 / size;
        int speed = 5;
        int strength = (int) size / 2;
        int health = (int) size * 2;
        return new Stats(strength, speed, health);
    }

    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Hexagon");
        file.writeInt(getSize());
        file.writeInt(getX());
        file.writeInt(getY());
    }

    public static Hexagon deserialize(DataInputStream file, Screen homeScreen) throws IOException {
        int size = file.readInt();
        int x = file.readInt();
        int y = file.readInt();

        Hexagon t = new Hexagon(size, x, y, homeScreen);
        return t;
    }
}
