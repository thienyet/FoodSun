package luabui.application.service;


import luabui.application.dto.FoodItemDTO;
import luabui.application.model.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodItemService extends CrudService<FoodItem, Long> {
    Page<FoodItem> getRestaurantFoodItems(Long restaurantId, Pageable pageable);

//    FoodItem getFoodItemByResIdAndFoodId(Long restaurantId, Long foodItemId);
}
