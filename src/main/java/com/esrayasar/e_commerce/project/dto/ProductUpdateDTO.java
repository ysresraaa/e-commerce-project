package com.esrayasar.e_commerce.project.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.boot.convert.DataSizeUnit;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {

    private Long categoryId;

    @NotBlank(message="product name connot be blank")
    @Size(min=2,max=255,message="Product  name must be between 2 and 255 characters")
    private String name;

    @Size(max=1000, message="Product description cannot exceed 1000 characterrs")
    private String description;

    @NotNull(message="Product price cannot be null")
    @DecimalMin(value="0.01",message="Product price must be greater than 0.01")
    private BigDecimal price;

    @NotNull(message="Stock quantity cannot be null")
    @Min(value=0, message="Stock quantity cannot be less than 0")
    public Integer stockQuantity;



}
