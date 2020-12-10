package luabui.application.controller;

import luabui.application.dto.OrderDTO;
import luabui.application.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
/**
 * Controller to perform order related components.
 */
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create a order and persists in database.
     * Takes:
     * customerId,
     * restaurantId,
     * etc..
     *
     * @param orderDTO
     * @return
     */
    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        log.debug("Creating new Order.");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder(orderDTO));
    }
}
