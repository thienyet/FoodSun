package luabui.application.repository;

import luabui.application.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select cate from Category cate where cate.name like %:name% and cate.isDeleted = false ")
    List<Category> findByName(@Param("name")String name);

    @Query("select cate from Category cate where cate.isDeleted = false ")
    List<Category> findAllExisted();
}
