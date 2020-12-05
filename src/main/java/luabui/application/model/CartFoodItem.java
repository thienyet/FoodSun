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
@Table(name = "cart_food_item")
public class CartFoodItem extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference(value = "cart-cartFoodItems")
    private Cart cart;

    private Integer quantity;
    private Double price;
}
