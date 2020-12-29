package luabui.application.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "delivery_guy", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class DeliveryGuy extends GeneralDetails {

    @Column
    private Boolean isBusy;

    @OneToMany(mappedBy = "deliveryGuy", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "delivery-guy-orders")
    private Set<Order> orders = new HashSet<>();
}
