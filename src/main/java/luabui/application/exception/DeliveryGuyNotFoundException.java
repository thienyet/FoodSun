package luabui.application.exception;

public class DeliveryGuyNotFoundException extends NotFoundException {
    public DeliveryGuyNotFoundException(Long deliveryGuyId) {
        super("No Delivery Guy with Id: " + deliveryGuyId);
    }
}
