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


    /*
    * Create new admin
    * */
    @PostMapping(value = "/admins/addnew")
    public ResponseEntity<AdminDTO> saveAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        log.debug("Saving Admin.");
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.save(adminDTO));
    }

    /*
     * Get profile
     * */
    @GetMapping(value = "/admins/profile")
    public ResponseEntity<?> getProfile(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findByEmail(email));
    }

    @PutMapping(value = "admins/edit/{adminId}")
    public ResponseEntity<AdminDTO> updateAdmin(@Valid @RequestBody AdminDTO adminDTO, @PathVariable Long adminId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.update(adminDTO, adminId));
    }

    /*
     * Get all restauransts
     * */
    @GetMapping(value = "/admins/restaurants")
    public ResponseEntity<Page<RestaurantDTO>> getAllRestaurants(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Restaurants are active");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> list = restaurantService.findAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /*
     * Get restauranst by Id
     * */
    @GetMapping(value = "/admins/restaurants/Id/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getAllRestaurantById(@PathVariable Long restaurantId) {
        log.debug("Getting Restaurant by Id");
        RestaurantDTO restaurantDTO = restaurantService.findById(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }

    /*
     * Get all restaurants by create date
     * */
    @GetMapping(value = "/admins/restaurants/createDate/{createDate}")
    public ResponseEntity<Page<RestaurantDTO>> getAllRestaurantsByCreateDate(@PathVariable Date createDate,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Restaurants by create Date");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> restaurantDTOList = restaurantService.getRestaurantByDate(createDate, request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOList);
    }

    /*
     * Get all restaurants by address
     * */
    @GetMapping(value = "/admins/restaurants/address/{area}")
    public ResponseEntity<?> getAllRestaurantsInArea(@PathVariable String area,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Restaurants in an area");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> restaurantDTOList = restaurantService.findRestaurantByAddressLike(area, request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOList);
    }

    /*
     * Get all restaurants by name
     * */
    @GetMapping(value = "/admins/restaurants/name/{name}")
    public ResponseEntity<?> getAllRestaurantsByName(@PathVariable String name,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Restaurants by name");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> restaurantDTOList = restaurantService.getRestaurantByName(name, request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOList);
    }

    /*
     * Get all restaurants of category by categoryId
     * */
    @GetMapping(value = "admins/restaurants/category/{categoryId}")
    public ResponseEntity<Page<RestaurantDTO>> getRestaurantByCategory(@PathVariable Long categoryId,
                                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                       @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<RestaurantDTO> restaurantDTOPage = restaurantService.getRestaurantByCategory(categoryId, request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOPage);
    }

    /*
     * Get all customers
     * */
    @GetMapping(value = "/admins/customers")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Customers are active");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<CustomerDTO> list = customerService.findAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /*
     * Get customer by Id
     * */
    @GetMapping(value = "/admins/customers/Id/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
        log.debug("Getting Customer by Id");
        CustomerDTO customerDTO = customerService.findById(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    /*
     * Get all customers by create date
     * */
    @GetMapping(value = "/admins/customers/createDate/{createDate}")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomersByCreateDate(@PathVariable Date createDate,
                                                                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                             @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Customers by create Date");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<CustomerDTO> customerDTOPage = customerService.getCustomerByDate(createDate, request);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTOPage);
    }

    /*
     * Get all customers by address
     * */
    @GetMapping(value = "/admins/customers/address/{address}")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomersByAddress(@PathVariable String address,
                                                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                         @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Customers by address");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<CustomerDTO> customerDTOPage = customerService.getCustomerByAddressLike(address, request);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTOPage);
    }

    /*
     * Get all customers by name
     * */
    @GetMapping(value = "/admins/customers/name/{name}")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomersByName(@PathVariable String name,
                                                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                      @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all Customers by name");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<CustomerDTO> customerDTOPage = customerService.getCustomerByName(name, request);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTOPage);
    }

    /*
     * Get all delivery guys by create date
     * */
    @GetMapping(value = "/admins/deliveryguys/createDate/{createDate}")
    public ResponseEntity<Page<DeliveryGuyDTO>> getAllDeliveryGuysByCreateDate(@PathVariable Date createDate,
                                                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                               @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all DeliveryGuys by create Date");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<DeliveryGuyDTO> deliveryGuyDTOPage = deliveryGuyService.getDeliveryGuyByDate(createDate, request);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyDTOPage);
    }

    /*
     * Get all delivery guys by address
     * */
    @GetMapping(value = "/admins/deliveryguys/address/{address}")
    public ResponseEntity<Page<DeliveryGuyDTO>> getAllDeliveryGuysByAddress(@PathVariable String address,
                                                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                      @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all DeliveryGuys by address");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<DeliveryGuyDTO> deliveryGuyDTOPage = deliveryGuyService.getDeliveryGuyByAddress(address, request);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyDTOPage);
    }

    /*
     * Get all delivery guys
     * */
    @GetMapping(value = "/admins/deliveryguys")
    public ResponseEntity<Page<DeliveryGuyDTO>> getAllDeliveryGuys(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                            @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all DeliveryGuys");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<DeliveryGuyDTO> deliveryGuyDTOPage = deliveryGuyService.findAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyDTOPage);
    }

    /*
     * Get delivery guy by Id
     * */
    @GetMapping(value = "/admins/deliveryguys/Id/{deliveryguyId}")
    public ResponseEntity<DeliveryGuyDTO> getDeliveryGuyrById(@PathVariable Long deliveryguyId) {
        log.debug("Getting Delivery Guy by Id");
        DeliveryGuyDTO deliveryGuyDTO = deliveryGuyService.findById(deliveryguyId);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyDTO);
    }

    /*
     * Get all delivery guys by name
     * */
    @GetMapping(value = "/admins/deliveryguys/name/{name}")
    public ResponseEntity<Page<DeliveryGuyDTO>> getAllDeliveryGuysByName(@PathVariable String name,
                                                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                   @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting all DeliveryGuys by name");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<DeliveryGuyDTO> deliveryGuyDTOPage = deliveryGuyService.getDeliveryGuyByName(name, request);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyDTOPage);
    }

    /*
     * Get all categories
     * */
    @GetMapping(value = "/admins/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
    }

    /*
     * Get category by categoryId
     * */
    @GetMapping(value = "/admins/categories/Id/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findById(categoryId));
    }

    /*
     * Get category by categoryId
     * */
    @GetMapping(value = "/admins/categories/name/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findByName(name));
    }

    /*
     * Create new category
     * */
    @PostMapping(value = "/admins/categories/add")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryDTO));
    }

    /*
     * Edit category
     * */
    @PutMapping(value = "/admins/categories/edit/{categoryId}")
    public ResponseEntity<CategoryDTO> editCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(categoryDTO, categoryId));
    }

    /*
     * Delete category
     * */
    @PutMapping(value = "/admins/categories/delete/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.updateDelete(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*
    * Get all admins
    * */
    @GetMapping(value = "/admins/admins")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findAll());
    }

    /*
     * Get admin by Id
     * */
    @GetMapping(value = "/admins/admins/Id/{adminId}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long adminId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findById(adminId));
    }

    /*
     * Get all admins by name
     * */
    @GetMapping(value = "/admins/admins/name/{name}")
    public ResponseEntity<List<AdminDTO>> getAllAdmin(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findByName(name));
    }

    /*
    * change status of admin (block or unblock)
    * */
    @PutMapping(value = "/admins/admins/changeStatus/{adminId}")
    public ResponseEntity<AdminDTO> blockAdmin(@PathVariable Long adminId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.changeStatus(adminId));
    }


    /*
     * change status of restaurant (block or unblock)
     * */
    @PutMapping(value = "/admins/restaurants/changeStatus/{restaurantId}")
    public ResponseEntity<RestaurantDTO> blockRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.changeStatus(restaurantId));
    }

    /*
     * change status of customer (block or unblock)
     * */
    @PutMapping(value = "/admins/customers/changeStatus/{customerId}")
    public ResponseEntity<CustomerDTO> blockCustomer(@PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.changeStatus(customerId));
    }

    /*
     * change status of delivery (block or unblock)
     * */
    @PutMapping(value = "/admins/deliveryguys/changeStatus/{deliveryguyId}")
    public ResponseEntity<DeliveryGuyDTO> blockDeliveryGuy(@PathVariable Long deliveryguyId) {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyService.changeStatus(deliveryguyId));
    }

    @GetMapping(value = "/admins/revenue/{month}")
    public ResponseEntity<Double> getRevenue(@PathVariable Integer month) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getRevenueAdmin(month));
    }

}
