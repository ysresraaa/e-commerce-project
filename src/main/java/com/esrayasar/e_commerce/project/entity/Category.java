package com.esrayasar.e_commerce.project.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name="categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank(message = "Name cannot be blank")
    @Size(min=2 ,max=255,message="Name must be between 2 and 255 characters")
    @Column(name="name")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(name="description")
    private String description;


    @OneToMany(mappedBy = "category", cascade=CascadeType.REMOVE,orphanRemoval=true)
    private List<Product>products;

    public void setId(Long id) {
    }
}
