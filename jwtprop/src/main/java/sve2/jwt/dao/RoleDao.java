package sve2.jwt.dao;

import sve2.jwt.domain.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAll();
    Role findById(Long id);
    Role findByName(String name);
    void persist(Role entity);
}
