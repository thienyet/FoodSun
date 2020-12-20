package luabui.application.service;

import luabui.application.dto.CartDTO;
import luabui.application.dto.CartFoodItemDTO;
import luabui.application.dto.CustomerDTO;
import luabui.application.model.CartFoodItem;
import luabui.application.model.Customer;
import luabui.application.model.FoodItem;
import luabui.application.vo.request.FoodItemForm;

import java.util.Collection;
import java.util.List;

public interface CartService extends CrudService<CartDTO, Long> {

    CartDTO getCart(CustomerDTO customerDTO);

    void mergeLocalCart(List<CartFoodItem> cartFoodItems, CustomerDTO customerDTO);

    void delete(Long foodItemID, CustomerDTO customerDTO);

    void checkout(CustomerDTO customerDTO);
}
