package model;

public class Enemy extends Entity{
    EntityObserver observer;
    int sides;
    int size;
    Coordinates coords;
    Enemy type;

    public Enemy(int sides, int size){
        this.size = size;
        this.sides = sides;
        //this.coords = new Corrdinates(WIDTH, HEIGHT);
    }

    public void generateEnemy(){
        type = new Triangle(size);
        // switch (sides) {
        //     case 3:{type = new Triangle(size);}
        //     default: break;
        // }
    }
}
