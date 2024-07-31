package com.gabriel.prodmsapp.serviceimpl;

import com.gabriel.prodmsapp.entity.CategoryData;
import com.gabriel.prodmsapp.model.Category;
import com.gabriel.prodmsapp.repository.CategoryDataRepository;
import com.gabriel.prodmsapp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryDataRepository categoryDataRepository;

    @Override
    public Category[] getCategories() {
        List<CategoryData> categoriesData = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        categoryDataRepository.findAll().forEach(categoriesData::add);
        Iterator<CategoryData> it = categoriesData.iterator();

        while(it.hasNext()) {
            Category category = new Category();
            CategoryData categoryData = it.next();
            category.setId(categoryData.getId());
            category.setName(categoryData.getName());
            categories.add(category);
        }

        Category[] array = new Category[categories.size()];
        for  (int i = 0; i< categories.size(); i++){
            array[i] = categories.get(i);
        }
        return array;
    }

    @Override
    public Category create(Category category) {
        logger.info("add: Input"+ category.toString());
        CategoryData categoryData = new CategoryData();
        categoryData.setName(category.getName());

        categoryData = categoryDataRepository.save(categoryData);
        logger.info("add: Input"+ categoryData.toString());

        Category newCategory = new Category();
        newCategory.setId(categoryData.getId());
        newCategory.setName(categoryData.getName());
        return newCategory;
    }

    @Override
    public Category update(Category category) {
        CategoryData categoryData = new CategoryData();
        categoryData.setId(category.getId());
        categoryData.setName(category.getName());

        categoryData = categoryDataRepository.save(categoryData);

        Category newCategory = new Category();
        newCategory.setId(categoryData.getId());
        newCategory.setName(categoryData.getName());
        return newCategory;
    }

    @Override
    public Category getUom(Integer id) {
        logger.info("Input id >> "+  Integer.toString(id) );
        Optional<CategoryData> optional = categoryDataRepository.findById(id);
        if(optional.isPresent()) {
            logger.info("Is present >> ");
            Category category = new Category();
            CategoryData uomDatum = optional.get();
            category.setId(uomDatum.getId());
            category.setName(uomDatum.getName());
             return category;
        }
        logger.info("Failed  >> unable to locate uom" );
        return null;
    }

    @Override
    public void delete(Integer id) {
        Category category = null;
        logger.info("Input >> " + Integer.toString(id));
         Optional<CategoryData> optional = categoryDataRepository.findById(id);
         if( optional.isPresent()) {
             CategoryData uomDatum = optional.get();
             categoryDataRepository.delete(optional.get());
             logger.info("Successfully deleted >> " + uomDatum.toString());
             category = new Category();
             category.setId(optional.get().getId());
             category.setName(optional.get().getName());
         }
         else {
             logger.info("Failed  >> unable to locate category id: " +  Integer.toString(id));
         }
    }
}
