import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;
public class HighScoreTest {
    public HighScoreManager scores = new HighScoreManager();
    HighScoreManager tempScores = new HighScoreManager();
    @Test
    public void testReset()
    {
        scores.resetScores();
        for(int i=0;i<5;i++)
        {
            assertEquals("Default", scores.get(i).getName());
            assertEquals((i+1)*1000, scores.get(i).getScore());
        }
    }
    @Test
    public void testSort()
    {
        HighScore[] temp = new HighScore[5];
        temp[0] = new HighScore(10,"Ten");
        temp[1] = new HighScore(4,"Four");
        temp[2] = new HighScore(9,"Nine");
        temp[3] = new HighScore(12,"Twelve");
        temp[4] = new HighScore(1,"One");
        scores.setScores(temp);
        tempScores.setScores(temp);
        scores.sort();
        assertEquals(1, scores.get(0).getScore());
    }
    @Test
    public void testSaveLoad()
    {
        try
        {
            scores.save();
            scores.resetScores();
            scores.load();
        }
        catch (IOException e)
        {
            fail();
        }
        for(int i=0;i<5;i++)
        {
            assertEquals(tempScores.get(i).getName(), scores.get(i).getName());
            assertEquals(tempScores.get(i).getScore(), scores.get(i).getScore());
        }
    }
    @Test
    public void testAdd()
    {
        HighScore[] temp = new HighScore[5];
        temp[0] = new HighScore(10,"Ten");
        temp[1] = new HighScore(4,"Four");
        temp[2] = new HighScore(9,"Nine");
        temp[3] = new HighScore(12,"Twelve");
        temp[4] = new HighScore(1,"One");
        scores.setScores(temp);
        tempScores.setScores(temp);
        scores.sort();
        scores.addScore(new HighScore(7,"Seven"));
        assertEquals(7, scores.get(1).getScore()); 
        assertEquals(4, scores.get(0).getScore());  
    }
}
