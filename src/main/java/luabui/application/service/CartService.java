package luabui.application.service;

import luabui.application.dto.CartDTO;
import luabui.application.dto.CustomerDTO;

import java.util.Collection;

public interface CartService extends CrudService<CartDTO, Long> {

    void checkout(CartDTO cartDTO);
}
