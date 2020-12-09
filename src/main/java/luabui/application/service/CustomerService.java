package luabui.application.service;



import luabui.application.dto.CustomerDTO;
import luabui.application.dto.OrderDTO;
import luabui.application.dto.OrderModificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface CustomerService extends CrudService<CustomerDTO, Long> {
    CustomerDTO update(CustomerDTO customerDTO, Long customerId);

    List<OrderDTO> getCustomerOrders(Long customerId);

    OrderDTO getCustomerOrderById(Long customerId, Long orderId);

    OrderDTO modifyOrder(Long customerId, Long orderId, OrderModificationDTO modification);

    Page<CustomerDTO> getCustomerByDate(Date createDate, Pageable pageable);

    Page<CustomerDTO> findAll(Pageable pageable);

    Page<CustomerDTO> getCustomerByAddressLike(String address, Pageable pageable);

    CustomerDTO findByEmail(String email);
}
