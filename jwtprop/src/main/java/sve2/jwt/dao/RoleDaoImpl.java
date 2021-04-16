package sve2.jwt.dao;

import sve2.jwt.domain.Role;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public Role findByName(String name) {
        TypedQuery<Role> query = em.createQuery(
                "select r from Role r where r.name = ?1",
                Role.class);
        query.setParameter(1, name);

        try {
            return query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Role> findAll() {
        return em
                .createQuery("select r from Role r", Role.class)
                .getResultList();
    }

    @Override
    public Role findById(Long id) {
        return em.find(Role.class, id);
    }

    @Override
    public void persist(Role entity) {
        em.persist(entity);
    }
}
