package luabui.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import luabui.application.constants.OrderStatus;
import luabui.application.constants.PaymentMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Order")
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference(value = "user-orders")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "delivery_guy_id")
    @JsonBackReference(value = "delivery-guy-orders")
    private DeliveryGuy deliveryGuy;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference(value = "restaurant-orders")
    private Restaurant restaurant;

    @Column
    private LocalDateTime timestamp;

    @Column
    private PaymentMode paymentMode;

    @Column
    private OrderStatus orderStatus;

    @Column
    private Double totalPrice;

    @Column
    private String deliveryAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "order-orderFoodItems")
    private Set<OrderFoodItem> orderFoodItems = new HashSet<>();
}
