package luabui.application.controller;

import luabui.application.dto.DeliveryGuyDTO;
import luabui.application.dto.OrderDTO;
import luabui.application.dto.OrderModificationDTO;
import luabui.application.dto.RestaurantDTO;
import luabui.application.service.DeliveryGuyService;
import lombok.extern.slf4j.Slf4j;
import luabui.application.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
/**
 * Controller to map all delivery guy related operations.
 */
public class DeliveryGuyController {
    private DeliveryGuyService deliveryGuyService;
    private OrderService orderService;

    @Autowired
    public DeliveryGuyController(DeliveryGuyService deliveryGuyService, OrderService orderService) {
        this.deliveryGuyService = deliveryGuyService;
        this.orderService = orderService;
    }

    /**
     * Returns list of all delivery guys.
     *
     * @return
     */
    @GetMapping(value = "/deliveryguys")
    public ResponseEntity<List<DeliveryGuyDTO>> getAllDeliveryGuys() {
        log.debug("Getting all Delivery Guys.");
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyService.findAll());
    }

    /**
     * Returns a delivery guy with given id.
     *
     * @param deliveryGuyId
     * @return
     */
    @GetMapping(value = "/deliveryguys/{deliveryGuyId}")
    public ResponseEntity<DeliveryGuyDTO> getDeliveryGuy(@PathVariable Long deliveryGuyId) {
        log.debug("Getting Delivery Guy by id.");
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyService.findById(deliveryGuyId));
    }

    /**
     * Persists a delivery guy in the database.
     *
     * @param deliveryGuyDTO
     * @return
     */
    @PostMapping(value = "/foodsun/signup/deliveryguys")
    public ResponseEntity<DeliveryGuyDTO> saveDeliveryGuy(@Valid @RequestBody DeliveryGuyDTO deliveryGuyDTO) {
        log.debug("Saving Delivery Guy.");
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryGuyService.save(deliveryGuyDTO));
    }

    @GetMapping(value = "/deliveryguys/profile")
    public ResponseEntity<DeliveryGuyDTO> getProfile(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyService.findByEmail(email));
    }

    /**
     * Modifies a delivery guy with given id in the database.
     *
     * @param deliveryGuyDTO
     * @param deliveryGuyId
     * @return
     */
    @PutMapping(value = "/deliveryguys/edit/{deliveryGuyId}")
    public ResponseEntity<DeliveryGuyDTO> updateDeliveryGuy(@Valid @RequestBody DeliveryGuyDTO deliveryGuyDTO, @PathVariable Long deliveryGuyId) {
        log.debug("Updating Delivery Guy by id.");
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyService.update(deliveryGuyDTO, deliveryGuyId));
    }

    /**
     * Returns list of orders assigned to delivery guy with given id.
     *
     * @param deliveryGuyId
     * @return
     */
    @GetMapping(value = "/deliveryguys/{deliveryGuyId}/orders")
    public ResponseEntity<Page<OrderDTO>> getDeliveryGuyOrders(@PathVariable Long deliveryGuyId,
                                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting Orders assigned to Delivery Guy.");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderDTO> list = deliveryGuyService.getOrdersByDeliveryId(deliveryGuyId, request);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping(value = "/deliveryguys/{deliveryGuyId}/orders/date/{date}")
    public ResponseEntity<Page<OrderDTO>> getDeliveryGuyOrders(@PathVariable Long deliveryGuyId, @PathVariable Date date,
                                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "3") Integer size) {
        log.debug("Getting Orders assigned to Delivery Guy.");
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderDTO> list = deliveryGuyService.getOrdersByDeliveryIdAndDate(deliveryGuyId, date, request);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * Returns a order by given id assigned to delivery guy with given id.
     *
     * @param deliveryGuyId
     * @param orderId
     * @return
     */
    @GetMapping(value = "/deliveryguys/{deliveryGuyId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> getDeliveryGuyOrderById(@PathVariable Long deliveryGuyId, @PathVariable Long orderId) {
        log.debug("Getting Order assigned to Delivery Guy using given OrderId.");
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyService.getDeliveryGuyOrderById(deliveryGuyId, orderId));
    }

    /**
     * Modify a specific order status as delivered/picked up by delivery guy.
     *
     * @param deliveryGuyId
     * @param orderId
     * @param modification
     * @return
     */
    @PutMapping(value = "/deliveryguys/{deliveryGuyId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> modifyOrder(@PathVariable Long deliveryGuyId, @PathVariable Long orderId, @Valid @RequestBody OrderModificationDTO modification) {
        log.debug("Modifying Delivery Guy Order.");
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyService.modifyOrder(deliveryGuyId, orderId, modification));
    }

    /**
     * Delete a delivery guy entry from database belonging to given id.
     *
     * @param deliveryGuyId
     * @return
     */
    @DeleteMapping(value = "/deliveryguys/{deliveryGuyId}")
    public ResponseEntity<?> deleteDeliveryGuyById(@PathVariable Long deliveryGuyId) {
        log.debug("Deleting Delivery Guy by id.");
        deliveryGuyService.deleteById(deliveryGuyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/deliveryguys/{deliveryGuyId}/revenue/{month}")
    public ResponseEntity<Double> getRevenue(@PathVariable Long deliveryGuyId, @PathVariable Integer month) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getRevenueDelivery(deliveryGuyId, month));
    }
}
