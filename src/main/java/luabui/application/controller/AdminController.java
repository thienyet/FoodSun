package luabui.application.controller;

import lombok.extern.slf4j.Slf4j;
import luabui.application.dto.CustomerDTO;
import luabui.application.model.Role;
import luabui.application.model.User;
import luabui.application.service.*;
import luabui.application.vo.request.LoginForm;
import luabui.application.vo.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> getAllRestaurants() {
        log.debug("Getting all Restaurants");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.findAll());
    }

    @GetMapping(value = "/admin/restaurants/{createDate}")
    public ResponseEntity<?> getAllRestaurantsByCreateDate(@PathVariable Date createDate) {
        log.debug("Getting all Restaurants by create Date");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getRestaurantByDate(createDate));
    }
}
