package sve2.jwt.model;

public class AuthResponseModel {
    private String token;

    public AuthResponseModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
