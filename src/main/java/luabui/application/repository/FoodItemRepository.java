package luabui.application.repository;

import luabui.application.model.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    @Query("SELECT fooditem FROM FoodItem fooditem where fooditem.restaurant.id = :resId")
    Page<FoodItem> findAllInPage(@Param("resId")Long resId, Pageable pageable);

//    @Query("SELECT fooditem FROM FoodItem fooditem where fooditem.restaurant.id = :resId and fooditem.id = :foodId")
//    Page<FoodItem> findAllInPage(@Param("resId")Long resId, @Param("foodId")Long foodId);
}
