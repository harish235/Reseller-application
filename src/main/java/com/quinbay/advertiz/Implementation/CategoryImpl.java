package com.quinbay.advertiz.Implementation;

import com.quinbay.advertiz.Repositories.CategoryRepository;
import com.quinbay.advertiz.Repositories.SubcategoryRepository;
import com.quinbay.advertiz.Service.CategoryService;
import com.quinbay.advertiz.model.Category;
import com.quinbay.advertiz.model.CategoryResponse;
import com.quinbay.advertiz.model.Subcategory;
import com.quinbay.advertiz.pojo.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubcategoryRepository subcategoryRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    @Override
    public Category loadCategories(CategoryRequest c){
        return categoryRepository.save(c.getCategory());
    }

    @Override
    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }

    @Override
    @Cacheable(value = "listOfCategories", key = "#root.targetClass")
    public List<CategoryResponse> getCategories(){
        List<CategoryResponse> categoryresponselist = new ArrayList();

        List<Category> allCategories = categoryRepository.findAll();
        for (Category c: allCategories){
            CategoryResponse cr = new CategoryResponse();
            cr.setId(c.getId());
            cr.setCategoryname(c.getName());
            List<String> subcategories = new ArrayList();
            List<Subcategory> subcategoriesOfCategory = subcategoryRepository.findByCategoryid(c.getId());
            for(Subcategory s: subcategoriesOfCategory){
                subcategories.add(s.getName());
            }
            cr.setSubcategories(subcategories);
            categoryresponselist.add(cr);
        }
        return categoryresponselist;
    }
}
