package com.yj.bookstore.dao;

import com.yj.bookstore.model.domain.Book;
import com.yj.bookstore.model.dto.PageObject;
import com.yj.bookstore.model.dto.SelectItem;
import lombok.Data;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
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
public class BookDao implements BaseDao<Book,Integer> {
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

    public Book findByBookNameAndAuthor(String bookName,String author){
        entityDao.init();
        EntityManager entityManager=entityDao.getEntityManager();
        String jpql="select b from Book b where b.bookName =?1 and b.author = ?2";
        Query query=entityManager.createQuery(jpql);
        query.setParameter(1,bookName);
        query.setParameter(2,author);
        List<Book> books=query.getResultList();
        entityDao.after();
        if(books.size()>0){
            return books.get(1);
        }else{
            return null;
        }
    }

    //分页查询
    public PageObject<Book> findList(int first, int pageSize, List<SelectItem> selectItems){
        entityDao.init();
        EntityManager entityManager=entityDao.getEntityManager();
        //分页查询
        StringBuilder sb=new StringBuilder();
        sb.append("select * from book b where 1=1 ");
        for (SelectItem item : selectItems) {
            sb.append("and ");
            sb.append(item.getColumn()+" "+item.getExpression()+" ");
            sb.append("? ");
        }
        String countSql="select count(id) "+ sb.substring(sb.indexOf("*")+1);
        sb.append("limit ?,?");
        String sql=sb.toString();
        Query query=entityManager.createNativeQuery(sql);
        Query countQuery=entityManager.createNativeQuery(countSql);
        for (int i = 0; i < selectItems.size(); i++) {
            SelectItem item=selectItems.get(i);
            String column= item.getColumn();
            if(column.equals("book_name") || column.equals("category") || column.equals("brief")){
                query.setParameter(i+1,"%"+(String)item.getValue()+"%");
                countQuery.setParameter(i+1,"%"+(String)item.getValue()+"%");
            }
            if(column.equals("publish_date")){
                query.setParameter(i+1,(Date)item.getValue());
                countQuery.setParameter(i+1,(Date)item.getValue());
            }
            if(column.equals("price")){
                query.setParameter(i+1,(Double)item.getValue());
                countQuery.setParameter(i+1,(Double)item.getValue());
            }
        }
        int start=first;
        int offset=pageSize;
        query.setParameter(selectItems.size()+1,start);
        query.setParameter(selectItems.size()+2,offset);
        List<Object> objectList=query.getResultList();
        List<Book> list=objListToBookList(objectList);
        //查询 总记录数
        Integer count=((BigInteger)countQuery.getSingleResult()).intValue();
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
