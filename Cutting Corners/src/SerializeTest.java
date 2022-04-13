import static org.junit.Assert.assertEquals;

import java.io.DataInputStream;
import java.io.FileInputStream;

import org.junit.Test;

import model.*;

public class SerializeTest {


    @Test
    void testSave_savesToFile()  {
        
        World.reset();
        World.instance().setCurrentLevel(1);
        World.instance().setDifficulty(2);
        Player player = new Player(1, 2);
        player.setHealth(5);
        World.instance().getCampaign().add(new Level(1));
        World.instance().getCurrentLevel().placeEntity(1, 2, player);
        World.instance().save("SaveGame.dat");
        
        try (DataInputStream reader = new DataInputStream(new FileInputStream("SaveGame.dat"))) {
            assertEquals(World.instance().getCurrentLevel(), reader.readInt());
            assertEquals(World.instance().getDifficultyLevel(), reader.readInt());
            assertEquals(World.instance().getPlayer().getHealth(), reader.readInt());
        }

    }

    @Test
    void testLoad_loadsFromFile() {
        World.reset();
        Level currentLevel = World.instance().getCurrentLevel();
        assertEquals(currentLevel.getEntities().size(), 0);
        World.instance().load("SaveGame.dat");
        assertEquals(currentLevel.getEntities().size(), 2);

    }
}
