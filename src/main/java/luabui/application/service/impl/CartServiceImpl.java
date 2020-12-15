package luabui.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import luabui.application.dto.CartDTO;
import luabui.application.dto.CartFoodItemDTO;
import luabui.application.dto.CustomerDTO;
import luabui.application.dto.FoodItemDTO;
import luabui.application.exception.FoodItemNotFoundException;
import luabui.application.exception.NotFoundException;
import luabui.application.model.Cart;
import luabui.application.model.CartFoodItem;
import luabui.application.model.FoodItem;
import luabui.application.repository.*;
import luabui.application.service.CartFoodItemService;
import luabui.application.service.CartService;
import luabui.application.service.CustomerService;
import luabui.application.service.OrderService;
import luabui.application.utility.MapperUtil;
import luabui.application.vo.request.FoodItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CustomerRepository customerRepository;
    private CartFoodItemRepository cartFoodItemRepository;
    private OrderRepository orderRepository;
    private FoodItemRepository foodItemRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CustomerRepository customerRepository, CartFoodItemRepository cartFoodItemRepository, OrderRepository orderRepository, FoodItemRepository foodItemRepository) {
        this.customerRepository = customerRepository;
        this.cartFoodItemRepository = cartFoodItemRepository;
        this.orderRepository = orderRepository;
        this.foodItemRepository = foodItemRepository;
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
    public void checkout(CartDTO cartDTO) {

    }

    @Override
    public CartDTO addItemToCart(FoodItemForm foodItemForm, CartDTO cartDTO) {
        Cart cart = MapperUtil.toCart(cartDTO);
//        List<CartFoodItem> cartFoodItemList = cartDTO.getCartFoodItemDTOs().stream().map(cfiDTO -> toCartFoodItem(cfiDTO)).collect(Collectors.toList());
        Set<CartFoodItem> set = cart.getCartFoodItems();
        set.forEach(cartFoodItem -> {
//            Optional<CartFoodItem> old = set.stream().filter(e -> e.getFoodItem().getId().equals(cartFoodItem.getId())).findFirst();
//            CartFoodItem cartFoodItem1;
//            if(old.isPresent()) {
//                cartFoodItem1 = old.get();
//                cartFoodItem1.setQuantity(cartFoodItem.getQuantity() + cartFoodItem1.getQuantity());
//                cartFoodItem1.setPrice(cartFoodItem.getPrice());
//            } else {
//                cartFoodItem1 = new CartFoodItem();
//                cartFoodItem1.setCart(cart);
//                cartFoodItem1.getQuantity(foodItemForm.getQuantity())
//            }
            FoodItem foodItem = foodItemRepository.findById(foodItemForm.getFoodItemId()).orElseThrow(() -> new NotFoundException("Not Found"));
            if(cartFoodItem.getFoodItem().getId() == foodItemForm.getFoodItemId()) {
                cartFoodItem.setQuantity(cartFoodItem.getQuantity() + foodItemForm.getQuantity());
                cartFoodItem.setFoodItem(foodItem);
                cartFoodItem.setPrice(foodItem.getPrice());
                cartFoodItem.setCart(cart);
//                cartFoodItemRepository.update
            } else {
                CartFoodItem cartFoodItem1 = new CartFoodItem();
                cartFoodItem1.setCart(cart);
                cartFoodItem1.setFoodItem(foodItem);
                cartFoodItem1.setPrice(foodItem.getPrice());
                cartFoodItem1.setQuantity(foodItemForm.getQuantity());
            }
//            cartRepository.updateCart
        });
        return MapperUtil.toCartDTO(cart);
    }

//    @Override
//    public CartDTO addItemToCart(FoodItem foodItem, CartDTO cartDTO) {
//        Cart cart = MapperUtil.toCart(cartDTO);
//        List<CartFoodItem> cartFoodItemList = cartDTO.getCartFoodItemDTOs().stream().map(cfiDTO -> cartFoodItemService.toCartFoodItem(cfiDTO)).collect(Collectors.toList());
//        FoodItem foodItem = foodItemService.findById(foodItemForm.getFoodItemId());
//        Set<CartFoodItem> set = cart.getCartFoodItems();
//        set.forEach(cartFoodItem -> {
//            Optional<CartFoodItem> old = set.stream().filter(e -> e.getFoodItem().getId().equals(cartFoodItem.getId())).findFirst();
//            CartFoodItem cartFoodItem1;
//            if(old.isPresent()) {
//                cartFoodItem1 = old.get();
//                cartFoodItem1.setQuantity(cartFoodItem.getQuantity());
//                cartFoodItem1.setPrice(cartFoodItem.getPrice());
//            }
//        });
//    }

    private CartFoodItem toCartFoodItem(CartFoodItemDTO cartFoodItemDTO) {
        log.debug("Converting cartFoodItemVo into cartFoodItem");
        CartFoodItem cartFoodItem = new CartFoodItem();
        Long foodItemId = cartFoodItemDTO.getFoodItemId();
        FoodItem foodItem = foodItemRepository.findById(foodItemId).orElseThrow(() -> new FoodItemNotFoundException(foodItemId));
        cartFoodItem.setFoodItem(foodItem);
        cartFoodItem.setQuantity(cartFoodItemDTO.getQuantity());
//        Double totalPrice = cartFoodItemDTO.getQuantity() * foodItem.getPrice();
//        if (BigDecimal.valueOf(totalPrice).compareTo(BigDecimal.valueOf(orderFoodItemDTO.getTotalPrice())) != 0) {
//            throw new PriceMismatchException("Total Price for " + foodItem.getName() + " should be " + totalPrice + " but found " + orderFoodItemDTO.getTotalPrice());
//        }
        cartFoodItem.setPrice(foodItem.getPrice());
        return cartFoodItem;
    }


}
