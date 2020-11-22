package luabui.application.exception;

public class RestaurantNotFoundException extends NotFoundException {
    public RestaurantNotFoundException(Long restaurantId) {
        super("No Restaurant found with Id: " + restaurantId);
    }
}
