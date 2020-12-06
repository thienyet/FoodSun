package luabui.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Cart")
@Table(name = "cart")
public class Cart extends BaseEntity {

    @OneToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "cart")
    private Set<CartFoodItem> cartFoodItems = new HashSet<>();

    public Cart(Customer customer) {
        this.customer  = customer;
    }
    public Cart(){};
}
