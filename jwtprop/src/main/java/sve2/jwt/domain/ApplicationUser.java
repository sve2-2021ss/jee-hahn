package sve2.jwt.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ApplicationUser {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String passwordHash;

    @ManyToMany(mappedBy = "users", cascade = { CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public ApplicationUser() {
    }

    public ApplicationUser(String firstName, String lastName, String username, String passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public void addRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role is null");
        }

        role.getUsers().add(this);
        this.roles.add(role);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
