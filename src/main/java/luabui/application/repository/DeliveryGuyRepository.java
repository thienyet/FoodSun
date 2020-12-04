package luabui.application.repository;

import luabui.application.model.DeliveryGuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface DeliveryGuyRepository extends JpaRepository<DeliveryGuy, Long> {
    @Query("select delivery from DeliveryGuy delivery where delivery.createDate = :createDate")
    List<DeliveryGuy> findDeliveryGuyByDate(@Param("createDate") Date createDate);
}
