package luabui.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "order_food_item")
public class OrderFoodItem extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference(value = "order-orderFoodItems")
    private Order order;


    private Integer quantity;
    private Double totalPrice;
}
