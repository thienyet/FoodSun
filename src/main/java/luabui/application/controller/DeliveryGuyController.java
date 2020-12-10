package luabui.application.controller;

import luabui.application.dto.DeliveryGuyDTO;
import luabui.application.dto.OrderDTO;
import luabui.application.dto.OrderModificationDTO;
import luabui.application.service.DeliveryGuyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
/**
 * Controller to map all delivery guy related operations.
 */
public class DeliveryGuyController {
    private DeliveryGuyService deliveryGuyService;

    @Autowired
    public DeliveryGuyController(DeliveryGuyService deliveryGuyService) {
        this.deliveryGuyService = deliveryGuyService;
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
     * Returns list of orders assigned to delivery guy with given id.
     *
     * @param deliveryGuyId
     * @return
     */
    @GetMapping(value = "/deliveryguys/{deliveryGuyId}/orders")
    public ResponseEntity<List<OrderDTO>> getDeliveryGuyOrders(@PathVariable Long deliveryGuyId) {
        log.debug("Getting Orders assigned to Delivery Guy.");
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyService.getDeliveryGuyOrders(deliveryGuyId));
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

    /**
     * Modifies a delivery guy with given id in the database.
     *
     * @param deliveryGuyDTO
     * @param deliveryGuyId
     * @return
     */
    @PutMapping(value = "/deliveryguys/{deliveryGuyId}")
    public ResponseEntity<DeliveryGuyDTO> updateDeliveryGuy(@Valid @RequestBody DeliveryGuyDTO deliveryGuyDTO, @PathVariable Long deliveryGuyId) {
        log.debug("Updating Delivery Guy by id.");
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyService.update(deliveryGuyDTO, deliveryGuyId));
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
}
