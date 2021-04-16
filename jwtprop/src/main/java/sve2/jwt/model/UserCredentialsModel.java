package sve2.jwt.model;

public class UserCredentialsModel {
    private String username;
    private String password;

    public UserCredentialsModel() {
    }

    public UserCredentialsModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
}
