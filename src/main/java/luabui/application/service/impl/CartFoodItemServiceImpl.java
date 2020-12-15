package luabui.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import luabui.application.dto.CartFoodItemDTO;
import luabui.application.exception.FoodItemNotFoundException;
import luabui.application.exception.NotFoundException;
import luabui.application.model.Cart;
import luabui.application.model.CartFoodItem;
import luabui.application.model.FoodItem;
import luabui.application.repository.CartFoodItemRepository;
import luabui.application.repository.CartRepository;
import luabui.application.repository.CategoryRepository;
import luabui.application.repository.FoodItemRepository;
import luabui.application.service.CartFoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CartFoodItemServiceImpl implements CartFoodItemService {
    private CartRepository cartRepository;
    private CartFoodItemRepository cartFoodItemRepository;
    private FoodItemRepository foodItemRepository;

    @Autowired
    public CartFoodItemServiceImpl(CartRepository cartRepository, CartFoodItemRepository cartFoodItemRepository, FoodItemRepository foodItemRepository) {
        this.cartRepository = cartRepository;
        this.cartFoodItemRepository = cartFoodItemRepository;
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public List<CartFoodItem> findAll() {
        return null;
    }

    @Override
    public CartFoodItem findById(Long aLong) {
        return null;
    }

    @Override
    public CartFoodItem save(CartFoodItem newObject) {
        return null;
    }
    
    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public CartFoodItem toCartFoodItem(CartFoodItemDTO cartFoodItemDTO) {
        CartFoodItem cartFoodItem = new CartFoodItem();
        Long foodItemId = cartFoodItemDTO.getFoodItemId();
        FoodItem foodItem = foodItemRepository.findById(foodItemId).orElseThrow(() -> new FoodItemNotFoundException(foodItemId));
        cartFoodItem.setFoodItem(foodItem);
        cartFoodItem.setQuantity(cartFoodItemDTO.getQuantity());
        cartFoodItem.setPrice(foodItem.getPrice());
        Cart cart = cartRepository.findById(cartFoodItemDTO.getFoodItemId()).orElseThrow(() -> new NotFoundException("Not found food item id"));
        cartFoodItem.setCart(cart);
        return cartFoodItem;
    }
}
