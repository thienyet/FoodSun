package luabui.application.service;

import luabui.application.dto.CategoryDTO;
import luabui.application.dto.RestaurantDTO;

import java.util.List;

public interface CategoryService extends CrudService<CategoryDTO, Long> {

    CategoryDTO update(CategoryDTO categoryDTO, Long categoryId);

    List<CategoryDTO> findByName(String name);

    CategoryDTO updateDelete(Long categoryId);

    List<CategoryDTO> getAllExisted();
}
