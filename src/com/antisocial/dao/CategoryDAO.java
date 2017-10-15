package com.antisocial.dao;

import com.antisocial.model.Article;
import com.antisocial.model.Category;

import java.util.List;

public interface CategoryDAO {

    public List<Category> getCategories();

    public Category getCategoryById(Long id);

    public Category getCategoryByName(String categoryName);

    public void saveCategory(Category category);
}
