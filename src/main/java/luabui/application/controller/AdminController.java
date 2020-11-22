package luabui.application.controller;

import luabui.application.model.Role;
import luabui.application.service.RoleService;
import luabui.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
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
