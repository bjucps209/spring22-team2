import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HighScoreManager {
    private HighScore[] scores = new HighScore[5];
    public HighScoreManager() throws IOException
    {
        try (DataInputStream reader = new DataInputStream(new FileInputStream("scores.dat")))
        {
            for(int i=0;i<5;i++)
            {
                HighScore score = new HighScore();
                score.setScore(reader.readInt());
                score.setName(reader.readUTF());
                scores[i]=score;
            }
        }
        catch(EOFException e)
        {
            for(int i=0;i<5;i++)
            {
                scores[i]=new HighScore((i+1)*1000,"Default");
            }
        }
    }
    public void sort()
    {
        scores = Stream.of(scores).sorted().collect(Collectors.toList()).toArray(scores);
    }
    public void addScore(HighScore score)
    {
        if(score.getScore()>scores[4].getScore())
        {
            scores[4]=score;
            this.sort();
        }
    }
    public void load() throws IOException
    {
        try (DataInputStream reader = new DataInputStream(new FileInputStream("scores.dat")))
        {
            for(int i=0;i<5;i++)
            {
                HighScore score = new HighScore();
                score.setScore(reader.readInt());
                score.setName(reader.readUTF());
                scores[i]=score;
            }
        }
        catch(EOFException e)
        {
            for(int i=0;i<5;i++)
            {
                scores[i]=new HighScore((i+1)*1000,"Default");
            }
        }
    }
    public void save() throws IOException
    {
        try(DataOutputStream writer = new DataOutputStream(new FileOutputStream("scores.dat")))
        {
            for(int i=0;i<5;i++)
            {
                writer.writeInt(scores[i].getScore());
                writer.writeUTF(scores[i].getName());
            }
        }
    }
}
