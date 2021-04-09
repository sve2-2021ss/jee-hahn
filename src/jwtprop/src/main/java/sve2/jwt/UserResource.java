package sve2.jwt;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import sve2.jwt.models.AuthResponse;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.HashSet;

@Path("/users")
@RequestScoped
public class UserResource {

    @GET
    @Path("/authenticate")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public AuthResponse authenticate() {
        String token = Jwt
                .issuer("https://sve2.jwt.com/issuer")
                .upn("andreas")
                .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                .claim(Claims.nickname, "Andi")
                .sign();

        return new AuthResponse(token);
    }
}
