package luabui.application.repository;

import luabui.application.model.DeliveryGuy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface DeliveryGuyRepository extends JpaRepository<DeliveryGuy, Long> {
    @Query("select delivery from DeliveryGuy delivery where delivery.createDate = :createDate")
    Page<DeliveryGuy> findDeliveryGuyByDate(@Param("createDate") Date createDate, Pageable pageable);

    @Query("select delivery from DeliveryGuy delivery where delivery.address like %:address%")
    Page<DeliveryGuy> findDeliveryGuyByAddress(@Param("address") String address, Pageable pageable);

    Page<DeliveryGuy> findAll(Pageable pageable);
}
