package luabui.application.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PaymentMode {
    CARD("card"),
    CASH_ON_DELIVERY("cod"),
    CARD_ON_DELIVERY("caod");

    private static final Map<String, PaymentMode> LOOKUP;

    static {
        LOOKUP = new HashMap<>();
        for (PaymentMode paymentMode : EnumSet.allOf(PaymentMode.class)) {
            LOOKUP.put(paymentMode.getDescription(), paymentMode);
        }
    }

    String description;

    PaymentMode(String description) {
        this.description = description;
    }

    @JsonCreator
    public static PaymentMode fromDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Payment Mode Description cannot be null");
        }
        if (LOOKUP.containsKey(description)) {
            return LOOKUP.get(description);
        }
        throw new IllegalArgumentException("No Payment Mode found with given Description.");
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return description;
    }
}
