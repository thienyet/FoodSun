package luabui.application.service;


import luabui.application.dto.OrderDTO;

public interface OrderService extends CrudService<OrderDTO, Long> {
    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO createOrder2(Long customerId, OrderDTO orderDTO);

    Double getRevenueAdmin(Integer month);

    Double getRevenueRestaurant(Long restaurantId, Integer month);

    Double getRevenueDelivery(Long deliveryguyId, Integer month);
}
