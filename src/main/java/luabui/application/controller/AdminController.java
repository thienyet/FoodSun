package luabui.application.controller;

import lombok.extern.slf4j.Slf4j;
import luabui.application.dto.*;
import luabui.application.model.Role;
import luabui.application.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
/**
 * Controller to map all Admin Related operations.
 */
public class AdminController {
    private AdminService adminService;
    private UserService userService;
    private RoleService roleService;
    private OrderService orderService;
    private CustomerService customerService;
    private RestaurantService restaurantService;
    private DeliveryGuyService deliveryGuyService;
    private CategoryService categoryService;

    @Autowired
    public AdminController(AdminService adminService, UserService userService, RoleService roleService, OrderService orderService, CustomerService customerService, RestaurantService restaurantService, DeliveryGuyService deliveryGuyService, CategoryService categoryService) {
        this.adminService = adminService;
        this.userService = userService;
        this.roleService = roleService;
        this.orderService = orderService;
        this.customerService = customerService;
        this.restaurantService = restaurantService;
        this.deliveryGuyService = deliveryGuyService;
        this.categoryService = categoryService;
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

    @PostMapping(value = "/foodsun/signup/admins")
    public ResponseEntity<AdminDTO> saveAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        log.debug("Saving Admin.");
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.save(adminDTO));
    }

    @GetMapping(value = "/admin/profile")
    public ResponseEntity<?> getProfile(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findByEmail(email));
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

    @GetMapping(value = "/admin/restaurants/address/{area}")
    public ResponseEntity<?> getAllRestaurantsInArea(@PathVariable String area,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Restaurants in an area");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> restaurantDTOList = restaurantService.findRestaurantByAddressLike(area, request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOList);
    }

    @GetMapping(value = "/admin/restaurants/name/{name}")
    public ResponseEntity<?> getAllRestaurantsByName(@PathVariable String name,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Restaurants by name");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> restaurantDTOList = restaurantService.getRestaurantByName(name, request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOList);
    }

    @GetMapping(value = "/admin/customers")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Customers");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<CustomerDTO> list = customerService.findAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping(value = "/admin/customers/createDate/{createDate}")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomersByCreateDate(@PathVariable Date createDate,
                                                                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                             @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Customers by create Date");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<CustomerDTO> customerDTOPage = customerService.getCustomerByDate(createDate, request);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTOPage);
    }

    @GetMapping(value = "/admin/customers/address/{address}")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomersByAddress(@PathVariable String address,
                                                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                         @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Customers by address");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<CustomerDTO> customerDTOPage = customerService.getCustomerByAddressLike(address, request);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTOPage);
    }

    @GetMapping(value = "/admin/customers/name/{name}")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomersByName(@PathVariable String name,
                                                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                      @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Customers by name");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<CustomerDTO> customerDTOPage = customerService.getCustomerByName(name, request);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTOPage);
    }

    @GetMapping(value = "/admin/deliveryguys/createDate/{createDate}")
    public ResponseEntity<Page<DeliveryGuyDTO>> getAllDeliveryGuysByCreateDate(@PathVariable Date createDate,
                                                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                               @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all DeliveryGuys by create Date");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<DeliveryGuyDTO> deliveryGuyDTOPage = deliveryGuyService.getDeliveryGuyByDate(createDate, request);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyDTOPage);
    }

    @GetMapping(value = "/admin/deliveryguys/address/{address}")
    public ResponseEntity<Page<DeliveryGuyDTO>> getAllDeliveryGuysByAddress(@PathVariable String address,
                                                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                      @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all DeliveryGuys by address");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<DeliveryGuyDTO> deliveryGuyDTOPage = deliveryGuyService.getDeliveryGuyByAddress(address, request);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyDTOPage);
    }

    @GetMapping(value = "/admin/deliveryguys")
    public ResponseEntity<Page<DeliveryGuyDTO>> getAllDeliveryGuys(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                            @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all DeliveryGuys");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<DeliveryGuyDTO> deliveryGuyDTOPage = deliveryGuyService.findAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyDTOPage);
    }

    @GetMapping(value = "/admin/deliveryguys/name/{name}")
    public ResponseEntity<Page<DeliveryGuyDTO>> getAllDeliveryGuysByName(@PathVariable String name,
                                                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                   @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all DeliveryGuys by name");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<DeliveryGuyDTO> deliveryGuyDTOPage = deliveryGuyService.getDeliveryGuyByName(name, request);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyDTOPage);
    }

    @GetMapping(value = "/admin/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
    }

    @GetMapping(value = "admin/categories/{categoryId}/restaurants")
    public ResponseEntity<Page<RestaurantDTO>> getRestaurantByCategory(@PathVariable Long categoryId,
                                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> restaurantDTOPage = restaurantService.getRestaurantByCategory(categoryId, request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOPage);
    }

    @GetMapping(value = "/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findById(categoryId));
    }

    @PostMapping(value = "/admin/categories/add")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryDTO));
    }

    @PutMapping(value = "/admin/categories/edit/{categoryId}")
    public ResponseEntity<CategoryDTO> editCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(categoryDTO, categoryId));
    }
}
