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
        assertEquals(0, World.instance().currentLevel);
        World.instance().currentLevel = 1;
        World.instance().setDifficulty(1);

        World.instance().save("savegame.dat");
        
        try (DataInputStream reader = new DataInputStream(new FileInputStream("SaveGame.dat"))) {
            assertEquals(1, reader.readInt()); //currentLevel
            assertEquals(1, reader.readInt()); //difficulty
        } catch (IOException e) {
            fail();
        }


    }

    @Test
    public void testLoad_loadsFromFile() {
        // assertEquals(0, World.instance().currentLevel);
        // World.instance().load("SaveGame.dat");
        // assertEquals(currentLevel.getEntities().size(), 1);
        // assertEquals(World.instance().getPlayer().getHealth(), 5);

    }
}
