package model;

public class Swing extends Entity{
    int damage;
    int speed;
    int radius;
    int arc;

    public Swing(int startX, int startY, int damage, int speed, int radius, int arc) {
        super(startX, startY, null);
        this.damage = damage;
        this.speed = speed;
        this.radius = radius;
        this.arc = arc;
    }

    @Override
    public void performMovement(){}

    public Entity checkIfHit(){return null;}
}
