package model;

public class Triangle extends Enemy{
    Equipment weapon;
    EntityObserver observer;
    Stats stats;
    Coordinates coords;

    public Triangle(int size){
        super(3, size);
        sizeToStats();
    }

    public void sizeToStats(){stats = null;}
}
