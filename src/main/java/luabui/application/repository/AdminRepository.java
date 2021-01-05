package luabui.application.repository;

import luabui.application.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByEmail(String email);

    List<Admin> findByName(String name);
    
}
