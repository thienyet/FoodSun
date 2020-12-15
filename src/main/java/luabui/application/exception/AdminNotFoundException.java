package luabui.application.exception;

public class AdminNotFoundException extends NotFoundException{
    public AdminNotFoundException(Long adminId) {
        super("No Admin with Id: " + adminId);
    }
}
