package luabui.application.exception;

public class OrderFoodItemNotFoundException extends NotFoundException {
    public OrderFoodItemNotFoundException(Long orderFoodItemId) {
        super("No Order Food Item with Id: " + orderFoodItemId);
    }
}
