package luabui.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import luabui.application.dto.CartDTO;
import luabui.application.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CartServiceImpl implements CartService {
    @Override
    public void checkout(CartDTO cartDTO) {

    }

    @Override
    public List<CartDTO> findAll() {
        return null;
    }

    @Override
    public CartDTO findById(Long aLong) {
        return null;
    }

    @Override
    public CartDTO save(CartDTO newObject) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
