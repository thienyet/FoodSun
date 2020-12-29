package luabui.application.service.impl;

import luabui.application.exception.FoodItemNotFoundException;
import luabui.application.model.FoodItem;
import luabui.application.repository.FoodItemRepository;
import luabui.application.service.FoodItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FoodItemServiceImpl implements FoodItemService {
    private FoodItemRepository foodItemRepository;

    @Autowired
    public FoodItemServiceImpl(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public List<FoodItem> findAll() {
        log.debug("Getting Food Items from Service.");
        return foodItemRepository.findAll();
    }

    @Override
    public FoodItem findById(Long foodItemId) {
        log.debug("Getting Food Item By Id from Service.");
        return foodItemRepository.findById(foodItemId).orElseThrow(() -> new FoodItemNotFoundException(foodItemId));
    }

    @Override
    public FoodItem save(FoodItem newFoodItem) {
        log.debug("Saving Food Item from Service.");
        return foodItemRepository.save(newFoodItem);
    }

    public FoodItem update(FoodItem foodItem, Long foodItemId) {
        log.debug("Updating Food Item from Service.");
        return null;
    }

    @Override
    public void deleteById(Long foodItemId) {
        log.debug("Deleting Food Item By Id from Service.");
        foodItemRepository.deleteById(foodItemId);
    }

    @Override
    public Page<FoodItem> getRestaurantFoodItems(Long restaurantId, Pageable pageable) {
        Page<FoodItem> foodItemPage = foodItemRepository.findAllInPage(restaurantId, pageable);
        return foodItemPage;
    }

}