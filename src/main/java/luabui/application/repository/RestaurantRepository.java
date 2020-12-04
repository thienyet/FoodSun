package luabui.application.repository;

import luabui.application.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select res from Restaurant res where res.address like %:address%")
    List<Restaurant> getRestaurantsByAddressLike(@Param("address")String address);

    @Query("select res from Restaurant res where res.createDate = :createDate")
    List<Restaurant> getRestaurantsByDate(@Param("createDate")Date createDate);
}
