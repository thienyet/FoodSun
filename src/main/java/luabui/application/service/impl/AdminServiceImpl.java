package luabui.application.service.impl;

import luabui.application.dto.AdminDTO;
import luabui.application.exception.AdminNotFoundException;
import luabui.application.exception.CustomerNotFoundException;
import luabui.application.exception.NotFoundException;
import luabui.application.exception.RestaurantNotFoundException;
import luabui.application.model.*;
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
    public AdminDTO update(AdminDTO adminDTO, Long adminId) {
        Admin admin  = getAdmin(adminId);
        User user = userRepository.findByEmail(admin.getEmail());
        admin.setEmail(adminDTO.getEmail());
        admin.setPhoneNo(adminDTO.getPhoneNo());
        admin.setName(adminDTO.getName());
        admin.setPassword(adminDTO.getPassword());
        admin.setAddress(adminDTO.getAddress());

        adminRepository.save(admin);

        user.setEmail(admin.getEmail());
        user.setPassword(admin.getPassword());
        userRepository.saveAndFlush(user);

        return MapperUtil.toAdminDTO(admin);
    }

    @Override
    public AdminDTO changeStatus(Long adminId) {
        Admin admin = getAdmin(adminId);
        User user = userRepository.findByEmail(admin.getEmail());
        admin.setIsActive(!admin.getIsActive());
        adminRepository.save(admin);

        user.setIsActive(admin.getIsActive());
        userRepository.saveAndFlush(user);
        return MapperUtil.toAdminDTO(admin);
    }

    @Override
    public List<AdminDTO> findByName(String name) {
        List<Admin> adminList = adminRepository.findByName(name);
        return adminList.stream().map(MapperUtil :: toAdminDTO).collect(Collectors.toList());
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
        user.setIsActive(admin.getIsActive());
        userRepository.save(user);
    }

    private Admin getAdmin(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> new NotFoundException("Not found admin with id " + adminId));
    }
}
