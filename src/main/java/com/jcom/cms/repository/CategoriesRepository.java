package com.jcom.cms.repository;


import com.jcom.cms.entity.Categories;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
     Categories findFirstByCpuContaining(String cpu);
     List<Categories> findByTypeContaining(String type, Pageable pageable);
     Categories getOne(Integer id);
     long count();
}