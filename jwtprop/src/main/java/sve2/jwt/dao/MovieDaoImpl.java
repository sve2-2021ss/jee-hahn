package sve2.jwt.dao;

import sve2.jwt.domain.ApplicationUser;
import sve2.jwt.domain.Movie;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class MovieDaoImpl implements MovieDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public Movie findById(Long id) {
        return em.find(Movie.class, id);
    }

    @Override
    public List<Movie> findAll() {
        return em
                .createQuery("select m from Movie m", Movie.class)
                .getResultList();
    }

    @Override
    public Movie merge(Movie entity) {
        return em.merge(entity);
    }
}
