package luabui.application.controller;

import lombok.extern.slf4j.Slf4j;
import luabui.application.dto.RestaurantDTO;
import luabui.application.model.Role;
import luabui.application.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Slf4j
@RestController
/**
 * Controller to map all Admin Related operations.
 */
public class AdminController {
    private RoleService roleService;
    private CustomerService customerService;
    private RestaurantService restaurantService;
    private DeliveryGuyService deliveryGuyService;

    @Autowired
    public AdminController(RoleService roleService, CustomerService customerService, RestaurantService restaurantService, DeliveryGuyService deliveryGuyService) {
        this.roleService = roleService;
        this.customerService = customerService;
        this.restaurantService = restaurantService;
        this.deliveryGuyService = deliveryGuyService;
    }

    /**
     * Persists a role in database.
     *
     * @param role
     * @return
     */
    @PostMapping(value = "/admin/roles")
    public Role save(@RequestBody Role role) {
        return roleService.save(role);
    }

    @GetMapping(value = "/admin/allroles")
    /**
     * Returns list of all roles.
     */
    public List<Role> getAll() {
        return roleService.findAll();
    }

    @GetMapping(value = "/admin/restaurants")
    public ResponseEntity<Page<RestaurantDTO>> getAllRestaurants(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Restaurants");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> list = restaurantService.findAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping(value = "/admin/restaurants/createDate/{createDate}")
    public ResponseEntity<Page<RestaurantDTO>> getAllRestaurantsByCreateDate(@PathVariable Date createDate,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Restaurants by create Date");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> restaurantDTOList = restaurantService.getRestaurantByDate(createDate, request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOList);
    }

    @GetMapping(value = "/admin/restaurants/area/{area}")
    public ResponseEntity<?> getAllRestaurantsInArea(@PathVariable String area,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Restaurants in an area");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> restaurantDTOList = restaurantService.findRestaurantByAddressLike(area, request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOList);
    }


}
