package luabui.application.repository;

import luabui.application.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByEmail(String email);

    @Query("select admin from Admin admin where admin.name like %:name%")
    List<Admin> findByName(String name);
    
}
