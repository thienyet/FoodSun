package luabui.application.service;



import luabui.application.dto.CustomerDTO;
import luabui.application.dto.OrderDTO;
import luabui.application.dto.OrderModificationDTO;

import java.sql.Date;
import java.util.List;

public interface CustomerService extends CrudService<CustomerDTO, Long> {
    CustomerDTO update(CustomerDTO customerDTO, Long customerId);

    List<OrderDTO> getCustomerOrders(Long customerId);

    OrderDTO getCustomerOrderById(Long customerId, Long orderId);

    OrderDTO modifyOrder(Long customerId, Long orderId, OrderModificationDTO modification);

    List<CustomerDTO> getCustomerByDate(Date createDate);
}
