package model;
public class HighScore implements Comparable {
    private int score;
    private String name;
    public HighScore()
    {

    }
    public HighScore(int s, String n)
    {
        score=s;
        name=n;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public int compareTo(Object o) {
        if(o instanceof HighScore)
        {
            HighScore temp = (HighScore) o;
            if(this.getScore()>temp.getScore())
            {
                return 1;
            }
            else if(this.getScore()<temp.getScore())
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
        else
        {
            return 0;
        }
    }
    
}
