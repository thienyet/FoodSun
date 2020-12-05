package luabui.application.utility;

import luabui.application.constants.PaymentMode;
import luabui.application.dto.*;
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
        return customerDTO;
    }

    public static FoodItem toFoodItem(FoodItemDTO foodItemDTO, Restaurant restaurant) {
        FoodItem foodItem = new FoodItem();
        foodItem.setName(foodItemDTO.getName());
        foodItem.setPrice(foodItemDTO.getPrice());
        foodItem.setRestaurant(restaurant);
        return foodItem;
    }

    public static FoodItemDTO toFoodItemDTO(FoodItem foodItem) {
        FoodItemDTO foodItemDTO = new FoodItemDTO();
        foodItemDTO.setId(foodItem.getId());
        foodItemDTO.setName(foodItem.getName());
        foodItemDTO.setPrice(foodItem.getPrice());
        foodItemDTO.setRestaurantId(foodItem.getRestaurant().getId());
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
        deliveryGuyDTO.setOrderIds(getSetOfId(deliveryGuy.getOrders()));
        return deliveryGuyDTO;
    }

    public static Restaurant toRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDTO.getName());
        restaurant.setPhoneNo(restaurantDTO.getPhoneNo());
        restaurant.setEmail(restaurantDTO.getEmail());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setPassword(restaurantDTO.getPassword());
        restaurant.setCreateDate(restaurantDTO.getCreateDate());
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
        restaurantDTO.setOrderIds(getSetOfId(restaurant.getOrders()));
        restaurantDTO.setFoodItemIds(getSetOfId(restaurant.getFoodItems()));
        return restaurantDTO;
    }


}
