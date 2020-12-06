package luabui.application.service;


import luabui.application.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;

public interface OrderService extends CrudService<OrderDTO, Long> {
    OrderDTO createOrder(OrderDTO orderDTO);

//    Page<OrderDTO> getOrderOfResInOneDay(Long restaurantId, Date date, Pageable pageable);
}
