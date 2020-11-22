package luabui.application.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum OrderStatus {
    APPROVED("approved"),
    CANCELLED("cancelled"),
    CANCELLED_BY_USER("user cancelled"),
    CANCELLED_BY_RESTAURANT("restaurant cancelled"),
    PREPARING("preparing"),
    PICKED_UP("picked up"),
    DELIVERED("delivered");

    private static final Map<String, OrderStatus> LOOKUP;

    static {
        LOOKUP = new HashMap<>();
        for (OrderStatus orderStatus : EnumSet.allOf(OrderStatus.class)) {
            LOOKUP.put(orderStatus.getDescription(), orderStatus);
        }
    }

    String description;

    OrderStatus(String description) {
        this.description = description;
    }

    @JsonCreator
    public static OrderStatus fromDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Order Status Description cannot be null");
        }
        if (LOOKUP.containsKey(description)) {
            return LOOKUP.get(description);
        }
        throw new IllegalArgumentException("No Order Status found with given Description.");
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return description;
    }
}
