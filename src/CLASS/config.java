package CLASS;

import java.io.Serializable;

public class config implements Serializable {

    String username, password;
    int theme;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public config() {
    }

    public config(String username, String password, int theme) {
        this.username = username;
        this.password = password;
        this.theme = theme;
    }

    @Override
    public String toString() {
        return username + " " + password + " " + theme;
    }
}
