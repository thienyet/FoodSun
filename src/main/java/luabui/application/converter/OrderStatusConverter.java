package luabui.application.converter;

import luabui.application.constants.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {
    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        return orderStatus.getDescription();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String description) {
        return OrderStatus.fromDescription(description);
    }
}
