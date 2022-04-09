import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.*;

public class SerializeTest {


    @Test
    void testSave_savesToFile() {
        // World.reset();
        // Player player = new Player(1, 2);
        // Enemy enemy = new Enemy(3, 5, 5, 5, null);
        // World.instance().getCampaign().add(new Level(2, 1));
        // World.instance().getCurrentLevel().placeEntity(1, 2, player);
        // World.instance().getCurrentLevel().placeEntity(2, 4, enemy);
        World.instance().save("SaveGame.dat");

    }

    @Test
    void testLoad_loadsFromFile() {
        // World.reset();
        // Level currentLevel = World.instance().getCurrentLevel();
        // assertEquals(currentLevel.getEntities().size(), 0);
        World.instance().load("SaveGame.dat");
        // assertEquals(currentLevel.getEntities().size(), 2);

    }
}
