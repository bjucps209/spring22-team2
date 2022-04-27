package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CCLvLFileReaderTest {

    @Test
    public void test_ReaderTest_TestSaveandLoad(){
        DataManager.DaMan().resetDaMan();

        DataManager.DaMan().attemptCreateScreen(Direction.North);
        DataManager.DaMan().createObject("Player", "imgthing.png", ObjType.Player, new Vector(2, 2), new Vector(1, 1));
        assertEquals("0,1,0", DataManager.DaMan().getPlayerScrID());

        try {
            CCLvLFileReader.save(DataManager.DaMan().getTheLevel(), "TestyTest", false);        
        } catch (Exception e) {
            fail();
        }
        try {
            DataManager.DaMan().load("TestyTest");
        } catch (Exception e) {
            fail();
        }

        assertEquals( 2, DataManager.DaMan().getScreens().size()); ///
        assert DataManager.DaMan().getCurrentScreen() != null; ///

        String playerLoc = DataManager.DaMan().getPlayerScrID(); //Not implemented yet (Could switch to Screen from StrID)
        assertEquals("0,1,0", playerLoc);
        int incr = 0;
        for (Screen sc : DataManager.DaMan().getScreens()) {

            if(sc.getStrID().equals(playerLoc)) { //change to playerLoc
                boolean testthing = false;
                for(LvLObject obj : sc.getObjects()) {
                    if (obj.getName().equals("Player")) {
                        testthing = true;
                    }
                }
                assert testthing; ///
            }

            incr += sc.getObjects().size();
        }
        assertEquals( 1, incr); ///
    }
}
