package Model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CCLvLFileReaderTest {
    DataManager DM = DataManager.DaMan();

    @Test
    public void test_ReaderTest_TestLoad(){
        DM.resetDaMan();

        DM.load("Pathway", "/SuperSecretMagicLevel.dat");
        
        assertEquals( 9, DM.getScreens().size()); ///
        assert DM.getCurrentScreen() != null; ///

        String playerLoc = DM.getPlayerLoc(); //Not implemented yet (Could switch to Screen from StrID)
        int incr = 0;
        for (Screen sc : DM.getScreens()) {

            if(sc.getStrID().equals(playerLoc)) {
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
        assertEquals(14, incr); ///
    }
}
