package com.xzc.cotroller;

import com.xzc.pojo.Book;
import com.xzc.service.BookService;
import com.xzc.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class LibraryController {

    @Autowired
    BookService bookService;

    /**
     * 添加图片上传
     */
    @PostMapping("api/cover")
    public String coverUpload(MultipartFile file) throws IOException {
        String folder = "D:/workspace/img";
        File imgFile = new File(folder);
        File f = new File(imgFile, StringUtils.getRandomString(6) + file.getOriginalFilename().
                substring(file.getOriginalFilename().length() - 4));
        if(!f.getParentFile().exists()){
            f.getParentFile().mkdirs();
        }
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8443/api/file/"+f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 根据关键字模糊查找
     * @param keywords
     * @return
     */
    @CrossOrigin
    @GetMapping("api/search")
    public  List<Book> searchResult(@RequestParam("keywords") String keywords){
        if ("".equals(keywords)){
            return bookService.list();
        }else {
            return bookService.Search(keywords);
        }
    }
    
    @GetMapping("api/books")
    public List<Book>list(){
        return bookService.list();
    }

    @PostMapping("api/books")
    public Book addOrUpdate(@RequestBody Book book){
        bookService.addOrUpdate(book);
        return book;
    }

    @PostMapping("api/delete")
    public void delete(@RequestBody Book book){
        bookService.deleteById(book.getId());
    }

    @GetMapping("api/categories/{cid}/books")
    public List<Book> listByCategory(@PathVariable("cid") int cid){
        if(0 != cid){
            return bookService.listByCategory(cid);
        }else {
            return list();
        }
    }


}
