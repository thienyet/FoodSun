package luabui.application.service;


import luabui.application.model.User;

import java.util.List;

public interface UserService extends CrudService<User, Long> {
    User findByEmail(String email);
    List<User> findByRole(String role);
}
