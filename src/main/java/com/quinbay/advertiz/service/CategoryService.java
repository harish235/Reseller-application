package com.quinbay.advertiz.service;

import com.quinbay.advertiz.Repositories.CategoryRepository;
import com.quinbay.advertiz.Repositories.SubcategoryRepository;
import com.quinbay.advertiz.functions.CategoryInterface;
import com.quinbay.advertiz.model.Category;
import com.quinbay.advertiz.model.CategoryResponse;
import com.quinbay.advertiz.model.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryService implements CategoryInterface {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubcategoryRepository subcategoryRepository;


    @Override
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }


    @Override
    public List<CategoryResponse> getCategories(){
        CategoryResponse cr = new CategoryResponse();
        List<CategoryResponse> cnew = new ArrayList<CategoryResponse>();
        List<Category> categoryList = categoryRepository.findAll();
        for(Category c: categoryList){
            cr.setId(c.getId());
            cr.setCategoryname(c.getName());
            List<Subcategory> subList = subcategoryRepository.findByCategoryid(c.getId());
            List<String> subcategories = new ArrayList<String>();
            for(Subcategory sub: subList){
                subcategories.add(sub.getName());
            }
            cr.setSubcategories(subcategories);
            cnew.add(cr);
        }
        return cnew;
    }
}
