package com.antisocial.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CategoryDTO {

    private String categoryName;

    @NotBlank(message = "Category cannot be empty.")
    @Size(min = 3, max = 10 , message = "Category min 3 and max 10 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only characters allowed in the category name.")
    private String categoryNew;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryNew() {
        return categoryNew;
    }

    public void setCategoryNew(String categoryNew) {
        this.categoryNew = categoryNew;
    }
}
