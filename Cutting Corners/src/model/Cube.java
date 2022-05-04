//--------------------------------
// Cube.java
// One of the many bosses
//----------------------------------
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Cube extends Boss {
    static String image = "media/Enemies/cube.png";
    private EnemyState state = EnemyState.patrolling;
    private int attackCount = 150;
    private int currentAttack = 0;

    public Cube(int size, int xCoord, int yCoord, Screen homeScreen) {
        super(3, size, xCoord, yCoord, image, homeScreen, 700,
                new Stats(10, 9, 15), 15,
                150);
    }

    // state machine to determine the actions of the boss, whether they be resting
    // after attacking, attacking using performAttack1() 2() or 3(), or stunned
    // after a player attack
    public void performMovement() {
        if (attackCount == 149) {
            state = EnemyState.patrolling;
            currentAttack = 0;
            super.getObserver().changeImage(image, Direction.left);
        }
        attackCount--;
        switch (state) {
            case patrolling:

                if (attackCount <= 0) {
                    state = EnemyState.attacking;
                }
                break;
            case attacking:
                if (World.instance().getPlayer() != null) {
                    switch ((int) (Math.random() * 3) + 1) {
                        case 1:
                            performAttack1();
                            super.getObserver().changeImage("media/enemies/cubeexpand2.gif", Direction.left);
                            attackCount = 225;
                            state = EnemyState.stunned;
                            currentAttack = 1;
                            break;
                        case 2:
                            performAttack2();
                            super.getObserver().changeImage("media/enemies/cubeslam2.gif", Direction.left);
                            attackCount = 225;
                            state = EnemyState.stunned;
                            currentAttack = 2;
                            break;
                        case 3:
                            performAttack3();
                            super.getObserver().changeImage("media/enemies/cubeslide2.gif", Direction.left);
                            state = EnemyState.stunned;
                            attackCount = 225;
                            currentAttack = 3;
                            break;
                    }
                }
                break;
            case stunned:
                switch (currentAttack) {
                    case 1:
                        performAttack1();
                        break;
                    case 2:
                        performAttack2();
                        break;
                    case 3:
                        performAttack3();
                        break;
                }
                break;
        }
    }

    // randomly selected by perform movement
    // calls the player's takeDamage() method if he/she is on the right side of the
    // screen
    @Override
    public void performAttack1() {
        if (World.instance().getPlayer() != null) {
            if (attackCount > 150 && attackCount < 170) {
                if (World.instance().getPlayer().getCoords().getxCoord() > 400
                        && World.instance().getPlayer().getCoords().getxCoord() < 750) {
                    World.instance().getPlayer().takeDamage(1 * World.instance().getDifficulty(), Direction.left);
                }
            }
        }
    }

    // randomly selected by perform movement
    // calls the player's takeDamage() method if he/she is on the right side of the
    // screen
    @Override
    public void performAttack2() {
        if (World.instance().getPlayer() != null) {
            if (attackCount == 150) {
                if (World.instance().getPlayer().getCoords().getxCoord() > 500
                        && World.instance().getPlayer().getCoords().getxCoord() < 750) {
                    World.instance().getPlayer().takeDamage(5 * World.instance().getDifficulty(), Direction.left);
                }
            }
        }
    }

    // randomly selected by perform movement
    // calls the player's takeDamage() method if he/she is within certain orbitals
    // of the boss
    @Override
    public void performAttack3() {
        if (World.instance().getPlayer().getCoords().getyCoord() < 700) {
            if (World.instance().getPlayer() != null) {
                if (attackCount > 195) {
                    double temp = attackCount - 195;
                    double divide = temp / 30;
                    double percent = divide * 640;
                    double pos = World.instance().getPlayer().getCoords().getxCoord();
                    double distance = Math.abs(pos - percent);
                    if (distance < 10) {
                        World.instance().getPlayer().takeDamage(1 * World.instance().getDifficulty(), Direction.up);
                    }
                }
                if (attackCount < 195 && attackCount > 170) {
                    double temp = attackCount - 170;
                    double divide = temp / 25;
                    double percent = divide * 1280;
                    double dispalce = 1280 - percent;
                    double pos = World.instance().getPlayer().getCoords().getxCoord();
                    double distance = Math.abs(pos - dispalce);
                    if (distance < 10) {
                        World.instance().getPlayer().takeDamage(1 * World.instance().getDifficulty(), Direction.up);
                    }
                }
                if (attackCount < 170 && attackCount > 150) {
                    double temp = attackCount - 150;
                    double divide = temp / 20;
                    double percent = 2 * divide * 1280;
                    double pos = World.instance().getPlayer().getCoords().getxCoord();
                    double distance = Math.abs(pos - percent);
                    if (distance < 10) {
                        World.instance().getPlayer().takeDamage(1 * World.instance().getDifficulty(), Direction.up);
                    }
                }
            }
        }
    }

    @Override
    public EnemyState getState() {
        return state;
    }

    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Cube");
        file.writeInt(getSize());
        file.writeInt(getX());
        file.writeInt(getY());
    }

    public static Cube deserialize(DataInputStream file, Screen homeScreen) throws IOException {
        int size = file.readInt();
        int x = file.readInt();
        int y = file.readInt();

        Cube t = new Cube(size, x, y, homeScreen);
        return t;
    }
}
