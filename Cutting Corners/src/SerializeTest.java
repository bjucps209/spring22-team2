import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

import model.*;

public class SerializeTest {



    @Test
    public void testSave_savesToFile()  {
        assertEquals(100, World.instance().getPlayer().getCoords().getxCoord());
        Player extraPlayer = new Player(50, 50);
        World.instance().getCurrentLevel().placeEntity(0, 1, extraPlayer);
        World.instance().setCurrentLevel(1);
        // World.reset();

        World.instance().save("SaveGame.dat");
        
        try (DataInputStream reader = new DataInputStream(new FileInputStream("SaveGame.dat"))) {
            assertEquals(World.instance().getCurrentLevel(), reader.readInt());
        } catch (IOException e) {
            fail();
        }

    }

    @Test
    public void testLoad_loadsFromFile() {
        // World.reset();
        // Level currentLevel = World.instance().getCurrentLevel();
        // assertEquals(currentLevel.getEntities().size(), 0);
        // World.instance().load("SaveGame.dat");
        // assertEquals(currentLevel.getEntities().size(), 1);
        // assertEquals(World.instance().getPlayer().getHealth(), 5);

    }
}
