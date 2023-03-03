package com.project.rentauto.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auto")
    private Auto auto;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "status_order")
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    public Order(User user, Auto auto, LocalDateTime orderDate, StatusOrder statusOrder) {
        this.user = user;
        this.auto = auto;
        this.orderDate = orderDate;
        this.statusOrder = statusOrder;
    }
}
