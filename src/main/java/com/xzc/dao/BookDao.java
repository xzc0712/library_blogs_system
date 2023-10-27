package com.xzc.dao;

import com.xzc.pojo.Book;
import com.xzc.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDao extends JpaRepository<Book,Integer> {
    List<Book>findAllByCategory(Category category);
    List<Book>findAllByTitleLikeAndAuthorLike(String keyword1,String keyword2);

}
