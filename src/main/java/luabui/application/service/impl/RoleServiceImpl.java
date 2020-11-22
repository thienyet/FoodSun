package luabui.application.service.impl;

import luabui.application.model.Role;
import luabui.application.repository.RoleRepository;
import luabui.application.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("No such role."));
    }

    @Override
    public Role save(Role newRole) {
        return roleRepository.save(newRole);
    }

    @Override
    public void deleteById(Long roleId) {
        roleRepository.deleteById(roleId);
    }
}
