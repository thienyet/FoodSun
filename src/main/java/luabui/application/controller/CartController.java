//package luabui.application.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import luabui.application.dto.CartDTO;
//import luabui.application.dto.CustomerDTO;
//import luabui.application.dto.FoodItemDTO;
//import luabui.application.model.Cart;
//import luabui.application.model.CartFoodItem;
//import luabui.application.model.Customer;
//import luabui.application.model.FoodItem;
//import luabui.application.service.CartFoodItemService;
//import luabui.application.service.CartService;
//import luabui.application.service.CustomerService;
//import luabui.application.service.FoodItemService;
//import luabui.application.utility.MapperUtil;
//import luabui.application.vo.request.FoodItemForm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.security.Principal;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RestController
//@CrossOrigin
//public class CartController {
//
//    private CartService cartService;
//    private FoodItemService foodItemService;
//    private CartFoodItemService cartFoodItemService;
//    private CustomerService customerService;
//
//    @Autowired
//    public CartController(CartService cartService, FoodItemService foodItemService, CartFoodItemService cartFoodItemService, CustomerService customerService) {
//
//        this.cartService = cartService;
//        this.foodItemService = foodItemService;
//        this.cartFoodItemService = cartFoodItemService;
//        this.customerService = customerService;
//    }
//
//    @PostMapping("/customers/cart")
//    public ResponseEntity<CartDTO> mergeCart(@RequestBody List<CartFoodItem> cartFoodItems, Principal principal) {
//        CustomerDTO customerDTO = customerService.findByEmail(principal.getName());
//        try {
//            cartService.mergeLocalCart(cartFoodItems, customerDTO);
//        } catch (Exception e) {
//            ResponseEntity.badRequest().body("Merge Cart Failed");
//        }
//        return ResponseEntity.ok(cartService.getCart(customerDTO));
//    }
//
//    @GetMapping("/customers/cart")
//    public CartDTO getCart(Principal principal) {
//        CustomerDTO customerDTO = customerService.findByEmail(principal.getName());
//        return cartService.getCart(customerDTO);
//    }
//
//    @PostMapping("/customers/cart/add")
//    public boolean addToCart(@RequestBody FoodItemForm foodItemForm, Principal principal) {
//        FoodItem foodItem = foodItemService.findById(foodItemForm.getFoodItemId());
//        try {
//            mergeCart(Collections.singletonList(new CartFoodItem(foodItem, foodItemForm.getQuantity())), principal);
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//
//    @PutMapping("/customers/cart/{foodIemId}")
//    public CartFoodItem modifyItem(@PathVariable("foodIemId") Long foodIemId, @RequestBody Integer quantity, Principal principal) {
//
//        CustomerDTO customerDTO = customerService.findByEmail(principal.getName());
//        cartFoodItemService.update(foodIemId, quantity, customerDTO);
//        return cartFoodItemService.findOne(foodIemId, customerDTO);
//    }
//
//    @DeleteMapping("/customers/cart/{foodIemId}")
//    public void deleteItem(@PathVariable("foodIemId") Long foodIemId, Principal principal) {
//        CustomerDTO customerDTO = customerService.findByEmail(principal.getName());
//        cartService.delete(foodIemId, customerDTO);
//        // flush memory into DB
//    }
//
//
//    @PostMapping("/customers/cart/checkout")
//    public ResponseEntity checkout(Principal principal) {
//        CustomerDTO customerDTO = customerService.findByEmail(principal.getName());;// Email as username
//        cartService.checkout(customerDTO);
//        return ResponseEntity.ok(null);
//    }
//
//}
