package com.antisocial.service;

import com.antisocial.dao.CategoryDAO;
import com.antisocial.model.Article;
import com.antisocial.model.Category;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private ServletContext servletContext;


    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        return categoryDAO.getCategories();
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public Category getCategoryById(Long id) {
        return categoryDAO.getCategoryById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public Category getCategoryByName(String categoryName) {
        return categoryDAO.getCategoryByName(categoryName);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public boolean isCategoryValid(String categoryName) {
        return categoryDAO.getCategoryByName(categoryName) != null;
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public void addArticle(Article article, Long ID){
        Category category = categoryDAO.getCategoryById(ID);
        List<Article> articleList = category.getArticleList();
        if(articleList == null){
            articleList = new ArrayList<>();
        }
        articleList.add(article);
        article.setCategory(category);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void uploadCategoryStarterFiles(String categoryName) throws IOException {
        String CAT_DOMAIN_LOCATION = servletContext.getRealPath("/resources/") + "pic/cat/def.jpg";
        String CAT_UPLOAD_LOCATION = servletContext.getRealPath("/resources/") + "pic/cat/" + categoryName + ".jpg";
        File fileToCopy = new File(CAT_DOMAIN_LOCATION);
        File newFile = new File(CAT_UPLOAD_LOCATION);
        FileUtils.copyFile(fileToCopy, newFile);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void saveCategory(Category category) {
        categoryDAO.saveCategory(category);
    }

}
