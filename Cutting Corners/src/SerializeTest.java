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
        World.instance().getCurrentLevel().getTotal
        assertEquals(World.instance().displayCurrentEntities().size(), 7);
        Player extraPlayer = new Player(50, 50);
        World.instance().getCurrentLevel().placeEntity(0, 1, extraPlayer);
        
        // World.reset();
        // World.instance().setCurrentLevel(1);
        // World.instance().setDifficulty(2);
        // Player player = new Player(1, 2);
        // player.setHealth(5);
        // World.instance().getCampaign().add(new Level(1));
        // World.instance().getCurrentLevel().placeEntity(1, 2, player);
        // World.instance().save("SaveGame.dat");
        
        // try (DataInputStream reader = new DataInputStream(new FileInputStream("SaveGame.dat"))) {
        //     assertEquals(World.instance().getCurrentLevel(), reader.readInt());
        //     assertEquals(World.instance().getDifficultyLevel(), reader.readInt());
        //     assertEquals(World.instance().getPlayer().getHealth(), reader.readInt());
        // } catch (IOException e) {
        //     fail();
        // }

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
