package com.yj.bookstore.model;

import com.alibaba.fastjson.JSON;
import com.yj.bookstore.dao.EntityDao;
import com.yj.bookstore.model.domain.Book;
import org.hibernate.jpa.QueryHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 76355
 * @Date 2019/4/27 18:02
 * @Description
 */

public class BookTest {

    private EntityDao entityDao=new EntityDao();
    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        entityDao.init();
        entityManager=entityDao.getEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        entityDao.after();
    }

    //使用 hibernate的查询缓存
    @Test
    public void testQueryCache(){
        String jpql="from Book b where b.price > ?1";
        Query query=entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE,true);
        //占位符的索引从1开始
        query.setParameter(1,20.0);
        List<Book> books=query.getResultList();
        System.out.println(books.size());

        //占位符的索引从1开始
        query.setParameter(1,20.0);
        books=query.getResultList();
        System.out.println(books.size());
    }

    //createNativeQuery适用于 本地sql
    @Test
    public void testNativeQuery(){
        String sql="select * from book where id = ?";
        Query query=entityManager.createNativeQuery(sql).setParameter(1,1);

        Object o=  query.getSingleResult();
        System.out.println(o);
    }

    //默认情况下，若只查询部分属性，则将返回 Object[] 类型的结果，或者Object[]类型的List
    //也可以在实体类中创建对应的构造器，然后在JPQL语句中利用对应的构造器返回实体类的对象
    @Test
    public void testPartlyProperties(){
        String jpql="select new Book(b.bookName, b.price) from Book b where b.price > ?1";
        List<Book> books=entityManager.createQuery(jpql).setParameter(1,20.0).getResultList();
        System.out.println(books);
    }

    @Test
    public void testPage(){
        String sql="select * from book b where 1=1 limit ?,?";
        Query query=entityManager.createNativeQuery(sql);
        query.setParameter(1,0);
        query.setParameter(2,10);
        List<Book> list=new ArrayList<>();
        query.getResultList().stream().forEach(item -> {
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
        for (Book o : list) {
            System.out.println(JSON.toJSONString(o));
        }
    }

    @Test
    public void testHelloJPQL(){
        String jpql="from Book b where b.price > ?1";
        Query query=entityManager.createQuery(jpql);
        //占位符的索引从1开始
        query.setParameter(1,20.0);
        List<Book> books=query.getResultList();
        System.out.println(books.size());
    }

    /**
     * 同 hibernate 中 Session 的 refresh 方法
     */
    @Test
    public void testRefresh(){
        Book book=entityManager.find(Book.class,1);
        book=entityManager.find(Book.class,1); //只会查询一次，因为EntityManager和 hibernate的Session一样，有一级缓存

        //从数据库获取最新的book对象，更新entityManager中的book持久对象
        entityManager.refresh(book);
    }

    /**
     * 同 hibernate 中 Session 的 flush 方法
     */
    @Test
    public void testFlush(){
        Book book = entityManager.find(Book.class,1);
        System.out.println(book);
        book.setBrief("出版于西安交通大学出版社，新东方出品。");
        entityManager.flush();
    }

    //若传入的是一个游离对象，即传入的对象有 OID，
    // 1.若在EntityManager 缓存中有该对象
    // 2. JPA 会把游离对象的属性复制到查询到的 EntityManager缓存中的对象中，
    // 3.查询到的EntityManager 缓存中的对象 执行 update
    @Test
    public void testMerge4() throws ParseException {
        Book book=new Book();
        book.setBookName("高等数学（第三版）下册");
        book.setAuthor("黄立宏，黄云清");
        book.setCategory("教科书");
        book.setPublishHouse("复旦大学出版社");
        book.setPublishDate(new SimpleDateFormat("yyyy-MM").parse("2010-05"));
        book.setPrice(34.00);
        book.setBrief("出版于复旦大学出版社的高等数学大学教科书1。");

        book.setId(3);

        Book book2=entityManager.find(Book.class,3);
        entityManager.merge(book);
        System.out.println(book == book2); // false
    }

    //若传入的是一个游离对象，即传入的对象有 OID，
    // 1.若在EntityManager 缓存中没有该对象
    // 2. 若在数据库中有对应的记录
    // 3. JPA 会查询对应的记录， 然后返回该记录对应的对象，再然后会把游离对象的属性复制到查询到的对象中，
    // 4. 对查询到的对象执行update 操作
    @Test
    public void testMerge3() throws ParseException {
        Book book=new Book();
        book.setBookName("高等数学（第三版）下册");
        book.setAuthor("黄立宏，黄云清");
        book.setCategory("教科书");
        book.setPublishHouse("复旦大学出版社");
        book.setPublishDate(new SimpleDateFormat("yyyy-MM").parse("2010-05"));
        book.setPrice(34.00);
        book.setBrief("出版于复旦大学出版社的高等数学大学教科书。");

        book.setId(3);

        Book book2=entityManager.merge(book);
        System.out.println(book == book2); // false
    }

    //若传入的是一个游离对象，即传入的对象有 OID，
        // 1.若在EntityManager 缓存中没有该对象
        // 2. 若在数据库中也没有对应的记录
        // 3. JPA 会创建一个新的对象， 然后把当前游离对象的属性复制到新创建的对象中，
        // 4. 对新的对象执行insert 操作
    @Test
    public void testMerge2() throws ParseException {
        Book book=new Book();
        book.setBookName("高等数学（第三版）下册");
        book.setAuthor("黄立宏，黄云清");
        book.setCategory("教科书");
        book.setPublishHouse("复旦大学出版社");
        book.setPublishDate(new SimpleDateFormat("yyyy-MM").parse("2010-05"));
        book.setPrice(34.00);
        book.setBrief("出版于复旦大学出版社的高等数学大学教科书");

        book.setId(100);

        Book book2=entityManager.merge(book);
        System.out.println("book#id: "+book.getId());
        System.out.println("book2#id: "+book2.getId());
    }

    /**
     * 总的来说： 类似于 hibernate Session 的 saveOrUpdate方法
     */
    //若传入的是一个临时对象，
    //会创建一个新的对象，把临时对象的属性复制到新的对象中，然后对新的对象执行持久化操作，所以
    //新的对象中有id，但以前的临时对象中没有id
    @Test
    public void testMerge() throws ParseException {
        Book book=new Book();
        book.setBookName("线性代数");
        book.setAuthor("周勇，朱砾");
        book.setCategory("教科书");
        book.setPublishHouse("复旦大学出版社");
        book.setPublishDate(new SimpleDateFormat("yyyy-MM").parse("2014-11"));
        book.setPrice(29.50);
        book.setBrief("出版于复旦大学出版社的线性代数大学教科书");

        Book book2=entityManager.merge(book);
        System.out.println("book#id: "+book.getId());
        System.out.println("book2#id: "+book2.getId());
    }

    //类似于 hibernate的 Session的 delete方法，把对象对应的记录从数据库中移除
    //但注意：该方法只能移除 持久化对象，而 hibernate的delete 方法实际上还可以移除 游离对象
    @Test
    public void testRemove(){
//        Book book=new Book();
//        book.setId(2);

        Book book=entityManager.find(Book.class,2);
        entityManager.remove(book);
    }

    // 类似于 hibernate的 save方法，使对象由临时状态变为持久化状态
    // 和 hibernate 的save方法的不同之处：若对象有id，则不能执行 insert操作，而会抛出异常
    @Test
    public void testPersistence() throws ParseException {
        Book book=new Book();
        book.setBookName("六级词汇词根+联想记忆法");
        book.setAuthor("俞敏洪");
        book.setCategory("工具书");
        book.setPublishDate(new SimpleDateFormat("yyyy-MM").parse("2013-11"));
        book.setPrice(32.00);
        book.setPublishHouse("西安交通大学出版社");
        book.setBrief("出版于西安交通大学出版社，新东方出品");
        entityManager.persist(book);
        System.out.println(book.getId());
    }

    //类似于 hibernate中 Session的 load方法
    @Test
    public void testGetReference(){
        //查询 book
        Book book=entityManager.getReference(Book.class,1);
        System.out.println(book.getClass().getName());
        // entityDao.after();
        //在这里after关闭 entityManager之后，book对象调用get set方法会报错，
        // 因为此时的book只是一个代理对象，还没有真正的访问数据库

        System.out.println("------------------------");
        System.out.println(book.toString());
    }

    //类似于 hibernate中 Session 的 get方法
    @Test
    public void testFind() {
        //查询 book
        Book book=entityManager.find(Book.class,1);
        System.out.println("------------------------");
        System.out.println(book.toString());
    }
}