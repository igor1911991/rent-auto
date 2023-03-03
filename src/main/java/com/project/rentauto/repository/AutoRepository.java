package com.project.rentauto.repository;

import com.project.rentauto.model.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {

    List<Auto> findAllByOrderById();

    @Query("FROM Auto WHERE available = true ORDER BY id")
    List<Auto> getAvailableAuto();

    @Query("SELECT available FROM Auto WHERE id = :id")
    Boolean available(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Auto SET available = false WHERE id = :id")
    void blockAuto(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Auto SET available = true WHERE id = :id")
    void unlockAuto(@Param("id") Long id);

    @Query("SELECT orderNum FROM Auto WHERE id = :autoID")
    Long getAutoOrderID(@Param("autoID") Long autoID);

    @Modifying
    @Query("UPDATE Auto SET order_num = :orderID WHERE id = :autoID")
    void setOrderID(@Param("orderID") Long orderID, @Param("autoID") Long autoID);

    @Query("FROM Auto WHERE model = :model")
    Auto modelIsHave(@Param("model") String model);
}
