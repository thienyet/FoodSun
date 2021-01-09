package luabui.application.service.impl;

import luabui.application.constants.OrderStatus;
import luabui.application.dto.*;
import luabui.application.exception.NotFoundException;
import luabui.application.exception.OrderNotFoundException;
import luabui.application.exception.OrderStatusException;
import luabui.application.exception.RestaurantNotFoundException;
import luabui.application.model.*;
import luabui.application.repository.*;
import luabui.application.service.RestaurantService;
import luabui.application.utility.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService {
    private RestaurantRepository restaurantRepository;
    private FoodItemRepository foodItemRepository;
    private OrderRepository orderRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private DeliveryGuyRepository deliveryGuyRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, FoodItemRepository foodItemRepository,
                                 OrderRepository orderRepository, RoleRepository roleRepository,
                                 UserRepository userRepository, CategoryRepository categoryRepository,
                                 DeliveryGuyRepository deliveryGuyRepository) {
        this.restaurantRepository = restaurantRepository;
        this.foodItemRepository = foodItemRepository;
        this.orderRepository = orderRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.categoryRepository =  categoryRepository;
        this.deliveryGuyRepository = deliveryGuyRepository;
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

//    @Override
//    public Page<FoodItemDTO> getRestaurantFoodItems(Long restaurantId, Pageable pageable) {
//        log.debug("Getting all Food Items from Restaurant");
//        Restaurant restaurant = getRestaurant(restaurantId);
//        Page<FoodItem> foodItemPage = restaurantRepository.
//    }

    @Override
    public Page<RestaurantDTO> findRestaurantByAddressLike(String address, Pageable pageable) {
        log.debug("Find Restaurants By Address.");
        Page<Restaurant> pageRestaurant = restaurantRepository.getRestaurantsByAddressLike(address, pageable);
        Page<RestaurantDTO> dtoPageRestaurant = pageRestaurant.map(MapperUtil :: toRestaurantDTO);
        return dtoPageRestaurant;
    }

    @Override
    public RestaurantDTO update(RestaurantDTO restaurantDTO, Long restaurantId) {
        log.debug("Updating Restaurant.");
        Restaurant restaurant = getRestaurant(restaurantId);
        User user = userRepository.findByEmail(restaurant.getEmail());
        restaurant.setEmail(restaurantDTO.getEmail());
        restaurant.setPhoneNo(restaurantDTO.getPhoneNo());
        restaurant.setName(restaurantDTO.getName());
        restaurant.setPassword(restaurantDTO.getPassword());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setAvatar(restaurantDTO.getAvatar());
        Category category = categoryRepository.findById(restaurantDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("Not Found category id"));
        restaurant.setCategory(category);
        restaurant.setMaxCost(restaurantDTO.getMaxCost());
        restaurant.setMinCost(restaurantDTO.getMinCost());

        restaurantRepository.save(restaurant);

        user.setEmail(restaurant.getEmail());
        user.setPassword(restaurant.getPassword());
        userRepository.saveAndFlush(user);

        return MapperUtil.toRestaurantDTO(restaurant);
    }

    @Override
    public Page<RestaurantDTO> getRestaurantByDate(Date createDate, Pageable pageable) {
        Page<Restaurant> pageRestaurant = restaurantRepository.getRestaurantsByDate(createDate, pageable);
        Page<RestaurantDTO> dtoPageRestaurant = pageRestaurant.map(MapperUtil :: toRestaurantDTO);
        return dtoPageRestaurant;
    }

    @Override
    public Page<RestaurantDTO> findAll(Pageable pageable) {
        Page<Restaurant> pageRestaurant = restaurantRepository.findAllInPage(pageable);
        Page<RestaurantDTO> dtoPageRestaurant = pageRestaurant.map(MapperUtil :: toRestaurantDTO);
        return dtoPageRestaurant;
    }

    @Override
    public RestaurantDTO findByEmail(String email) {
        Restaurant restaurant = restaurantRepository.findByEmail(email);
        return MapperUtil.toRestaurantDTO(restaurant);
    }

    @Override
    public Page<RestaurantDTO> getRestaurantByName(String name, Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantRepository.getRestaurantsByName(name, pageable);
        Page<RestaurantDTO> restaurantDTOPage = restaurantPage.map(MapperUtil :: toRestaurantDTO);
        return restaurantDTOPage;
    }

    @Override
    public Page<RestaurantDTO> getRestaurantByCategory(Long categoryId, Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantRepository.getRestaurantsByCategory(categoryId, pageable);
        Page<RestaurantDTO> restaurantDTOPage = restaurantPage.map(MapperUtil :: toRestaurantDTO);
        return restaurantDTOPage;
    }

    @Override
    public Page<RestaurantDTO> getRestaurantByFoodItemName(String name, Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantRepository.getRestaurantByFoodItemName(name, pageable);
        Page<RestaurantDTO> restaurantDTOPage = restaurantPage.map(MapperUtil :: toRestaurantDTO);
        return restaurantDTOPage;
    }

    @Override
    public RestaurantDTO changeStatus(Long restaurantId) {
        Restaurant restaurant = getRestaurant(restaurantId);
        User user = userRepository.findByEmail(restaurant.getEmail());
        restaurant.setIsActive(!restaurant.getIsActive());
        restaurantRepository.save(restaurant);

        user.setIsActive(restaurant.getIsActive());
        userRepository.saveAndFlush(user);
        return MapperUtil.toRestaurantDTO(restaurant);
    }

    @Override
    public Page<FoodItemDTO> getFoodItemByRestaurantId(Long restaurantId, Pageable pageable) {
        Page<FoodItem> foodItemPage = foodItemRepository.findAllInPage(restaurantId, pageable);
        Page<FoodItemDTO> foodItemDTOPage = foodItemPage.map(MapperUtil :: toFoodItemDTO);
        return foodItemDTOPage;
    }

    @Override
    public FoodItemDTO findFoodItemById(Long fooditemId) {
        FoodItem foodItem = foodItemRepository.findById(fooditemId).orElseThrow(() -> new NotFoundException("Not Found"));
        return MapperUtil.toFoodItemDTO(foodItem);
    }

    @Override
    public Page<FoodItemDTO> getFoodItemByName(String name, Pageable pageable) {
        Page<FoodItem> foodItemPage = foodItemRepository.findByName(name, pageable);
        Page<FoodItemDTO> foodItemDTOPage = foodItemPage.map(MapperUtil :: toFoodItemDTO);
        return foodItemDTOPage;
    }

    @Override
    public FoodItemDTO editFoodItem(Long fooditemId, FoodItemDTO foodItemDTO) {
        FoodItem foodItem = foodItemRepository.findById(fooditemId).orElseThrow(() -> new NotFoundException("Not Found"));
        foodItem.setName(foodItemDTO.getName());
        foodItem.setPrice(foodItemDTO.getPrice());
        foodItem.setImage(foodItemDTO.getImage());
        foodItemRepository.save(foodItem);
        return MapperUtil.toFoodItemDTO(foodItem);
    }

    @Override
    public FoodItemDTO updateStatus(Long foodItemId) {
        FoodItem foodItem = foodItemRepository.findById(foodItemId).orElseThrow(() -> new NotFoundException("Not Found"));
        foodItem.setIsDeleted(true);
        foodItemRepository.save(foodItem);
        return MapperUtil.toFoodItemDTO(foodItem);
    }

    @Override
    public FoodItemDTO addFoodItem(Long restaurantId, FoodItemDTO foodItemDTO) {
        Restaurant restaurant = getRestaurant(restaurantId);
        FoodItem foodItem = foodItemRepository.save(MapperUtil.toFoodItem(foodItemDTO, restaurant));
        return MapperUtil.toFoodItemDTO(foodItem);
    }

    @Override
    public Page<OrderDTO> getOrdersByRestaurantId(Long restaurantId, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findByRestaurantId(restaurantId, pageable);
        Page<OrderDTO> orderDTOPage = orderPage.map(MapperUtil :: toOrderDTO);
        return orderDTOPage;
    }

    @Override
    public Page<OrderDTO> getOrdersByRestaurantIdAndDate(Long restaurantId, Date date, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findByRestaurantIdAndDate(restaurantId, date, pageable);
        Page<OrderDTO> orderDTOPage = orderPage.map(MapperUtil :: toOrderDTO);
        return orderDTOPage;
    }


//    @Override
//    public Page<OrderDTO> getOrderOfResInOneDay(Long restaurantId, Date date, Pageable pageable) {
//        Page<Order> orderPage = restaurantRepository.getOrderOfResInOneDat(restaurantId, (Date) date, pageable);
//        Page<OrderDTO> orderDTOPage = orderPage.map(MapperUtil :: toOrderDTO);
//        return orderDTOPage;
//    }

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
        Category category = categoryRepository.findById(restaurantDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("Not Found"));
        Restaurant restaurant = restaurantRepository.save(MapperUtil.toRestaurant(restaurantDTO, category));
        createUser(restaurant);
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
            if(orderStatus.equals("cancelled")) {
                DeliveryGuy deliveryGuy = deliveryGuyRepository.findById(order.getDeliveryGuy().getId()).orElseThrow(() -> new NotFoundException("Not found"));
                deliveryGuy.setIsBusy(false);
                deliveryGuyRepository.save(deliveryGuy);
            }
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

    private void createUser(Restaurant restaurant) {
        User user = new User();
        user.setEmail(restaurant.getEmail());
        user.setPassword(restaurant.getPassword());
        user.setRole(roleRepository.findByRole("RESTAURANT"));
        userRepository.save(user);
    }
}