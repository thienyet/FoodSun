package luabui.application.service;


import luabui.application.dto.OrderDTO;

public interface OrderService extends CrudService<OrderDTO, Long> {
    OrderDTO createOrder(OrderDTO orderDTO);
}
