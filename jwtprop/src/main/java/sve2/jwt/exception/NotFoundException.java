package sve2.jwt.exception;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
