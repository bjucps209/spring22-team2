package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CCLvLFileReaderTest {
    DataManager DM = DataManager.DaMan();

    @Test
    public void test_ReaderTest_TestSaveandLoad(){
        DM.resetDaMan();

        DM.attemptCreateScreen(Direction.North);
        DM.createObject("Player", "imgthing.png", ObjType.Player, new Vector(2, 2), new Vector(1, 1));

        try {
            CCLvLFileReader.save(DM.getTheLevel(), "TestyTest", false);        
        } catch (Exception e) {
            fail();
        }
        try {
            DM.load("TestyTest");
        } catch (Exception e) {
            fail();
        }

        assertEquals( 2, DM.getScreens().size()); ///
        assert DM.getCurrentScreen() != null; ///

        String playerLoc = DM.getPlayerScrID(); //Not implemented yet (Could switch to Screen from StrID)
        assertEquals("0,1,0", playerLoc);
        int incr = 0;
        for (Screen sc : DM.getScreens()) {

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
        assertEquals( 2, incr); ///
    }
}
