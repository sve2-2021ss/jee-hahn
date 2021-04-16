package sve2.jwt.resource;

import sve2.jwt.logic.RoleLogic;
import sve2.jwt.model.RoleModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@Path("/roles")
public class RoleResource {

    @Inject
    RoleLogic roleLogic;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RoleModel> GetAll()
    {
        return roleLogic.getAll();
    }
}
