package luabui.application.repository;

import luabui.application.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> getRestaurantsByAddress(String area);
}
