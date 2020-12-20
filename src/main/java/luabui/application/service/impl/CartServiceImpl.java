//package luabui.application.service.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import lombok.var;
//import luabui.application.dto.*;
//import luabui.application.exception.FoodItemNotFoundException;
//import luabui.application.exception.NotFoundException;
//import luabui.application.model.Cart;
//import luabui.application.model.CartFoodItem;
//import luabui.application.model.Customer;
//import luabui.application.model.FoodItem;
//import luabui.application.repository.*;
//import luabui.application.service.CartFoodItemService;
//import luabui.application.service.CartService;
//import luabui.application.service.CustomerService;
//import luabui.application.service.OrderService;
//import luabui.application.utility.MapperUtil;
//import luabui.application.vo.request.FoodItemForm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//public class CartServiceImpl implements CartService {
//
//    private CartRepository cartRepository;
//    private CustomerRepository customerRepository;
//    private CartFoodItemRepository cartFoodItemRepository;
//    private OrderRepository orderRepository;
//    private FoodItemRepository foodItemRepository;
//    private OrderService orderService;
//
//    @Autowired
//    public CartServiceImpl(CartRepository cartRepository, CustomerRepository customerRepository, CartFoodItemRepository cartFoodItemRepository, OrderRepository orderRepository, FoodItemRepository foodItemRepository,
//                           OrderService orderService) {
//        this.customerRepository = customerRepository;
//        this.cartFoodItemRepository = cartFoodItemRepository;
//        this.orderRepository = orderRepository;
//        this.foodItemRepository = foodItemRepository;
//        this.orderService = orderService;
//    }
//
//    @Override
//    public List<CartDTO> findAll() {
//        return null;
//    }
//
//    @Override
//    public CartDTO findById(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public CartDTO save(CartDTO newObject) {
//        return null;
//    }
//
//    @Override
//    public void deleteById(Long aLong) {
//
//    }
//
//
//    @Override
//    public CartDTO getCart(CustomerDTO customerDTO) {
//        Long cartId = customerDTO.getCartId();
//        Cart cart =  cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Not Found"));
//        return MapperUtil.toCartDTO(cart);
//    }
//
//    @Override
//    @Transactional
//    public void mergeLocalCart(List<CartFoodItem> cartFoodItems, CustomerDTO customerDTO) {
//        Customer customer = MapperUtil.toCustomer(customerDTO);
//        Cart finalCart = customer.getCart();
//        cartFoodItems.forEach(cartFoodItem -> {
//            Set<CartFoodItem> set = finalCart.getCartFoodItems();
//            Optional<CartFoodItem> old =set.stream().filter(e -> e.getFoodItem().getId().equals(cartFoodItem.getId())).findFirst();
//            CartFoodItem cfi;
//            if(old.isPresent()) {
//                cfi = old.get();
//                cfi.setQuantity(cartFoodItem.getQuantity() + cfi.getQuantity());
//            } else {
//                cfi = cartFoodItem;
//                cfi.setCart(finalCart);
//                finalCart.getCartFoodItems().add(cfi);
//            }
//            cartFoodItemRepository.save(cfi);
//        });
//        cartRepository.save(finalCart);
//    }
//
//    @Override
//    @Transactional
//    public void delete(Long foodItemID, CustomerDTO customerDTO) {
//        Customer customer = MapperUtil.toCustomer(customerDTO);
//        var op = customer.getCart().getCartFoodItems().stream().filter(e -> foodItemID.equals(e.getFoodItem().getId())).findFirst();
//        op.ifPresent(c -> {
//            c.setCart(null);
//            cartFoodItemRepository.deleteById(c.getId());
//        });
//    }
//
//    @Override
//    @Transactional
//    public void checkout(CustomerDTO customerDTO) {
//        // Creat an order
//        OrderMain order = new OrderMain(user);
//        orderRepository.save(order);
//
//        // clear cart's foreign key & set order's foreign key& decrease stock
//        user.getCart().getProducts().forEach(productInOrder -> {
//            productInOrder.setCart(null);
//            productInOrder.setOrderMain(order);
//            productService.decreaseStock(productInOrder.getProductId(), productInOrder.getCount());
//            productInOrderRepository.save(productInOrder);
//        });
//        Customer customer = MapperUtil.toCustomer(customerDTO);
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setCustomerId(customer.getId());
//        orderDTO.setRestaurantId();
//    }
//}
