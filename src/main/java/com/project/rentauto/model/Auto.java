package com.project.rentauto.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "autos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "model")
    @NotBlank(message = "field must not be empty")
    private String model;

    @Column(name = "engine_power")
    @Min(value = 50, message = "value range from 50 to 1200")
    @Max(value = 1200, message = "value range from 50 to 1200")
    private Integer power;

    @Column(name = "speed")
    @Min(value = 20, message = "value range from 20 to 420")
    @Max(value = 420, message = "value range from 20 to 420")
    private Integer speed;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "order_num")
    private Long orderNum;

}
