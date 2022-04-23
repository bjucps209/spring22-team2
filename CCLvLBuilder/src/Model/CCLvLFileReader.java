//-----------------------------------------------------------
//File:   blahblah.java
//Desc:   File Reader to optionally be used in 
//        both programs
//----------------------------------------------------------- 
package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CCLvLFileReader {
    public static final String pathWay = "src/levels/";


    public CCLvLFileReader(){}

    public static String save(Level savelvl, String fileName, boolean saveCurrentWork) throws FileNotFoundException, IOException {
        fileName = pathWay + cleanFileIdentifier(fileName);
        var playerscn = savelvl.getScreenWithPlayer();
        if (playerscn == null && !saveCurrentWork) return "Player Obj required to save";

        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(fileName))) {

            writer.writeUTF(savelvl.getScreenWithPlayer()); // Player Scr

            //writer.writeUTF("scr");

            var screens = savelvl.getScreens(); //Screens
            writer.writeInt(screens.size()); //Amount of screens
            for (int scrIdx = 0; scrIdx < screens.size(); scrIdx++) {
                var scr = screens.get(scrIdx);
                
                writer.writeUTF(scr.getStrID()); //Screen identification

                if (scr.getBackgroundPathName() == null) writer.writeUTF("null");
                else writer.writeUTF(scr.getBackgroundPathName()); //Background Path

                for (Screen adjScr : scr.getAdjacentScreens()) {
                    if (adjScr == null) writer.writeUTF("null");  // Adjacent Screen ID's
                    else writer.writeUTF(adjScr.getStrID());
                }

                var objects = scr.getObjects();
                writer.writeInt(objects.size()); //Amount of objects
                for (int objIdx = 0; objIdx < objects.size(); objIdx++) {
                    var obj = objects.get(objIdx);
                    
                    writer.writeUTF(obj.getName());
                    writer.writeUTF(obj.getImgPath());
                    writer.writeUTF(String.valueOf(obj.getObjType()));
                    writer.writeInt(obj.getTopLeftCell().getY());
                    writer.writeInt(obj.getTopLeftCell().getX());
                    writer.writeInt(obj.getDimensions().getY());
                    writer.writeInt(obj.getDimensions().getX());

                }

            }
            return fileName + " saved";

        }


    }

    //Do not use the FileNotFound with this for anything
    public static String load(Level lvlRef, String fileName) throws FileNotFoundException, IOException {
        fileName = pathWay + cleanFileIdentifier(fileName);
        int Directions = 6;
        lvlRef = new Level();

        try (DataInputStream reader = new DataInputStream(new FileInputStream(fileName))) {
            reader.readUTF();

            var screenQuantity = reader.readInt(); //Amount of screens
            String[][] adjStrIDCollection = new String[screenQuantity][Directions];
            for (int scrIdx = 0; scrIdx < screenQuantity; scrIdx++) {
                
                int[] curScrSeries = breakUpStrID(reader.readUTF()); //Screen ID
                var scr = new Screen(curScrSeries[0], curScrSeries[1], curScrSeries[2], DataManager.gridDimensions);
                lvlRef.addScreen(scr);

                scr.setBackgroundPathName(reader.readUTF()); //Screen Background path

                for (int dir = 0; dir < Directions; dir++) {//collects all adjStrID's for later 
                    adjStrIDCollection[scrIdx][dir] = reader.readUTF();
                }

                int objQuantity = reader.readInt(); //Amount of objects
                for (int objIdx = 0; objIdx < objQuantity; objIdx++) {
                    String name = reader.readUTF();
                    String imgPath = reader.readUTF();
                    ObjType type = ObjType.valueOf(reader.readUTF());
                    int tlCellY = reader.readInt();
                    int tlCellX = reader.readInt();
                    int dimenY = reader.readInt();
                    int dimenX = reader.readInt();

                    scr.createObject(name, imgPath, type, new Vector(tlCellY, tlCellX), new Vector(dimenY, dimenX));
                }

            }

            for (int scrAdjStrs = 0; scrAdjStrs < adjStrIDCollection.length; scrAdjStrs++) {
                Screen scr2 = lvlRef.getScreens().get(scrAdjStrs);
                for (int adjScrDir = 0; adjScrDir < Directions; adjScrDir++) {
                    scr2.setAdjacentScreen(Direction.intToDir(adjScrDir), lvlRef.findScreen(adjStrIDCollection[scrAdjStrs][adjScrDir])); //Should handle null
                }
            }


            return lvlRef + " loaded";
        } catch (FileNotFoundException e) {
            return "File does not exist";
        }
    }
    
    public static String cleanFileIdentifier(String givenFileName) {
        String[] stuff = givenFileName.split(".");
        if (stuff.length == 0) return givenFileName + ".dat";
        return stuff[0] + ".dat";
    }

    public static int[] breakUpStrID(String strid) {
        String[] blah = strid.split(",");
        return new int[] {Integer.parseInt(blah[0]), Integer.parseInt(blah[1]), Integer.parseInt(blah[2])};
    }
}
