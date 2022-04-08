import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class HighScoreTest {
    public HighScoreManager scores = new HighScoreManager();
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
        HighScore[] tempScores = new HighScore[5];
        tempScores[0] = new HighScore(10,"Ten");
        tempScores[1] = new HighScore(4,"Four");
        tempScores[2] = new HighScore(9,"Nine");
        tempScores[3] = new HighScore(12,"Twelve");
        tempScores[4] = new HighScore(1,"One");
        scores.setScores(tempScores);
        scores.sort();
        assertEquals(1, scores.get(0).getScore());
    }
}
