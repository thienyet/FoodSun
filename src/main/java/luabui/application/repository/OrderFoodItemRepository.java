package luabui.application.repository;

import luabui.application.model.OrderFoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderFoodItemRepository extends JpaRepository<OrderFoodItem, Long> {
}
