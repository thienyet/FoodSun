package luabui.application.exception;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException(Long customerId) {
        super("No Customer with Id: " + customerId);
    }
}
