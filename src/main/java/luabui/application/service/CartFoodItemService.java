package luabui.application.service;

import luabui.application.dto.CartFoodItemDTO;
import luabui.application.model.CartFoodItem;

public interface CartFoodItemService extends CrudService<CartFoodItem, Long> {

    CartFoodItem toCartFoodItem(CartFoodItemDTO cartFoodItemDTO);
}
