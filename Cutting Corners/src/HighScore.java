public class HighScore {
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
    
}
