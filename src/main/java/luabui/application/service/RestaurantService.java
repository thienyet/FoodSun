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

}
