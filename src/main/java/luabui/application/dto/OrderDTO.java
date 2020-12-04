package luabui.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO extends BaseEntityDTO{

    private Long customerId;

    private Long deliveryGuyId;

    private Long restaurantId;

    private LocalDateTime timestamp;

    @Pattern(regexp = "cod|caod|card", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Payment Mode can be: cod, caod(Card on Delivery), card")
    private String paymentMode;

    private String orderStatus;

    private Double totalPrice;

    private String deliveryAddress;

    private Set<OrderFoodItemDTO> orderFoodItemDTOs = new HashSet<>();
}
