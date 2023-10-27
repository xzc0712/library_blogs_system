package com.xzc.service;

import com.xzc.dao.BookDao;
import com.xzc.pojo.Book;
import com.xzc.pojo.Category;
import lombok.Lombok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookDao bookDao;
    @Autowired
    CategoryService categoryService;

    //实现按照关键字查询
    public List<Book>Search(String keywords){
        return bookDao.findAllByTitleLikeAndAuthorLike('%'+keywords+'%','%'+keywords+'%');
    }


    public List<Book> list(){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        return bookDao.findAll(sort);
    }

    /**
     * 当主键存在时更新数据，主键不存在时插入数据
     * @param book
     */
    public void addOrUpdate(Book book){
        bookDao.save(book);
    }

    public void deleteById(int id){
        bookDao.deleteById(id);
    }

    public List<Book> listByCategory(int cid){
        Category category = categoryService.get(cid);
        return bookDao.findAllByCategory(category);
    }
}
