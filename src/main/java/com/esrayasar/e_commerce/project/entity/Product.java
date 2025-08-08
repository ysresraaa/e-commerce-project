package com.esrayasar.e_commerce.project.entity;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="name cannot be blank")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    @Column(name="name")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(name="description")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(name="price")
    private BigDecimal price;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Column(name="stock_quantity")
    private int stockQuantity;

    @ManyToOne
    @JoinColumn(name="category_id",nullable=false)
    private Category category;



}
