package model;

public class Dodecahedron extends Boss{
    static String image = "Dodecahedron.png";

    public Dodecahedron(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 700, new Stats(10, 9, 11), 11, 200);
    }

    @Override
    public void performAttack1(){}

    @Override
    public void performAttack2(){}

    @Override
    public void performAttack3(){}
}

