package com.gabriel.prodmsapp.service;

import com.gabriel.prodmsapp.model.Category;

public interface CategoryService {
    Category[] getCategories() throws Exception;

    Category getUom(Integer id) throws Exception;

    Category create(Category category) throws Exception;

    Category update(Category category) throws Exception;

    void delete(Integer id) throws Exception;
}
