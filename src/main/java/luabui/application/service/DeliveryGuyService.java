package luabui.application.service;

import luabui.application.dto.DeliveryGuyDTO;
import luabui.application.dto.OrderDTO;
import luabui.application.dto.OrderModificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface DeliveryGuyService extends CrudService<DeliveryGuyDTO, Long> {
    List<OrderDTO> getDeliveryGuyOrders(Long deliveryGuyId);

    OrderDTO getDeliveryGuyOrderById(Long deliveryGuyId, Long orderId);

    DeliveryGuyDTO update(DeliveryGuyDTO deliveryGuyDTO, Long deliveryGuyId);

    OrderDTO modifyOrder(Long deliveryGuyId, Long orderId, OrderModificationDTO modification);

    Page<DeliveryGuyDTO> getDeliveryGuyByDate(Date createDate, Pageable pageable);

    Page<DeliveryGuyDTO> getDeliveryGuyByAddress(String address, Pageable pageable);

    Page<DeliveryGuyDTO> findAll(Pageable pageable);
}
