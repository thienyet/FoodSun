package luabui.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartFoodItemDTO extends BaseEntityDTO{

    private Long foodItemId;
    @Min(value = 1, message = "Food Item Quantity should be at least 1.")
    @Max(value = 5, message = "Food Item Quantity should not exceed 5.")
    private Integer quantity;
    private Double price;
}
