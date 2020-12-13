package luabui.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import luabui.application.dto.CartDTO;
import luabui.application.dto.CartFoodItemDTO;
import luabui.application.dto.CustomerDTO;
import luabui.application.repository.CartFoodItemRepository;
import luabui.application.repository.CartRepository;
import luabui.application.repository.CustomerRepository;
import luabui.application.repository.OrderRepository;
import luabui.application.service.CartFoodItemService;
import luabui.application.service.CartService;
import luabui.application.service.CustomerService;
import luabui.application.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CustomerRepository customerRepository;
    private CartFoodItemRepository cartFoodItemRepository;
    private OrderRepository orderRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CustomerRepository customerRepository, CartFoodItemRepository cartFoodItemRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.cartFoodItemRepository = cartFoodItemRepository;
        this.orderRepository = orderRepository;
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

    @Override
    public CartDTO getCart(CustomerDTO customerDTO) {
//        return cartRepository.getOne(customerDTO.getCartId());
        return null;
    }

    @Override
    public void mergeLocalCart(List<CartFoodItemDTO> cartFoodItemDTOList, CustomerDTO customerDTO) {
//        CartDTO finalCart = cartRepository.getOne(customerDTO.getCartId());
    }

    @Override
    public void delete(Long foodItemId, CustomerDTO customerDTO) {

    }

    @Override
    public void checkout(CustomerDTO customerDTO) {

    }
}
