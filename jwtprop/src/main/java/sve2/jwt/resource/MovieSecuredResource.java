package sve2.jwt.resource;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import sve2.jwt.exception.ApplicationException;
import sve2.jwt.logic.MovieLogic;
import sve2.jwt.model.MovieModel;

@RequestScoped
@Path("/movies")
public class MovieSecuredResource {

    @Inject
    MovieLogic movieLogic;

    @GET
    @RolesAllowed({ "User" })
    public List<MovieModel> GetAll() {
        return movieLogic.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "User" })
    public MovieModel GetById(@PathParam("id") long id) {
        try {
            return movieLogic.getMovieById(id);
        } catch (ApplicationException e) {
            sve2.jwt.exception.WebApplicationException.of(e);
            return null;
        }
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response CreateMovie(MovieModel movie) {
       Long id = movieLogic.createMovie(movie);
        return Response.created(URI.create("movies/" + id)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public MovieModel UpdateMovie(@PathParam("id") long id, MovieModel movie) {
        movie.setId(id);
        try {
            return movieLogic.updateMovie(movie);
        } catch (ApplicationException e) {
            sve2.jwt.exception.WebApplicationException.of(e);
            return null;
        }
    }
}
