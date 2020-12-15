package luabui.application.controller;

import luabui.application.dto.*;
import luabui.application.model.FoodItem;
import luabui.application.model.Restaurant;
import luabui.application.model.User;
import luabui.application.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import luabui.application.service.FoodItemService;
import luabui.application.service.RestaurantService;
import luabui.application.service.UserService;
import luabui.application.utility.MapperUtil;
import luabui.application.vo.request.LoginForm;
import luabui.application.vo.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
/**
 * Controller to map all customer related actions.
 */
public class CustomerController {
    private CustomerService customerService;
    private RestaurantService restaurantService;
    private FoodItemService foodItemService;

    @Autowired
    public CustomerController(CustomerService customerService, RestaurantService restaurantService, FoodItemService foodItemService) {
        this.customerService = customerService;
        this.restaurantService = restaurantService;
        this.foodItemService = foodItemService;
    }

    /**
     * Returns a customer with the given id. Throws CustomerNotFoundError.
     *
     * @param customerId
     * @return
     */
    @GetMapping(value = "/customers/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId) {
        log.debug("Getting Customers By Id.");
        CustomerDTO customerDTO = customerService.findById(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    /**
     * Returns list of all orders belonging to a customer.
     *
     * @param customerId
     * @return
     */
    @GetMapping(value = "/customers/{customerId}/orders")
    public ResponseEntity<List<OrderDTO>> getCustomerOrders(@PathVariable Long customerId) {
        log.debug("Getting Customer Orders.");
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerOrders(customerId));
    }


    /**
     * Returns order detail belonging to passed customerId and orderId.
     *
     * @param customerId
     * @param orderId
     * @return
     */
    @GetMapping(value = "/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> getCustomerOrderById(@PathVariable Long customerId, @PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerOrderById(customerId, orderId));
    }

    /**
     * Persists a customer in database.
     *
     * @param customerDTO
     * @return
     */
    @PostMapping(value = "/foodsun/signup/customer")
    public ResponseEntity<CustomerDTO> saveCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        log.debug("Saving Customer.");
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerDTO));
    }


    /**
     * Deletes customer from database with given id.
     *
     * @param customerId
     * @return
     */
    @DeleteMapping(value = "/customers/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long customerId) {
        log.debug("Deleting Customer By Id.");
        customerService.deleteById(customerId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Modify order details of current order. Cannot change payment time after order is placed.
     *
     * @param customerId
     * @param orderId
     * @param modification
     * @return
     */
    @PutMapping(value = "/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> modifyOrder(@PathVariable Long customerId, @PathVariable Long orderId, @Valid @RequestBody OrderModificationDTO modification) {
        log.debug("Modifying Customer Order.");
        return ResponseEntity.status(HttpStatus.OK).body(customerService.modifyOrder(customerId, orderId, modification));
    }

    @GetMapping(value = "/customers/restaurants")
    public ResponseEntity<Page<RestaurantDTO>> getAllRestaurant(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> restaurantPage = restaurantService.findAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantPage);
    }

    @GetMapping(value = "/customers/restaurants/category/{categoryId}")
    public ResponseEntity<Page<RestaurantDTO>> getRestaurantByCategory(@PathVariable Long categoryId,
                                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                       @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> restaurantDTOPage = restaurantService.getRestaurantByCategory(categoryId, request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOPage);
    }

    @GetMapping(value = "/customers/restaurants/address/{area}")
    public ResponseEntity<?> getAllRestaurantsInArea(@PathVariable String area,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Restaurants in an area");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> restaurantDTOList = restaurantService.findRestaurantByAddressLike(area, request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOList);
    }

    @GetMapping(value = "customers/restaurants/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long restaurantId) {
        RestaurantDTO restaurantDTO = restaurantService.findById(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }

    @GetMapping(value = "customers/restaurants/{restaurantId}/foodItems")
    public ResponseEntity<Page<FoodItemDTO>> getFoodItemByRestaurantId(@PathVariable Long restaurantId,
                                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                       @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<FoodItem> foodItemPage = foodItemService.getRestaurantFoodItems(restaurantId, request);
        Page<FoodItemDTO> foodItemDTOPage = foodItemPage.map(foodItem -> MapperUtil.toFoodItemDTO(foodItem));
        return ResponseEntity.status(HttpStatus.OK).body(foodItemDTOPage);
    }

    @GetMapping(value = "customers/restaurants/foodItems/{foodItemId}")
    public ResponseEntity<FoodItemDTO> getFoodItemOfResByFoodId(@PathVariable Long foodItemId) {
        FoodItem foodItem = foodItemService.findById(foodItemId);
        return ResponseEntity.status(HttpStatus.OK).body(MapperUtil.toFoodItemDTO(foodItem));
    }

    @GetMapping(value = "/customers/profile")
    public ResponseEntity<?> getProfile(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findByEmail(email));
    }

//    @PutMapping(value = "/customers/profile/edit")
//    public ResponseEntity<CustomerDTO> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO, @PathVariable Long customerId) {
//        log.debug("Updating Customer.");
//        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.update(customerDTO, customerId));
//    }
}
