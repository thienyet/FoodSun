package luabui.application.repository;

import luabui.application.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;


public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select od from Order od where od.restaurant.id = :restaurant")
    Page<Order> findByRestaurantId(@Param("restaurant")Long restaurantId, Pageable pageable);

    @Query("select od from Order od where od.restaurant.id = :restaurant and Date(od.timestamp) = :date")
    Page<Order> findByRestaurantIdAndDate(@Param("restaurant") Long restaurantId, @Param("date") Date date, Pageable pageable);

    @Query("select od from Order od where od.customer.id = :customer")
    Page<Order> findByCustomerId(@Param("customer")Long customerId, Pageable pageable);

    @Query("select od from Order od where od.customer.id = :customer and Date(od.timestamp) = :date")
    Page<Order> findByCustomerIdAndDate(@Param("customer") Long customerId, @Param("date") Date date, Pageable pageable);

    @Query("select od from Order od where od.deliveryGuy.id = :delivery")
    Page<Order> findByDeliveryGuyId(@Param("delivery")Long deliveryId, Pageable pageable);

    @Query("select od from Order od where od.deliveryGuy.id = :delivery and Date(od.timestamp) = :date")
    Page<Order> findByDeliveryGuyIdAndDate(@Param("delivery") Long deliveryId, @Param("date") Date date, Pageable pageable);
}
