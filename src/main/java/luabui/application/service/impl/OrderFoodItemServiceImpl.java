package luabui.application.service.impl;

import luabui.application.dto.OrderFoodItemDTO;
import luabui.application.exception.OrderFoodItemNotFoundException;
import luabui.application.model.OrderFoodItem;
import luabui.application.repository.OrderFoodItemRepository;
import luabui.application.service.OrderFoodItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderFoodItemServiceImpl implements OrderFoodItemService {
    private OrderFoodItemRepository orderFoodItemRepository;

    @Autowired
    public OrderFoodItemServiceImpl(OrderFoodItemRepository orderFoodItemRepository) {
        this.orderFoodItemRepository = orderFoodItemRepository;
    }

    @Override
    public List<OrderFoodItemDTO> findAll() {
        log.debug("Getting Order FoodItems from Service");
//        return orderFoodItemRepository.findAll();
        return null;
    }

    @Override
    public OrderFoodItemDTO findById(Long orderFoodItemId) {
        log.debug("Getting Order FoodItem By Id from Service");
//        return orderFoodItemRepository.findById(orderFoodItemId).orElseThrow(() -> new OrderFoodItemNotFoundException(orderFoodItemId));
        return null;
    }

    @Override
    public OrderFoodItemDTO save(OrderFoodItemDTO newObject) {
        return null;
    }

//    @Override
//    public OrderFoodItem save(OrderFoodItem newOrderFoodItem) {
//        log.debug("Saving Order FoodItems from Service");
//        return orderFoodItemRepository.save(newOrderFoodItem);
//    }

    public OrderFoodItem update(OrderFoodItem orderFoodItem, Long orderFoodItemId) {
        log.debug("Updating Order FoodItem from Service");
        return null;
    }

    @Override
    public void deleteById(Long orderFoodItemId) {
        log.debug("Deleting Order FoodItem By Id from Service");
        orderFoodItemRepository.deleteById(orderFoodItemId);
    }
}