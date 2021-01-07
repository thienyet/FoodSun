package luabui.application.repository;

import luabui.application.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;


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

    @Query("select od from Order od where od.orderStatus = 'delivered' and month(od.timestamp) = :month")
    List<Order> getRevenueAdmin(@Param("month") Integer month);

    @Query("select od from Order od where od.orderStatus = 'delivered' and od.restaurant.id = :restaurant and month(od.timestamp) = :month")
    List<Order> getRevenueRestaurant(@Param("restaurant") Long restaurantId, @Param("month") Integer month);

    @Query("select od from Order od where od.orderStatus = 'delivered' and od.deliveryGuy.id = :delivery and month(od.timestamp) = :month")
    List<Order> getRevenueDelivery(@Param("delivery") Long deliveryId, @Param("month") Integer month);

//    @Query(value = "select od.restaurant.id, count(od.restaurant.id) as countOrder from Order od group by od.restaurant.id order by countOrder desc")
//    List<ModelStatistic> getMostRestaurant(@Param("month") Integer month, Pageable pageable);
//    @Query(value = "select new StatisticDTO (odfi.foodItem.id as id, count(odfi.foodItem.id) as number) from OrderFoodItem as odfi, Order as od " +
//            "where odfi.order.id = od.id and od.restaurant.id = :restaurant and month(od.timestamp) = :month group by odfi.foodItem.id order by number desc")
//    Page<StatisticDTO> getMostFood(@Param("month") Integer month, @Param("restaurant") Long restaurantId, Pageable pageable);
}
