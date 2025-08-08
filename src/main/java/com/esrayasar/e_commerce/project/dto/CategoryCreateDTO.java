package com.esrayasar.e_commerce.project.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDTO {


    @NotBlank(message = "Name cannot be blank")
    @Size(min=2 ,max=255,message="Name must be between 2 and 255 characters")
    @Column(name="name")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(name="description")
    private String description;
}
