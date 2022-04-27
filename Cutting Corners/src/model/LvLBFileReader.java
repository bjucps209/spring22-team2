package model;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LvLBFileReader {
    
    public static DummyLevel load(String filePath) throws FileNotFoundException, IOException {
        int Directions = 6;
        DummyLevel newLvL = new DummyLevel();

        try (DataInputStream reader = new DataInputStream(new FileInputStream(filePath))) {
            //Player Screen ID
            String playerScreenStrID = reader.readUTF();
            newLvL.setPlayerScrStrID(playerScreenStrID);

          
            
            //DummyScreen stuff
            int screenQuantity = reader.readInt();//Amount of screens
 
            for (int scrIdx = 0; scrIdx < screenQuantity; scrIdx++) {
                
                String screenStrID = reader.readUTF(); //Screen ID
                var scr = new DummyScreen(screenStrID);
                newLvL.addDScreen(scr);

                String backgroundPath = reader.readUTF(); //Background image path
                if (!backgroundPath.equals("null")) scr.setBackgroundImgPath(backgroundPath); //Screen Background path
                
                //Collects all adjStrID's
                for (int dir = 0; dir < Directions; dir++) {
                    String adjID = reader.readUTF();
                    if (!adjID.equals("null")) scr.setAdjacentStringID(dir, adjID);
                }

                int objQuantity = reader.readInt(); //Amount of objects

                //Object iteration
                for (int objIdx = 0; objIdx < objQuantity; objIdx++) {
                    String name = reader.readUTF();
                    String imgPath = reader.readUTF();
                    String type = reader.readUTF();
                    int tlCellY = reader.readInt();
                    int tlCellX = reader.readInt();
                    int dimenY = reader.readInt();
                    int dimenX = reader.readInt();

                    scr.addDummyObject(new DummyObject(name, imgPath, type, tlCellX, tlCellY, dimenX, dimenY));
                }

            }

            return newLvL;
        }
    }
}
