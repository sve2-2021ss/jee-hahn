package sve2.jwt.dao;

import sve2.jwt.domain.ApplicationUser;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public ApplicationUser findById(Long id) {
        return em.find(ApplicationUser.class, id);
    }

    @Override
    public ApplicationUser findByUsername(String username) {
        TypedQuery<ApplicationUser> query = em.createQuery(
                "select u from ApplicationUser u where u.username = ?1",
                ApplicationUser.class);
        query.setParameter(1, username);

        try {
            return query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<ApplicationUser> findAll() {
        return em
                .createQuery("select u from ApplicationUser u", ApplicationUser.class)
                .getResultList();
    }

    @Override
    public ApplicationUser merge(ApplicationUser entity) {
        return em.merge(entity);
    }

    @Override
    public void persist(ApplicationUser entity) {
        em.persist(entity);
    }
}
