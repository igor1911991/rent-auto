package com.project.rentauto.repository;

import com.project.rentauto.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT id FROM Order WHERE users = :userID AND status_order = 'CREATED' OR status_order ='IN_PROGRESS'")
    Long currentOrder(@Param("userID") Long userID);

    @Query("FROM Order WHERE status_order = 'CREATED'")
    List<Order> newOrders();

    @Query("FROM Order WHERE status_order = 'IN_PROGRESS'")
    List<Order> isActive();

    @Modifying
    @Query("UPDATE Order SET status_order = :IN_PROGRESS WHERE id = :id")
    void approve(@Param("id") Long id, @Param("IN_PROGRESS") String status);

    @Modifying
    @Query("UPDATE Order SET status_order = :CANCELLED WHERE id = :id")
    void cancel(@Param("id") Long id, @Param("CANCELLED") String status);

    @Modifying
    @Query("UPDATE Order SET status_order = :COMPLETED WHERE id = :id")
    void complete(@Param("id") Long id, @Param("COMPLETED") String status);
}
