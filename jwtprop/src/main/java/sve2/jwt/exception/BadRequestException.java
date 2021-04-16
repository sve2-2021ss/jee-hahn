package sve2.jwt.exception;

public class BadRequestException extends ApplicationException {
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
