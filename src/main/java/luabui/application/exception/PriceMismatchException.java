package luabui.application.exception;

public class PriceMismatchException extends IllegalArgumentException {
    public PriceMismatchException(String message) {
        super(message);
    }
}
