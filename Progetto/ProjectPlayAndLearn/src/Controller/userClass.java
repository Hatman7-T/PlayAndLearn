package Controller;

public class userClass {
	private String username;
    private String email;
    private String difficultyGame1;
    private int levelGame1;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDifficultyGame1() {
        return difficultyGame1;
    }

    public void setDifficultyGame1(String difficulty) {
        this.difficultyGame1 = difficulty;
    }

    public int getLevelGame1() {
        return levelGame1;
    }

    public void setLevelGame1(int level) {
        this.levelGame1 = level;
    }
}