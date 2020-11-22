package luabui.application.service;

import luabui.application.dto.DeliveryGuyDTO;
import luabui.application.dto.OrderDTO;
import luabui.application.dto.OrderModificationDTO;

import java.util.List;

public interface DeliveryGuyService extends CrudService<DeliveryGuyDTO, Long> {
    List<OrderDTO> getDeliveryGuyOrders(Long deliveryGuyId);

    OrderDTO getDeliveryGuyOrderById(Long deliveryGuyId, Long orderId);

    DeliveryGuyDTO update(DeliveryGuyDTO deliveryGuyDTO, Long deliveryGuyId);

    OrderDTO modifyOrder(Long deliveryGuyId, Long orderId, OrderModificationDTO modification);
}
