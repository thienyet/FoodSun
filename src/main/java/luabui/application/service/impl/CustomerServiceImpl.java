package luabui.application.service.impl;


import lombok.extern.slf4j.Slf4j;
import luabui.application.constants.OrderStatus;
import luabui.application.dto.CustomerDTO;
import luabui.application.dto.OrderDTO;
import luabui.application.dto.OrderModificationDTO;
import luabui.application.exception.CustomerNotFoundException;
import luabui.application.exception.OrderNotFoundException;
import luabui.application.exception.OrderStatusException;
import luabui.application.model.Customer;
import luabui.application.model.Order;
import luabui.application.model.User;
import luabui.application.repository.CustomerRepository;
import luabui.application.repository.OrderRepository;
import luabui.application.repository.RoleRepository;
import luabui.application.repository.UserRepository;
import luabui.application.service.CustomerService;
import luabui.application.utility.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, OrderRepository orderRepository, UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<CustomerDTO> findAll() {
        log.debug("Returning Customer(s) from Service");
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(MapperUtil:: toCustomerDTO).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findById(Long customerId) {
        log.debug("Returning Customer(s) by id from Service");
        Customer customer = getCustomer(customerId);
        return MapperUtil.toCustomerDTO(customer);
    }

    @Override
    public CustomerDTO save(CustomerDTO newCustomer) {
        log.debug("Saving Customer(s) from Service");
        Customer customer = customerRepository.save(MapperUtil.toCustomer(newCustomer));
        createUser(customer);
        return MapperUtil.toCustomerDTO(customer);
    }

    @Override
    public void deleteById(Long customerId) {
        log.debug("Deleting Customer by Id from Service.");
        customerRepository.deleteById(customerId);
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO, Long customerId) {
        log.debug("Updating Customer Details.");
        Customer customer = getCustomer(customerId);
        User user = userRepository.findByEmail(customer.getEmail());

        user.setEmail(customerDTO.getEmail());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNo(customerDTO.getPhoneNo());
        customer.setPassword(bCryptPasswordEncoder.encode(customerDTO.getPassword()));

        userRepository.save(user);
        customerRepository.save(customer);
        return MapperUtil.toCustomerDTO(customer);
    }

    @Override
    public List<OrderDTO> getCustomerOrders(Long customerId) {
        log.debug("Get all Customer Orders.");
        Customer customer = getCustomer(customerId);
        return customer.getOrders().stream().map(order -> MapperUtil.toOrderDTO(order)).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getCustomerOrderById(Long customerId, Long orderId) {
        log.debug("Getting Customer order by id.");
        Customer customer = getCustomer(customerId);
        Order order = getOrder(orderId);
        if (!order.getCustomer().equals(customer)) {
            throw new OrderNotFoundException(orderId);
        }
        return MapperUtil.toOrderDTO(order);
    }

    @Override
    public OrderDTO modifyOrder(Long customerId, Long orderId, OrderModificationDTO modification) {
        log.debug("Modifying Order.");
        Customer customer = getCustomer(customerId);
        Order order = getOrder(orderId);
        if (!order.getCustomer().equals(customer)) {
            throw new OrderNotFoundException(orderId);
        }
//        PaymentMode paymentMode = PaymentMode.fromDescription(modification.getPaymentMode());
        OrderStatus orderStatus = OrderStatus.fromDescription(modification.getOrderStatus());

//        if (paymentMode != null) {
//            throw new PaymentModeException("Payment mode cannot be changed now.");
//        }

        if (!order.getOrderStatus().getDescription().equals("delivered") && orderStatus.getDescription().equals("cancelled")) {
            log.debug("Successfully changed order status.");
            order.setOrderStatus(OrderStatus.CANCELLED_BY_USER);
        } else {
            throw new OrderStatusException("The order you are trying to cancel is already: " + order.getOrderStatus());
        }

        return MapperUtil.toOrderDTO(orderRepository.save(order));
    }

    private Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private void createUser(Customer customer) {
        User user = new User();
        user.setEmail(customer.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        user.setRole(roleRepository.findByRole("CUSTOMER"));
        userRepository.save(user);
    }
}
