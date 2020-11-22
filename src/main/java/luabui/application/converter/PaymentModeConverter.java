package luabui.application.converter;

import luabui.application.constants.PaymentMode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentModeConverter implements AttributeConverter<PaymentMode, String> {
    @Override
    public String convertToDatabaseColumn(PaymentMode paymentMode) {
        return paymentMode.getDescription();
    }

    @Override
    public PaymentMode convertToEntityAttribute(String description) {
        return PaymentMode.fromDescription(description);
    }
}
