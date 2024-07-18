package Controller;

public class UserClass {
	private int ID;
	private String password;
	private String username;
    private String email;
    private String difficultyGame1;
    private int levelGame1;
    private String difficultyGame3;
    
    //Inserire difficoltà e livello registrato degli alti giochi

    public UserClass(int ID, String username, String password, String email, String difficultyGame1, int levelGame1, String difficultyGame3) {
    	this.ID = ID;
    	this.username = username;
    	this.password = password;
        this.email = email;
        this.difficultyGame1 = difficultyGame1;
        this.levelGame1 = levelGame1;
        this.difficultyGame3 = difficultyGame3;
    }
    
    public int getID() {
        return ID;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }

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

    public String getDifficultyGame3() {
        return difficultyGame3;
    }

    public void setDifficultyGame3(String difficultyGame3) {
        this.difficultyGame3 = difficultyGame3;
    }
}