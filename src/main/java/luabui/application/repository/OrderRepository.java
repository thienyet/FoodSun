package luabui.application.repository;

import luabui.application.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;


public interface OrderRepository extends JpaRepository<Order, Long> {

//    @Query("select order from Order order where  order.restaurant.id = :restaurantId and order.timestamp = :date")
//    Page<Order> getOrderOfResInOneDat(@Param("restaurantId")Long restaurantId, @Param("date") Date date, Pageable pageable);

}
