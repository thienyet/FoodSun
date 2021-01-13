package luabui.application.service;



import luabui.application.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface RestaurantService extends CrudService<RestaurantDTO, Long> {
    RestaurantDTO addFoodItems(Long restaurantId, List<FoodItemDTO> foodItemDTOs);

    RestaurantDTO removeFoodItems(Long restaurantId, List<Long> foodItemIds);

    List<OrderDTO> getRestaurantOrders(Long restaurantId);

    OrderDTO getRestaurantOrderById(Long restaurantId, Long orderId);

    OrderDTO modifyOrder(Long restaurantId, Long orderId, OrderModificationDTO modification);

    Page<RestaurantDTO> findRestaurantByAddressLike(String address, Pageable pageable);

    RestaurantDTO update(RestaurantDTO restaurantDTO, Long restaurantId);

    Page<RestaurantDTO> getRestaurantByDate(Date createDate, Pageable pageable);

    Page<RestaurantDTO> findAll(Pageable pageable);

//    RestaurantDTO getRestaurantById(Long restaurantId);
    RestaurantDTO findByEmail(String email);

    Page<RestaurantDTO> getRestaurantByName(String name, Pageable pageable);

    Page<RestaurantDTO> getRestaurantByCategory(Long categoryId, Pageable pageable);

    Page<RestaurantDTO> getRestaurantByFoodItemName(String name, Pageable pageable);

    RestaurantDTO changeStatus(Long restaurantId);

    Page<FoodItemDTO> getFoodItemByRestaurantId(Long restaurantId, Pageable pageable);

    FoodItemDTO findFoodItemById(Long fooditemId);

    Page<FoodItemDTO> getFoodItemByName(String name, Pageable pageable);

    Page<FoodItemDTO> getFoodItemByNameAndResId(Long restaurantID, String name, Pageable pageable);

    FoodItemDTO editFoodItem(Long fooditemId, FoodItemDTO foodItemDTO);

    FoodItemDTO updateStatus(Long foodItemId);

    FoodItemDTO addFoodItem(Long restaurantId, FoodItemDTO foodItemDTO);

    Page<OrderDTO> getOrdersByRestaurantId(Long restaurantId, Pageable pageable);

    Page<OrderDTO> getOrdersByRestaurantIdAndDate(Long restaurantId, Date date, Pageable pageable);

}
