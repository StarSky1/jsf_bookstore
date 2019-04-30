package com.yj.bookstore.view;

import com.yj.bookstore.dao.BookDao;
import com.yj.bookstore.model.domain.Book;
import lombok.Data;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

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
    private Book selectedBook;
    @Inject
    private BookDao bookDao;
    @Inject
    private LazyDataModel<Book> lazyModel;

    public BookBean(){
        book=new Book();
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("编辑成功", ((Book) event.getObject()).getBookName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("编辑取消", ((Book) event.getObject()).getBookName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onAddNew() {
        // Add one new car to the table:
        //bookDao.insertRecord(book);
        FacesMessage msg = new FacesMessage("图书已添加", book.getBookName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        //清空book的属性值
        book = new Book();
    }

    public void onDeleteBook(ActionEvent event){
        FacesMessage msg = new FacesMessage("图书已删除", selectedBook.getBookName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onDeleteBookCancel(ActionEvent event){
        FacesMessage msg = new FacesMessage("删除已取消", selectedBook.getBookName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void select(Book book) {
        selectedBook=book;
    }



}
