package com.quinbay.advertiz.Repositories;


import com.quinbay.advertiz.model.Advertisement;
import com.quinbay.advertiz.model.Category;
import com.quinbay.advertiz.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    List<Subcategory> findByCategoryid(int  id);

    Optional<Subcategory> findByName(String name);
}
