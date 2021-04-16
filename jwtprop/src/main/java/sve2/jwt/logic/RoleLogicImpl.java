package sve2.jwt.logic;

import sve2.jwt.dao.RoleDao;
import sve2.jwt.domain.Role;
import sve2.jwt.model.RoleModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
@Transactional
public class RoleLogicImpl implements RoleLogic {

    @Inject
    RoleDao roleDao;

    @Override
    public List<RoleModel> getAll() {
        List<Role> movies = roleDao.findAll();

        return movies.stream()
                .map(RoleModel::new)
                .collect(Collectors.toList());
    }
}
