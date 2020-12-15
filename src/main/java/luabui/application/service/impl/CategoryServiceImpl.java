package luabui.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import luabui.application.dto.CategoryDTO;
import luabui.application.exception.NotFoundException;
import luabui.application.exception.RestaurantNotFoundException;
import luabui.application.model.Category;
import luabui.application.model.Restaurant;
import luabui.application.model.User;
import luabui.application.repository.CategoryRepository;
import luabui.application.service.CategoryService;
import luabui.application.utility.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(MapperUtil :: toCategoryDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(Long aLong) {
        Category category = getCategory(aLong);
        return MapperUtil.toCategoryDTO(category);
    }

    @Override
    public CategoryDTO save(CategoryDTO newObject) {
        Category category = categoryRepository.save(MapperUtil.toCategory(newObject));
        return MapperUtil.toCategoryDTO(category);
    }

    @Override
    public void deleteById(Long aLong) {
        categoryRepository.deleteById(aLong);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, Long categoryId) {
        Category category = getCategory(categoryId);
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return MapperUtil.toCategoryDTO(category);
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(categoryId + " not found"));
    }

}
