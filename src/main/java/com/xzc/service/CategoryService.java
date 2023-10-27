package com.xzc.service;

import com.xzc.dao.CategoryDao;
import com.xzc.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    //对查询结果进行排序
    public List<Category> list(){
        //Sort sort = new Sort(Sort.Direction.DESC,"id");
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        return categoryDao.findAll(sort);
    }

    public Category get(int id){
        //.orElse(null)将Optional对象转换为Category对象，如果查询结果为空，则返回null
        //为了避免空指针异常，常需要使用.orElse(null)或其他类似方法来处理Optional对象。
        Category category = categoryDao.findById(id).orElse(null);
        return category;
    }
}
