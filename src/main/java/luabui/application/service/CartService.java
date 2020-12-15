package luabui.application.service;

import luabui.application.dto.CartDTO;
import luabui.application.dto.CartFoodItemDTO;
import luabui.application.dto.CustomerDTO;
import luabui.application.model.Customer;
import luabui.application.model.FoodItem;
import luabui.application.vo.request.FoodItemForm;

import java.util.Collection;
import java.util.List;

public interface CartService extends CrudService<CartDTO, Long> {

    void checkout(CartDTO cartDTO);

    CartDTO addItemToCart(FoodItemForm foodItemForm, CartDTO cartDTO);
}
