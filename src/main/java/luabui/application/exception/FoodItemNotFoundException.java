package luabui.application.exception;

public class FoodItemNotFoundException extends NotFoundException {
    public FoodItemNotFoundException(Long foodItemId) {
        super("No Food Item with Id: " + foodItemId);
    }
}
