package luabui.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderModificationDTO {
    @Pattern(regexp = "cancelled|preparing|delivered|picked up", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Order status not accepted. User can send cancelled, Restro can send cancelled or preparing. Delivery can send picked up or delivered")
    String orderStatus;
}
