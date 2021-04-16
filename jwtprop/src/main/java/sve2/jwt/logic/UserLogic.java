package sve2.jwt.logic;

import sve2.jwt.exception.BadRequestException;
import sve2.jwt.exception.NotFoundException;
import sve2.jwt.model.UserCredentialsModel;
import sve2.jwt.model.AuthResponseModel;
import sve2.jwt.model.UserModel;

public interface UserLogic {
    AuthResponseModel authenticateUser(UserCredentialsModel request) throws BadRequestException, NotFoundException;
    Long createUser(UserModel user);
    Long createAdmin(UserModel user);
    UserModel getUserById(Long id);
    void addRoleToUser(Long id, Long roleId) throws NotFoundException;
}
