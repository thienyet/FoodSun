package luabui.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Restaurant extends GeneralDetails {

    @Column(length = 255)
    private String avatar;

    @Column
    private Double maxCost;

    @Column
    private Double minCost;

    @Column
    private Double ratingValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference(value = "category-restaurants")
    private Category category;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
//    @JsonManagedReference(value = "restaurant-foodItems")
    private Set<FoodItem> foodItems = new HashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "restaurant-orders")
    private Set<Order> orders = new HashSet<>();
}
