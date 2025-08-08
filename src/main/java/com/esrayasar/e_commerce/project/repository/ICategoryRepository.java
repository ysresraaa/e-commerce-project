package com.esrayasar.e_commerce.project.repository;

import com.esrayasar.e_commerce.project.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository  extends JpaRepository<Category,Long> {



}
