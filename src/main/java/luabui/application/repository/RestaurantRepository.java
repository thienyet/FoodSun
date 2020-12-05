package luabui.application.repository;

import luabui.application.model.FoodItem;
import luabui.application.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

//    @Query("select count(res) from Restaurant res where res.address like %:address%")
//    Integer countNumberResByAddress(@Param("address")String address);

    @Query("select res from Restaurant res where res.address like %:address%")
    Page<Restaurant> getRestaurantsByAddressLike(@Param("address")String address, Pageable pageable);

//    @Query("select count(res) from Restaurant res where res.address = :createDate")
//    Integer countNumberResByDate(@Param("createDate")Date createDate);

    @Query("select res from Restaurant res where res.createDate = :createDate")
    Page<Restaurant> getRestaurantsByDate(@Param("createDate")Date createDate, Pageable pageable);

//    @Query("select count(order) from Order order where order.restaurant.id = :restaurantId and date(order.timestamp) = :date")
//    int countNumberOrderOfResInOneDay(@Param("restaurantId")Long restaurantId, @Param("date") Date date);

    @Query("SELECT res FROM Restaurant res")
    Page<Restaurant> findAllInPage(Pageable pageable);
}
