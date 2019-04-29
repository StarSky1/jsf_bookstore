package com.yj.bookstore.dao;

import com.yj.bookstore.model.domain.Book;
import com.yj.bookstore.model.dto.PageObject;
import lombok.Data;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 76355
 * @Date 2019/4/29 7:58
 * @Description
 */
@Named
@Data
public class BookDao implements BaseDao<Book,Integer>{
    @Inject
    private EntityDao entityDao;

    @Override
    public Book findById(Integer id) {
        entityDao.init();
        EntityManager entityManager=entityDao.getEntityManager();
        Book book=entityManager.find(Book.class,id);
        entityDao.after();
        return book;
    }

    @Override
    public int updateById(Book book) {
        entityDao.init();
        EntityManager entityManager=entityDao.getEntityManager();
        Book book1=entityManager.merge(book);
        entityDao.after();
        return book1!=null?1:0;
    }

    @Override
    public Book insertRecord(Book book) {
        entityDao.init();
        EntityManager entityManager=entityDao.getEntityManager();
        entityManager.persist(book);
        entityDao.after();
        return book;
    }

    @Override
    public void deleteById(Integer id) {
        entityDao.init();
        EntityManager entityManager=entityDao.getEntityManager();
        Book book=entityManager.find(Book.class,id);
        entityManager.remove(book);
        entityDao.after();
    }

    //分页查询
    public PageObject<Book> findList(int first, int pageSize){
        entityDao.init();
        EntityManager entityManager=entityDao.getEntityManager();
        //分页查询
        String sql="select * from book b where 1=1 limit ?,?";
        Query query=entityManager.createNativeQuery(sql);
        first=first<1?1:first;
        int start=(first-1)*pageSize;
        int offset=pageSize;
        query.setParameter(1,start);
        query.setParameter(2,offset);
        List<Object> objectList=query.getResultList();
        List<Book> list=objListToBookList(objectList);
        //查询 总记录数
        sql="select count(id) from book b";
        query=entityManager.createNativeQuery(sql);
        Integer count=((BigInteger)query.getSingleResult()).intValue();
        PageObject<Book> pageObject=new PageObject<>(list,count);
        //关闭资源
        entityDao.after();
        return pageObject;
    }

    private List<Book> objListToBookList(List<Object> objList){
        List<Book> list=new ArrayList<>();
        objList.stream().forEach(item -> {
            Object[] array=(Object[])item;
            Book book=new Book();
            book.setId((Integer) array[0]);
            book.setAuthor((String)array[1]);
            book.setBookName((String)array[2]);
            book.setBrief((String)array[3]);
            book.setCategory((String)array[4]);
            book.setPrice((Double)array[5]);
            book.setPublishDate((Date)array[6]);
            book.setPublishHouse((String)array[7]);
            list.add(book);
        });
        return list;
    }

}
