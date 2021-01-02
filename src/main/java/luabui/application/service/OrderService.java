package luabui.application.service;


import luabui.application.dto.OrderDTO;
import luabui.application.dto.RestaurantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;

public interface OrderService extends CrudService<OrderDTO, Long> {
    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO createOrder2(Long customerId, OrderDTO orderDTO);

//    Page<OrderDTO> getOrderOfResInOneDay(Long restaurantId, Date date, Pageable pageable);
    Double getRevenueAdmin(Integer month);

    RestaurantDTO getMostRestaurant(Integer month);

    Double getRevenueRestaurant(Long restaurantId, Integer month);

    Double getRevenueDelivery(Long deliveryguyId, Integer month);
}
