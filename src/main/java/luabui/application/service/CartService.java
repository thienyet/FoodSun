package luabui.application.service;

import luabui.application.dto.CartDTO;
import luabui.application.dto.CartFoodItemDTO;
import luabui.application.dto.CustomerDTO;
import luabui.application.model.Customer;

import java.util.Collection;
import java.util.List;

public interface CartService extends CrudService<CartDTO, Long> {
    CartDTO getCart(CustomerDTO customerDTO);

    void mergeLocalCart(List<CartFoodItemDTO> cartFoodItemDTOList, CustomerDTO customerDTO);

    void delete(Long foodItemId, CustomerDTO customerDTO);

    void checkout(CustomerDTO customerDTO);
}
