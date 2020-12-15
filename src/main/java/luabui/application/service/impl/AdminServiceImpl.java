package luabui.application.service.impl;

import luabui.application.dto.AdminDTO;
import luabui.application.exception.AdminNotFoundException;
import luabui.application.exception.CustomerNotFoundException;
import luabui.application.model.Admin;
import luabui.application.model.Cart;
import luabui.application.model.Customer;
import luabui.application.model.User;
import luabui.application.repository.AdminRepository;
import luabui.application.repository.RoleRepository;
import luabui.application.repository.UserRepository;
import luabui.application.service.AdminService;
import luabui.application.utility.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public AdminServiceImpl(AdminRepository adminRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public AdminDTO findByEmail(String email) {
        Admin admin = adminRepository.findByEmail(email);
        return MapperUtil.toAdminDTO(admin);
    }

    @Override
    public List<AdminDTO> findAll() {
        List<Admin> adminList = adminRepository.findAll();
        return adminList.stream().map(MapperUtil :: toAdminDTO).collect(Collectors.toList());
    }

    @Override
    public AdminDTO findById(Long aLong) {
        Admin admin = adminRepository.getOne(aLong);
        return MapperUtil.toAdminDTO(admin);
    }

    @Override
    public AdminDTO save(AdminDTO newObject) {
        Admin admin = adminRepository.save(MapperUtil.toAdmin(newObject));
        createUser(admin);
        return MapperUtil.toAdminDTO(admin);
    }

    @Override
    public void deleteById(Long aLong) {

    }

    private void createUser(Admin admin) {
        User user = new User();
        user.setEmail(admin.getEmail());
//        user.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        user.setPassword(admin.getPassword());
        user.setRole(roleRepository.findByRole("ADMIN"));
        userRepository.save(user);
    }
}
