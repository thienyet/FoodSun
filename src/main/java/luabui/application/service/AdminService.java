package luabui.application.service;

import luabui.application.dto.AdminDTO;

import java.util.List;

public interface AdminService extends CrudService<AdminDTO, Long> {

    AdminDTO findByEmail(String email);

    AdminDTO update(AdminDTO adminDTO, Long adminId);

    AdminDTO changeStatus(Long adminId);



    List<AdminDTO> findByName(String name);
}
