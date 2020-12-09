package luabui.application.exception;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException(Long customerId) {
        super("No Customer with Id: " + customerId);
    }

    public CustomerNotFoundException(String email) {super("No Customer with email: " + email);}
}
