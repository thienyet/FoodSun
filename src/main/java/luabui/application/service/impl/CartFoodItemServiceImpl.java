package luabui.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import luabui.application.dto.CartFoodItemDTO;
import luabui.application.service.CartFoodItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CartFoodItemServiceImpl implements CartFoodItemService {
    @Override
    public List<CartFoodItemDTO> findAll() {
        return null;
    }

    @Override
    public CartFoodItemDTO findById(Long aLong) {
        return null;
    }

    @Override
    public CartFoodItemDTO save(CartFoodItemDTO newObject) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
