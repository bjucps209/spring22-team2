package model;

import javafx.scene.image.Image;

public class Cube extends Boss{
    static Image image = new Image("Cube.png");

    public Cube(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 700, new Stats(10, 9, 11));
    }

    @Override
    public void performAttack1(){}

    @Override
    public void performAttack2(){}

    @Override
    public void performAttack3(){}
}

