package luabui.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "FoodItem")
@Table(name = "food_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class FoodItem extends BaseEntity {
    @Column(length = 255)
    private String name;

    @Column
    private Double price;

    @Column(length = 255)
    private String image;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference(value = "restaurant-foodItems")
    private Restaurant restaurant;
}
