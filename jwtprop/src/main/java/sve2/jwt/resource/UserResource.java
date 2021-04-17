package sve2.jwt.resource;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import sve2.jwt.exception.ApplicationException;
import sve2.jwt.logic.UserLogic;
import sve2.jwt.model.UserCredentialsModel;
import sve2.jwt.model.AuthResponseModel;
import sve2.jwt.model.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;

@Path("/users")
@RequestScoped
public class UserResource {

    @Inject
    UserLogic userLogic;

    @Inject
    JsonWebToken jwt;

    @Claim(standard = Claims.groups)
    String claimGroups;

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

    @GET
    @Path("/jwt-data")
    @Produces(MediaType.TEXT_PLAIN)
    public String getJwtData(@Context SecurityContext context) {
        if (context.getUserPrincipal() == null) {
            return "no jwt available";
        }

        if (jwt.getName() == null) {
            return "no jwt available";
        }

        return String.format(
                "has JWT: %s,\n" +
                "user principal name: %s,\n" +
                "user principal given name: %s,\n" +
                "user principal family name: %s,\n" +
                "user principal groups: %s",
                jwt.getClaimNames() != null,
                context.getUserPrincipal().getName(),
                jwt.getClaim(Claims.given_name.toString()),
                jwt.getClaim(Claims.family_name.toString()),
                claimGroups);
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
