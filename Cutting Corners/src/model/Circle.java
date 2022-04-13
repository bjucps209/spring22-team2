package model;

import javafx.scene.image.Image;

public class Circle extends Boss{
    static Image image = new Image("Circle.png");

    public Circle(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 700, new Stats(10, 9, 11));
    }

    @Override
    public void performAttack1(){}

    @Override
    public void performAttack2(){}

    @Override
    public void performAttack3(){}
}

