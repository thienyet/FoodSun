package luabui.application.repository;

import luabui.application.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findAll(Pageable pageable);

    @Query("select cus from Customer cus where cus.createDate = :createDate")
    Page<Customer> getCustomersByDate(@Param("createDate")Date createDate, Pageable pageable);

    @Query("select cus from Customer cus where cus.address like %:address%")
    Page<Customer> getCustomersByAddressLike(@Param("address")String address, Pageable pageable);

    Customer findByEmail(String email);
}
