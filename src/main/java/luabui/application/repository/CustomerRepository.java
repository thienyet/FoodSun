package luabui.application.repository;

import luabui.application.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select cus from Customer cus where cus.createDate = :createDate")
    List<Customer> findCustomerByDate(@Param("createDate")Date createDate);
}
