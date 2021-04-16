package sve2.jwt.model;

public class UserModel extends UserCredentialsModel {
    private String firstName;
    private String lastName;

    public UserModel() {
    }

    public UserModel(String username, String password, String firstName, String lastName) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
