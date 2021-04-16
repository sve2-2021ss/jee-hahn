package sve2.jwt.dao;

import sve2.jwt.domain.Movie;

import java.util.List;

public interface MovieDao {
    Movie findById(Long id);
    List<Movie> findAll();
    Movie merge(Movie entity);
}
