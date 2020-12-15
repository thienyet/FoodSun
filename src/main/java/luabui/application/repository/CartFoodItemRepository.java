package luabui.application.repository;

import luabui.application.model.CartFoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartFoodItemRepository extends JpaRepository<CartFoodItem, Long> {

}
