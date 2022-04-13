package model;

import javafx.scene.image.Image;

public class Boss extends Enemy{
    public Boss(int sides, int size, int xCoord, int yCoord, Image image, Screen homeScreen, int vision, Stats stats){
        super(sides, size, xCoord, yCoord, image, homeScreen, vision, null, stats);
    }

    public void performAttack1(){}

    public void performAttack2(){}

    public void performAttack3(){}
}
