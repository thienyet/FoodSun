package luabui.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodItemDTO extends BaseEntityDTO {

    @NotNull(message = "Food Item Name cannot be null.")
    private String name;

    @Positive
    private String image;
    private Double price;
    private Long restaurantId;
}
