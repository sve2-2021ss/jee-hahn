package sve2.jwt.logic;

import sve2.jwt.dao.MovieDao;
import sve2.jwt.dao.UserDao;
import sve2.jwt.domain.Movie;
import sve2.jwt.exception.NotFoundException;
import sve2.jwt.model.MovieModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
@Transactional
public class MovieLogicImpl implements MovieLogic {

    @Inject
    MovieDao movieDao;

    @Override
    public List<MovieModel> getAll() {
        List<Movie> movies = movieDao.findAll();

        return movies.stream()
                .map(MovieModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public MovieModel getMovieById(Long id) throws NotFoundException {
        Movie movie = movieDao.findById(id);
        if (movie == null) {
            throw new NotFoundException("Movie with id " + id + " not found");
        }
        return new MovieModel(movie);
    }

    @Override
    public Long createMovie(MovieModel movie) {
        Movie newMovie = new Movie(
                movie.getName(),
                movie.getReleaseYear(),
                movie.getRating(),
                movie.getProductionCountry(),
                movie.getLength());

        Movie savedMovie = movieDao.merge(newMovie);
        return savedMovie.getId();
    }

    @Override
    public MovieModel updateMovie(MovieModel movie) throws NotFoundException {
        Movie movieFromDb = movieDao.findById(movie.getId());
        if (movieFromDb == null) {
            throw new NotFoundException("Movie with id " + movie.getId() + "not found");
        }

        movieFromDb.setName(movie.getName());
        movieFromDb.setReleaseYear(movie.getReleaseYear());
        movieFromDb.setRating(movie.getRating());
        movieFromDb.setProductionCountry(movie.getProductionCountry());
        movieFromDb.setLength(movie.getLength());

        Movie updatedMovie = movieDao.merge(movieFromDb);
        return new MovieModel(updatedMovie);
    }
}
