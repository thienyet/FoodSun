package luabui.application.repository;

import luabui.application.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select cate from Category cate where cate.name like %:name%")
    Category findByName(@Param("name")String name);
}
