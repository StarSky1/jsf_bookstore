package com.yj.bookstore.view;

import com.yj.bookstore.dao.BookDao;
import com.yj.bookstore.model.domain.Book;
import lombok.Data;
import org.primefaces.model.LazyDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * @Author 76355
 * @Date 2019/4/29 7:54
 * @Description
 */
@ManagedBean(name = "bookBean")
@SessionScoped
@Data
public class BookBean implements Serializable{

    private Book book;
    @Inject
    private BookDao bookDao;
    @Inject
    private LazyDataModel<Book> lazyModel;

    public BookBean(){
        book=new Book();
        //lazyModel = new LazyBookDataModel();
    }



}
