package model;

public class PlayerRelation {
    int xDifference;
    int yDifference;
    double distance;
    Player player;

    public PlayerRelation(int xDifference, int yDifference, double distance, Player player) {
        this.xDifference = xDifference;
        this.yDifference = yDifference;
        this.distance = distance;
        this.player = player;
    }
   
}
