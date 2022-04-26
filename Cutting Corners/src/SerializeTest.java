import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

import model.*;

public class SerializeTest {



    @Test
    public void testSave_savesToFile() throws IOException  {
        World.reset();
        World.instance();
        assertEquals(0, World.instance().currentLevel);
        World.instance().currentLevel = 0;
        World.instance().setDifficulty(6);
        World.instance().getCurrentLevel().setCurrentLevel(5);
        // World.instance().getPlayer().getStats().setHealth(5);

        World.instance().save("savegame.dat");

        try (DataInputStream reader = new DataInputStream(new FileInputStream("savegame.dat"))) {
            assertEquals(0, reader.readInt()); //currentLevel
            assertEquals(6, reader.readInt()); //difficulty
            assertEquals(5, reader.readInt()); //this level
        } catch (IOException e) {
            fail();
        }


    }

    @Test
    public void testLoad_loadsFromFile() {
        World.reset();
        World.instance();
        assertEquals(0, World.instance().currentLevel);
        World.instance().currentLevel = 0;
        World.instance().setDifficulty(6);
        World.instance().getCurrentLevel().setCurrentLevel(5);
        World.instance().getPlayer().getStats().setHealth(5);
        World.instance().getPlayer().setWeaponImage("FakeTestImage");

        try {
            World.instance().save("savegame.dat");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        World.instance().setDifficulty(1);
        World.instance().getPlayer().setStats(new Stats(1, 1, 1));
        World.instance().setCurrentLevel(1);


        try {
            World.instance().load("savegame.dat");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        assertEquals(0, World.instance().currentLevel);
        assertEquals(6, World.instance().getDifficulty());
        assertEquals(5, World.instance().getCurrentLevel().getCurrentLevel());
        assertEquals(1, World.instance().getPlayer().getStats().getHealth());

    }
}
