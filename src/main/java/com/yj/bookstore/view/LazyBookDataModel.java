package com.yj.bookstore.view;

import com.yj.bookstore.dao.BookDao;
import com.yj.bookstore.model.domain.Book;
import com.yj.bookstore.model.dto.PageObject;
import lombok.Data;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * @Author 76355
 * @Date 2019/4/29 8:27
 * @Description
 */
@Named
@Data
public class LazyBookDataModel extends LazyDataModel<Book> {

    private List<Book> list;
    @Inject
    private BookDao bookDao;

    public LazyBookDataModel(){}

    @Override
    public List<Book> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)
    {
        //这个方法不光可以实现分页，也可以实现过滤与排序
        /*我们暂时只实现简单的分页，
           first代表起始位置，pageSize代表查询数量，其他参数可以暂时不管，这两个参数就可以实现分页功能
           要查询出数据与数量
        */
        PageObject<Book> pageObject=bookDao.findList(first,pageSize);
        //setRowCount(数量); //这个方法一定要执行!
        setRowCount(pageObject.getTotal());
        //list = 查询结果
        list=pageObject.getList();
        return list;
    }

    @Override
    public Book getRowData(String rowKey)
    {
        for(Book book : list)
        {
            if(book.getId().toString().equals(rowKey))
            {
                return book;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Book book)
    {
        return book.getId();
    }

}
