package com.ucc.crudservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "el codigo sku no puede estar vacio")
    private String sku;

    @NotBlank(message = "el name es obligatorio")
    private String name;

    @NotBlank(message = "la descripcion es obligatoria")
    private String description;

    @NotNull(message = "el precio es obligatorio")
    @DecimalMin(value = "0.00", message = "el precio debe ser mayor o igual a 0")
    private Double price;

    private Boolean status;


}
