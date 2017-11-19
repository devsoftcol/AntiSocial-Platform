package com.antisocial.service;

import com.antisocial.model.Article;
import com.antisocial.model.Category;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    public List<Category> getCategories();

    public Category getCategoryById(Long id);

    public Category getCategoryByName(String categoryName);

    public boolean isCategoryValid(String categoryName);

    public void addArticle(Article article, Long ID);

    public void uploadCategoryStarterFiles(String categoryName) throws IOException;

    public void saveCategory(Category category);

}
