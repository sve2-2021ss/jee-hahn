package sve2.jwt.logic;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import sve2.jwt.dao.RoleDao;
import sve2.jwt.dao.UserDao;
import sve2.jwt.domain.Role;
import sve2.jwt.domain.ApplicationUser;
import sve2.jwt.exception.BadRequestException;
import sve2.jwt.exception.NotFoundException;
import sve2.jwt.model.AuthResponseModel;
import sve2.jwt.model.UserCredentialsModel;
import sve2.jwt.model.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RequestScoped
@Transactional
public class UserLogicImpl implements UserLogic {

    @Inject
    RoleDao roleDao;
    @Inject
    UserDao userDao;

    @Override
    public AuthResponseModel authenticateUser(UserCredentialsModel request)
            throws BadRequestException, NotFoundException {
        ApplicationUser user = userDao.findByUsername(request.getUsername());
        if (user == null) {
            throw new NotFoundException("user not available");
        }

        if (isPasswordEqualHash(
                request.getPassword(),
                user.getPasswordHash())) {
            String token = createToken(user);
            return new AuthResponseModel(token);
        }

        throw new BadRequestException("wrong password");
    }

    @Override
    public Long createUser(UserModel userModel) {
        Role role = getOrCreateUserRole();

        var roles = new ArrayList<Role>();
        roles.add(role);

        return createUserWithRoles(userModel, roles);
    }

    @Override
    public Long createAdmin(UserModel userModel) {
        Role userRole = getOrCreateUserRole();
        Role adminRole = getOrCreateAdminRole();

        var roles = new ArrayList<Role>();
        roles.add(userRole);
        roles.add(adminRole);

        return createUserWithRoles(userModel, roles);
    }

    @Override
    public UserModel getUserById(Long id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long id, Long roleId) throws NotFoundException {
        ApplicationUser user = userDao.findById(id);
        if (user == null) {
            throw new NotFoundException("User with id " + id + " not found.");
        }

        Role role = roleDao.findById(roleId);
        if (role == null) {
            throw new NotFoundException("Role with id " + id + " not found.");
        }

        if (!user.getRoles().stream().anyMatch(r -> r.getId().equals(roleId))) {
            user.addRole(role);
        }
    }

    private Role getOrCreateUserRole() {
        Role role = roleDao.findByName("User");
        if (role == null) {
            role = new Role("User");
            roleDao.persist(role);
        }

        return role;
    }

    private Role getOrCreateAdminRole() {
        Role role = roleDao.findByName("Admin");
        if (role == null) {
            role = new Role("Admin");
            roleDao.persist(role);
        }

        return role;
    }

    private Long createUserWithRoles(UserModel userModel, List<Role> roles) {
        ApplicationUser user = new ApplicationUser(
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getUsername(),
                getPasswordHash(userModel.getPassword()));

        for (Role r: roles) {
            user.addRole(r);
        }

        return userDao.merge(user).getId();
    }

    private String getPasswordHash(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    private boolean isPasswordEqualHash(String password, String hash) {
        String pwHash = getPasswordHash(password);
        return pwHash.equals(hash);
    }

    private String createToken(ApplicationUser user) {
        Set<String> roles = new HashSet<>();
        for (Role r: user.getRoles()) {
            roles.add(r.getName());
        }

        return Jwt
                .issuer("https://sve2.jwt.com/issuer")
                .upn(user.getFirstName() + " " + user.getLastName())
                .groups(roles)
                .claim(Claims.given_name, user.getFirstName())
                .claim(Claims.family_name, user.getLastName())
                .sign();
    }
}
