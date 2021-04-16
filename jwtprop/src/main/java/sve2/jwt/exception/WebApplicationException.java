package sve2.jwt.exception;

import javax.ws.rs.core.Response;

public class WebApplicationException {
    public static void of(ApplicationException ex) {
        if (ex instanceof BadRequestException) {
            throw new javax.ws.rs.WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        } else if (ex instanceof NotFoundException) {
            throw new javax.ws.rs.WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
    }
}
