package luabui.application.controller;

import luabui.application.dto.*;
import luabui.application.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
/**
 * Controller to perform restaurant related operations.
 */
public class RestaurantController {
    private RestaurantService restaurantService;


    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;

    }

    /**
     * Returns list of restaurants.
     *
     * @return
     */
    @GetMapping(value = "/restaurants")
    public ResponseEntity<?> getAllRestaurants() {
        log.debug("Getting all Restaurants");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.findAll());
    }

    @PostMapping(value = "/foodsun/signup/restaurants")
    public ResponseEntity<RestaurantDTO> saveRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        log.debug("Saving Restaurant.");
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.save(restaurantDTO));
    }

    /**
     * Returns list of restaurants in an area.
     *
     * @return
     */
    @GetMapping(value = "/restaurants/area/{area}")
    public ResponseEntity<?> getAllRestaurantsInArea(@PathVariable String area) {
        log.debug("Getting all Restaurants in an area");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.findRestaurantByAddressLike(area));
    }

    /**
     * Returns restaurant belonging to given restaurantId.
     *
     * @param restaurantId
     * @return
     */
    @GetMapping(value = "/restaurants/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable Long restaurantId) {
        log.debug("Getting Restaurant by id.");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.findById(restaurantId));
    }

    /**
     * Returns list of food items belonging to given restaurant.
     *
     * @param restaurantId
     * @return
     */
    @GetMapping(value = "/restaurants/{restaurantId}/fooditems")
    public ResponseEntity<List<FoodItemDTO>> getRestaurantFoodItems(@PathVariable Long restaurantId) {
        log.debug("Getting all FoodItems using restaurantId.");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getRestaurantFoodItems(restaurantId));
    }

    /**
     * Returns list of orders taken by restaurant.
     *
     * @param restaurantId
     * @return
     */
    @GetMapping(value = "/restaurants/{restaurantId}/orders")
    public ResponseEntity<List<OrderDTO>> getRestaurantOrders(@PathVariable Long restaurantId) {
        log.debug("Getting all Orders using restaurantId.");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getRestaurantOrders(restaurantId));
    }

    /**
     * Return a single order taken by restaurant.
     *
     * @param restaurantId
     * @param orderId
     * @return
     */
    @GetMapping(value = "/restaurants/{restaurantId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> getRestaurantOrderById(@PathVariable Long restaurantId, @PathVariable Long orderId) {
        log.debug("Getting Restaurant Order By Restaurant Id.");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getRestaurantOrderById(restaurantId, orderId));
    }

    /**
     * Persists list of food items provided in request body.
     *
     * @param restaurantId
     * @param foodItemDTOs
     * @return
     */
    @PostMapping(value = "/restaurants/{restaurantId}/fooditems")
    public ResponseEntity<RestaurantDTO> addFoodItems(@PathVariable Long restaurantId, @Valid @RequestBody List<FoodItemDTO> foodItemDTOs) {
        log.debug("Adding food items to restaurants.");
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.addFoodItems(restaurantId, foodItemDTOs));
    }

    /**
     * Modify order status to preparing, cooking, cancelled.
     *
     *@param restaurantId
     * @param orderId
     * @param modification
     * @return
     */
    @PutMapping(value = "/restaurants/{restaurantId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> modifyOrder(@PathVariable Long restaurantId, @PathVariable Long orderId, @Valid @RequestBody OrderModificationDTO modification) {
        log.debug("Modifying Customer Order.");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.modifyOrder(restaurantId, orderId, modification));
    }


    @DeleteMapping(value = "/restaurants/{restaurantId}/fooditems")
    public ResponseEntity<?> removeFoodItems(@PathVariable Long restaurantId, @Valid @RequestBody List<Long> foodItemIds) {
        log.debug("Removing food items from restaurants");
        /**
         * Delete food items when provided with food item ids.
         *
         * @param restaurantId
         * @param foodItemIds
         * @return
         */ restaurantService.removeFoodItems(restaurantId, foodItemIds);
        return ResponseEntity.noContent().build();
    }

}
