package kbcg;

public class Player {
    private String name;
    private int score;
    private int lives;

    public Player(String name) {
        this.name = name;
        this.score = 0;   
        this.lives = 3; 
    }

    public void addScore() { score++; }
    public void loseLife() { lives--; }

    public int getScore() { return score; }
    public int getLives() { return lives; }
    public String getName() { return name; }
}
