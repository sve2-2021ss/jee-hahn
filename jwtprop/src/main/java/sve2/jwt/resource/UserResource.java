package sve2.jwt.resource;

import sve2.jwt.exception.ApplicationException;
import sve2.jwt.logic.UserLogic;
import sve2.jwt.model.UserCredentialsModel;
import sve2.jwt.model.AuthResponseModel;
import sve2.jwt.model.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/users")
@RequestScoped
public class UserResource {

    @Inject
    UserLogic userLogic;

    @POST
    @Path("/authenticate")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthResponseModel authenticate(UserCredentialsModel credentials) {
        try {
            return userLogic.authenticateUser(credentials);
        } catch (ApplicationException e) {
            sve2.jwt.exception.WebApplicationException.of(e);
            return null;
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserModel user) {
        Long id = userLogic.createUser(user);
        return Response.created(URI.create("users/" + id)).build();
    }

    @PUT
    @Path("/admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAdmin(UserModel user) {
        Long id = userLogic.createAdmin(user);
        return Response.created(URI.create("users/" + id)).build();
    }

    @POST
    @Path("/{id}/role/{roleId}")
    public void addRoleToUser(@PathParam("id") Long id, @PathParam("roleId") Long roleId) {
        try {
            userLogic.addRoleToUser(id, roleId);
        } catch (ApplicationException e) {
            sve2.jwt.exception.WebApplicationException.of(e);
        }
    }
}
