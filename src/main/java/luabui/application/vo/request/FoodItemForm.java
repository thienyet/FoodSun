package luabui.application.vo.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class FoodItemForm {

    @Min(value = 1)
    private Integer quantity;
    @NotNull
    private Long foodItemId;
}
