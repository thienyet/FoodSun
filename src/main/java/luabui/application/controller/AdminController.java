package luabui.application.controller;

import luabui.application.config.jwt.JwtProvider;
import luabui.application.dto.CustomerDTO;
import luabui.application.model.Role;
import luabui.application.model.User;
import luabui.application.service.RoleService;
import luabui.application.service.UserService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
/**
 * Controller to map all Admin Related operations.
 */
public class AdminController {
    private RoleService roleService;

    @Autowired
    public AdminController(RoleService roleService) {
        this.roleService = roleService;

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
    
}
