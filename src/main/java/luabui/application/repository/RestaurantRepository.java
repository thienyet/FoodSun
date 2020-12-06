package luabui.application.repository;

import luabui.application.model.Order;
import luabui.application.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select res from Restaurant res where res.address like %:address%")
    Page<Restaurant> getRestaurantsByAddressLike(@Param("address")String address, Pageable pageable);

    @Query("select res from Restaurant res where res.createDate = :createDate")
    Page<Restaurant> getRestaurantsByDate(@Param("createDate")Date createDate, Pageable pageable);

    @Query("SELECT res FROM Restaurant res")
    Page<Restaurant> findAllInPage(Pageable pageable);

}
