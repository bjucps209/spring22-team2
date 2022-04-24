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
        World.instance().setDifficulty(0);
        World.instance().getPlayer().getStats().setHealth(5);

        World.instance().save("savegame.dat");
        World.instance().currentLevel = 1;

        try (DataInputStream reader = new DataInputStream(new FileInputStream("SaveGame.dat"))) {
            assertEquals(0, reader.readInt()); //currentLevel
            assertEquals(0, reader.readInt()); //difficulty
        } catch (IOException e) {
            fail();
        }


    }

    @Test
    public void testLoad_loadsFromFile() {
        World.reset();
        World.instance();
        assertEquals(0, World.instance().currentLevel);

        try {
            World.instance().load("savegame.dat");
        } catch (IOException e) {
            fail();
        }
        assertEquals(0, World.instance().currentLevel);
        // assertEquals(World.instance().getPlayer().getStats().getHealth(), 5);

    }
}
