package luabui.application.controller;

import lombok.extern.slf4j.Slf4j;
import luabui.application.dto.CartDTO;
import luabui.application.dto.FoodItemDTO;
import luabui.application.model.Cart;
import luabui.application.model.CartFoodItem;
import luabui.application.model.FoodItem;
import luabui.application.service.CartFoodItemService;
import luabui.application.service.CartService;
import luabui.application.service.FoodItemService;
import luabui.application.utility.MapperUtil;
import luabui.application.vo.request.FoodItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin
public class CartController {

    private CartService cartService;
    private FoodItemService foodItemService;
    private CartFoodItemService cartFoodItemService;

    @Autowired
    public CartController(CartService cartService, FoodItemService foodItemService, CartFoodItemService cartFoodItemService) {

        this.cartService = cartService;
        this.foodItemService = foodItemService;
        this.cartFoodItemService = cartFoodItemService;
    }

    @PostMapping("/customers/cart/add")
    public ResponseEntity<CartDTO> addToCart(@Valid @RequestBody FoodItemForm foodItemForm, @RequestParam Long cartID) {
        CartDTO cartDTO = cartService.findById(cartID);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addItemToCart(foodItemForm,cartDTO));
    }

}
