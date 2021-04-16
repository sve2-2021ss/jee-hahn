package sve2.jwt.logic;

import sve2.jwt.exception.NotFoundException;
import sve2.jwt.model.MovieModel;

import java.util.List;

public interface MovieLogic {
    List<MovieModel> getAll();
    MovieModel getMovieById(Long id) throws NotFoundException;
    Long createMovie(MovieModel model);
    MovieModel updateMovie(MovieModel movie) throws NotFoundException;
}
