package luabui.application.service;

import luabui.application.dto.CategoryDTO;
import luabui.application.dto.RestaurantDTO;

public interface CategoryService extends CrudService<CategoryDTO, Long> {

    CategoryDTO update(CategoryDTO categoryDTO, Long categoryId);

    CategoryDTO findByName(String name);

    CategoryDTO updateDelete(Long categoryId);
}
