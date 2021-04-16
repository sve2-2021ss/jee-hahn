package sve2.jwt.resource;

import sve2.jwt.logic.MovieLogic;
import sve2.jwt.logic.UserLogic;
import sve2.jwt.model.MovieModel;
import sve2.jwt.model.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/init")
public class InitResource {

    @Inject
    UserLogic userLogic;

    @Inject
    MovieLogic movieLogic;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String initializeDatabase() {
        var user = new UserModel("user", "user", "UserFirstName", "UserLastName");
        userLogic.createUser(user);

        var admin = new UserModel("admin", "admin", "AdminFirstName", "AdminLastName");
        userLogic.createAdmin(admin);

        var movie1 = new MovieModel("Jurassic World", 2015, 9, "United States", 124);
        var movie2 = new MovieModel("Jurassic World 2", 2018, 6, "United States", 129);
        var movie3 = new MovieModel("Der Schatzplanet", 2002, 8, "United States", 91);
        movieLogic.createMovie(movie1);
        movieLogic.createMovie(movie2);
        movieLogic.createMovie(movie3);

        return "Successfully initialized database";
    }
}
