package luabui.application.utility;

import luabui.application.constants.PaymentMode;
import luabui.application.dto.*;
import luabui.application.exception.FoodItemNotFoundException;
import luabui.application.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MapperUtil {
    private static BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MapperUtil(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public static Customer toCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNo(customerDTO.getPhoneNo());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
//        customer.setPassword(bCryptPasswordEncoder.encode(customerDTO.getPassword()));
        customer.setPassword(customerDTO.getPassword());
        customer.setCreateDate(customerDTO.getCreateDate());
        customer.setIsActive(customerDTO.getIsActive());
        return customer;
    }

    public static CustomerDTO toCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setName(customer.getName());
        customerDTO.setPassword(customer.getPassword());
        customerDTO.setPhoneNo(customer.getPhoneNo());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setOrderIds(getSetOfId(customer.getOrders()));
        customerDTO.setCreateDate(customer.getCreateDate());
        customerDTO.setIsActive(customer.getIsActive());
        customerDTO.setCartId(customer.getCart().getId());
        return customerDTO;
    }

    public static FoodItem toFoodItem(FoodItemDTO foodItemDTO, Restaurant restaurant) {
        FoodItem foodItem = new FoodItem();
        foodItem.setName(foodItemDTO.getName());
        foodItem.setImage(foodItemDTO.getImage());
        foodItem.setPrice(foodItemDTO.getPrice());
        foodItem.setRestaurant(restaurant);
        foodItem.setIsDeleted(foodItemDTO.getIsDeleted());
        return foodItem;
    }

    public static FoodItem toFoodItem2(FoodItemDTO foodItemDTO) {
        FoodItem foodItem = new FoodItem();
        foodItem.setName(foodItemDTO.getName());
        foodItem.setImage(foodItemDTO.getImage());
        foodItem.setPrice(foodItemDTO.getPrice());
        foodItem.setIsDeleted(foodItemDTO.getIsDeleted());
        return foodItem;
    }

    public static FoodItemDTO toFoodItemDTO(FoodItem foodItem) {
        FoodItemDTO foodItemDTO = new FoodItemDTO();
        foodItemDTO.setId(foodItem.getId());
        foodItemDTO.setName(foodItem.getName());
        foodItemDTO.setPrice(foodItem.getPrice());
        foodItemDTO.setRestaurantId(foodItem.getRestaurant().getId());
        foodItemDTO.setIsDeleted(foodItem.getIsDeleted());
        return foodItemDTO;
    }

    public static OrderFoodItem toOrderFoodItem(OrderFoodItemDTO orderFoodItemDTO, FoodItem foodItem, Order order) {
        OrderFoodItem orderFoodItem = new OrderFoodItem();
        orderFoodItem.setFoodItem(foodItem);
        orderFoodItem.setOrder(order);
        orderFoodItem.setPrice(orderFoodItemDTO.getPrice());
        orderFoodItem.setQuantity(orderFoodItemDTO.getQuantity());
        return orderFoodItem;
    }

    public static OrderFoodItemDTO toOrderFoodItemDTO(OrderFoodItem orderFoodItem) {
        OrderFoodItemDTO orderFoodItemDTO = new OrderFoodItemDTO();
        orderFoodItemDTO.setId(orderFoodItem.getId());
        orderFoodItemDTO.setQuantity(orderFoodItem.getQuantity());
        orderFoodItemDTO.setPrice(orderFoodItem.getPrice());
        orderFoodItemDTO.setFoodItemId(orderFoodItem.getFoodItem().getId());
        return orderFoodItemDTO;
    }

    public static OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerId(order.getCustomer().getId());
        orderDTO.setRestaurantId(order.getRestaurant().getId());
        orderDTO.setDeliveryGuyId(order.getDeliveryGuy().getId());
        orderDTO.setOrderStatus(order.getOrderStatus().getDescription());
        orderDTO.setPaymentMode(order.getPaymentMode().getDescription());
        orderDTO.setTimestamp(order.getTimestamp());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setDeliveryAddress(order.getDeliveryAddress());
        Set<OrderFoodItemDTO> orderFoodItemDTOs = order.getOrderFoodItems().stream()
                .map(MapperUtil :: toOrderFoodItemDTO).collect(Collectors.toSet());
        orderDTO.setOrderFoodItemDTOs(orderFoodItemDTOs);
        orderDTO.setId(order.getId());
        return orderDTO;
    }

    public static Order toOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setPaymentMode(PaymentMode.fromDescription(orderDTO.getPaymentMode()));
        order.setTimestamp(LocalDateTime.now());
        return order;
    }

    private static Set<Long> getSetOfId(Set<? extends BaseEntity> objects) {
        return objects.stream().map(object -> object.getId()).collect(Collectors.toSet());
    }

    public static DeliveryGuy toDeliveryGuy(DeliveryGuyDTO deliveryGuyDTO) {
        DeliveryGuy deliveryGuy = new DeliveryGuy();
        deliveryGuy.setName(deliveryGuyDTO.getName());
        deliveryGuy.setEmail(deliveryGuyDTO.getEmail());
        deliveryGuy.setPhoneNo(deliveryGuyDTO.getPhoneNo());
        deliveryGuy.setAddress(deliveryGuyDTO.getAddress());
        deliveryGuy.setPassword(deliveryGuyDTO.getPassword());
        deliveryGuy.setCreateDate(deliveryGuyDTO.getCreateDate());
        deliveryGuy.setIsActive(deliveryGuyDTO.getIsActive());
        deliveryGuy.setIsBusy(deliveryGuyDTO.getIsBusy());
        return deliveryGuy;
    }

    public static DeliveryGuyDTO toDeliveryGuyDTO(DeliveryGuy deliveryGuy) {
        //TODO: use code reuse. this is duplicate code.
        DeliveryGuyDTO deliveryGuyDTO = new DeliveryGuyDTO();
        deliveryGuyDTO.setId(deliveryGuy.getId());
        deliveryGuyDTO.setEmail(deliveryGuy.getEmail());
        deliveryGuyDTO.setName(deliveryGuy.getName());
        deliveryGuyDTO.setPassword(deliveryGuy.getPassword());
        deliveryGuyDTO.setPhoneNo(deliveryGuy.getPhoneNo());
        deliveryGuyDTO.setAddress(deliveryGuy.getAddress());
        deliveryGuyDTO.setCreateDate(deliveryGuy.getCreateDate());
        deliveryGuyDTO.setIsActive(deliveryGuy.getIsActive());
        deliveryGuyDTO.setIsBusy(deliveryGuy.getIsBusy());
        deliveryGuyDTO.setOrderIds(getSetOfId(deliveryGuy.getOrders()));
        return deliveryGuyDTO;
    }

    public static Restaurant toRestaurant(RestaurantDTO restaurantDTO, Category category) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDTO.getName());
        restaurant.setPhoneNo(restaurantDTO.getPhoneNo());
        restaurant.setEmail(restaurantDTO.getEmail());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setPassword(restaurantDTO.getPassword());
        restaurant.setCreateDate(restaurantDTO.getCreateDate());
        restaurant.setIsActive(restaurantDTO.getIsActive());
        restaurant.setAvatar(restaurantDTO.getAvatar());
        restaurant.setCategory(category);
        restaurant.setMaxCost(restaurantDTO.getMaxCost());
        restaurant.setMinCost(restaurantDTO.getMinCost());
        return restaurant;
    }

    public static RestaurantDTO toRestaurantDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setPhoneNo(restaurant.getPhoneNo());
        restaurantDTO.setEmail(restaurant.getEmail());
        restaurantDTO.setPassword(restaurant.getPassword());
        restaurantDTO.setAddress(restaurant.getAddress());
        restaurantDTO.setCreateDate(restaurant.getCreateDate());
        restaurantDTO.setIsActive(restaurant.getIsActive());
        restaurantDTO.setAvatar(restaurant.getAvatar());
        restaurantDTO.setCategoryId(restaurant.getCategory().getId());
        restaurantDTO.setMaxCost(restaurant.getMaxCost());
        restaurantDTO.setMinCost(restaurant.getMinCost());
        restaurantDTO.setOrderIds(getSetOfId(restaurant.getOrders()));
        restaurantDTO.setFoodItemIds(getSetOfId(restaurant.getFoodItems()));
        return restaurantDTO;
    }

    public static CartFoodItem toCartFoodItem(CartFoodItemDTO cartFoodItemDTO, FoodItem foodItem, Cart cart) {
        CartFoodItem cartFoodItem = new CartFoodItem();
        cartFoodItem.setFoodItem(foodItem);
        cartFoodItem.setCart(cart);
        cartFoodItem.setPrice(cartFoodItemDTO.getPrice());
        cartFoodItem.setQuantity(cartFoodItemDTO.getQuantity());
        return cartFoodItem;
    }

    public static CartFoodItemDTO toCartFoodItemDTO(CartFoodItem cartFoodItem) {
        CartFoodItemDTO cartFoodItemDTO = new CartFoodItemDTO();
        cartFoodItemDTO.setId(cartFoodItem.getId());
        cartFoodItemDTO.setQuantity(cartFoodItem.getQuantity());
        cartFoodItemDTO.setPrice(cartFoodItem.getPrice());
        cartFoodItemDTO.setFoodItemId(cartFoodItem.getFoodItem().getId());
        return cartFoodItemDTO;
    }

    public static CartDTO toCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setCustomerId(cart.getCustomer().getId());
        Set<CartFoodItemDTO> cartFoodItemDTOs = cart.getCartFoodItems().stream()
                .map(MapperUtil :: toCartFoodItemDTO).collect(Collectors.toSet());
        cartDTO.setCartFoodItemDTOs(cartFoodItemDTOs);
        return cartDTO;
    }

    public static Cart toCart(CartDTO cartDTO) {
        Cart cart = new Cart();
        return cart;
    }

    public static Admin toAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setName(adminDTO.getName());
        admin.setPhoneNo(adminDTO.getPhoneNo());
        admin.setEmail(adminDTO.getEmail());
        admin.setAddress(adminDTO.getAddress());
        admin.setPassword(adminDTO.getPassword());
        admin.setCreateDate(adminDTO.getCreateDate());
        admin.setIsActive(adminDTO.getIsActive());
        return admin;
    }

    public static AdminDTO toAdminDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setId(admin.getId());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setName(admin.getName());
        adminDTO.setPassword(admin.getPassword());
        adminDTO.setPhoneNo(admin.getPhoneNo());
        adminDTO.setAddress(admin.getAddress());
        adminDTO.setCreateDate(admin.getCreateDate());
        adminDTO.setIsActive(admin.getIsActive());
        return adminDTO;
    }

    public static Category toCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setIsDeleted(categoryDTO.getIsDeleted());
        return category;
    }

    public static CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        Set<RestaurantDTO> restaurantDTOS = category.getRestaurants().stream()
                .map(MapperUtil :: toRestaurantDTO).collect(Collectors.toSet());
        categoryDTO.setRestaurantIds(getSetOfId(category.getRestaurants()));
        categoryDTO.setIsDeleted(category.getIsDeleted());
        return categoryDTO;
    }
}
