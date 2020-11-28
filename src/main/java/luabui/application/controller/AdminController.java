package luabui.application.controller;

import luabui.application.config.jwt.JwtProvider;
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

import java.util.List;

@RestController
/**
 * Controller to map all Admin Related operations.
 */
public class AdminController {
    private RoleService roleService;
    private UserService userService;
    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AdminController(RoleService roleService, UserService userService, JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.roleService = roleService;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
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

    @PostMapping(value = "/admin/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginForm loginForm) {
        // throws Exception if authentication failed
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generate(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByEmail(userDetails.getUsername());
            return ResponseEntity.ok(new JwtResponse(jwt, user.getEmail(), user.getRole().getRole()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
