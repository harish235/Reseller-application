package com.quinbay.advertiz.functions;

import com.quinbay.advertiz.model.Category;
import com.quinbay.advertiz.model.CategoryResponse;

import java.util.List;

public interface CategoryInterface {

    List<Category> getAllCategory();
    List<CategoryResponse> getCategories();

}
