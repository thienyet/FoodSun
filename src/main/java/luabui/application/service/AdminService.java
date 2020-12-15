package luabui.application.service;

import luabui.application.dto.AdminDTO;

public interface AdminService extends CrudService<AdminDTO, Long> {

    AdminDTO findByEmail(String email);
}
