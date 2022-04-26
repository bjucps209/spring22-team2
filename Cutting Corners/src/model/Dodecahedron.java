package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Dodecahedron extends Boss{
    static String image = "Dodecahedron.png";

    public Dodecahedron(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 700, new Stats(10, 9, 11), 11);
    }

    @Override
    public void performAttack1(){}

    @Override
    public void performAttack2(){}

    @Override
    public void performAttack3(){}




    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Dodecahedron");
        file.writeInt(getSize());
        file.writeInt(getX());
        file.writeInt(getY());
    }

    public static Dodecahedron deserialize(DataInputStream file, Screen homeScreen) throws IOException {
        int size = file.readInt();
        int x = file.readInt();
        int y = file.readInt();

        Dodecahedron t = new Dodecahedron(size, x, y, homeScreen);
        return t;
    }
}

