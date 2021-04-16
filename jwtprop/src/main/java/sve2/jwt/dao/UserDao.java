package sve2.jwt.dao;

import sve2.jwt.domain.ApplicationUser;

import java.util.List;

public interface UserDao {
    ApplicationUser findById(Long id);
    ApplicationUser findByUsername(String username);
    List<ApplicationUser> findAll();
    ApplicationUser merge(ApplicationUser entity);
    void persist(ApplicationUser entity);
}
