package luabui.application.service.impl;

import luabui.application.constants.OrderStatus;
import luabui.application.dto.FoodItemDTO;
import luabui.application.dto.OrderDTO;
import luabui.application.dto.OrderModificationDTO;
import luabui.application.dto.RestaurantDTO;
import luabui.application.exception.OrderNotFoundException;
import luabui.application.exception.OrderStatusException;
import luabui.application.exception.RestaurantNotFoundException;
import luabui.application.model.FoodItem;
import luabui.application.model.Order;
import luabui.application.model.Restaurant;
import luabui.application.repository.FoodItemRepository;
import luabui.application.repository.OrderRepository;
import luabui.application.repository.RestaurantRepository;
import luabui.application.service.RestaurantService;
import luabui.application.utility.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService {
    private RestaurantRepository restaurantRepository;
    private FoodItemRepository foodItemRepository;
    private OrderRepository orderRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, FoodItemRepository foodItemRepository, OrderRepository orderRepository) {
        this.restaurantRepository = restaurantRepository;
        this.foodItemRepository = foodItemRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public RestaurantDTO addFoodItems(Long restaurantId, List<FoodItemDTO> foodItemDTOs) {
        Restaurant restaurant = getRestaurant(restaurantId);
        List<FoodItem> foodItems = foodItemDTOs.stream().map(foodItemDTO -> MapperUtil.toFoodItem(foodItemDTO, restaurant)).collect(Collectors.toList());
        foodItemRepository.saveAll(foodItems);
        return MapperUtil.toRestaurantDTO(restaurant);
    }

    @Override
    public RestaurantDTO removeFoodItems(Long restaurantId, List<Long> foodItemIds) {
        Restaurant restaurant = getRestaurant(restaurantId);
        log.debug("Removing Food Items from Restaurant" + restaurant.getName() + " from Service. ");
        List<FoodItem> foodItems = foodItemRepository.findAllById(foodItemIds);
        foodItemRepository.deleteAll(foodItems);
        return MapperUtil.toRestaurantDTO(restaurant);
    }

    @Override
    public List<OrderDTO> getRestaurantOrders(Long restaurantId) {
        log.debug("Getting orders from Restaurant.");
        Restaurant restaurant = getRestaurant(restaurantId);
        return restaurant.getOrders().stream().map(MapperUtil :: toOrderDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getRestaurantOrderById(Long restaurantId, Long orderId) {
        Restaurant restaurant = getRestaurant(restaurantId);
        Order order = getOrder(orderId);
        if (!restaurant.getOrders().contains(order)) {
            throw new RestaurantNotFoundException(restaurantId);
        }
        return MapperUtil.toOrderDTO(order);
    }

    @Override
    public List<FoodItemDTO> getRestaurantFoodItems(Long restaurantId) {
        log.debug("Getting all Food Items from Restaurant");
        Restaurant restaurant = getRestaurant(restaurantId);
        return restaurant.getFoodItems().stream().map(MapperUtil :: toFoodItemDTO).collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDTO> findRestaurantByArea(String area) {
        log.debug("Find Restaurants By Area.");
        List<Restaurant> restaurants = restaurantRepository.getRestaurantsByAddress(area);
        return restaurants.stream().map(MapperUtil :: toRestaurantDTO).collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDTO> findAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(MapperUtil :: toRestaurantDTO).collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO findById(Long restaurantId) {
        log.debug("Find Restaurant By Id.");
        Restaurant restaurant = getRestaurant(restaurantId);
        return MapperUtil.toRestaurantDTO(restaurant);
    }

    @Override
    public RestaurantDTO save(RestaurantDTO restaurantDTO) {
        log.debug("Saving Restaurant from Service.");
        Restaurant restaurant = restaurantRepository.save(MapperUtil.toRestaurant(restaurantDTO));
        return MapperUtil.toRestaurantDTO(restaurant);
    }

    @Override
    public void deleteById(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }


    @Override
    public OrderDTO modifyOrder(Long restaurantId, Long orderId, OrderModificationDTO modification) {
        //TODO: check working.
        Restaurant restaurant = getRestaurant(restaurantId);
        Order order = getOrder(orderId);

        if (!restaurant.getOrders().contains(order)) {
            throw new RestaurantNotFoundException(restaurantId);
        }

        OrderStatus orderStatus = OrderStatus.fromDescription(modification.getOrderStatus());
//        PaymentMode paymentMode = PaymentMode.fromDescription(modification.getPaymentMode());

//        if (paymentMode != null) {
//            throw new PaymentModeException("Restaurant cannot change Payment Mode.");
//        }

        if (order.getOrderStatus().getDescription().equals("approved")) {
            order.setOrderStatus(orderStatus);
        } else {
            throw new OrderStatusException("Restaurant cannot change status from " + order.getOrderStatus() + " to " + modification.getOrderStatus());
        }
        return MapperUtil.toOrderDTO(orderRepository.save(order));
    }

    private Restaurant getRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }

}