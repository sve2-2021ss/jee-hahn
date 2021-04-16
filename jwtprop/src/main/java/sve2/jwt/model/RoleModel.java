package sve2.jwt.model;

import sve2.jwt.domain.Role;

public class RoleModel {

    private Long id;
    private String name;

    public RoleModel() {
    }

    public RoleModel(Role role) {
        id = role.getId();
        name = role.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }
}
