package com.yj.bookstore.dao;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @Author 76355
 * @Date 2019/4/27 17:54
 * @Description
 */
@Named
public class EntityDao {

    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;
    private EntityTransaction entityTransaction;
    private String persistenceUnitName="jpa-1";

    public void init(){
        // 1.创建 EntityManagerFactory
        entityManagerFactory= Persistence.createEntityManagerFactory(persistenceUnitName);
        // 2. 创建 EntityManager
        entityManager=entityManagerFactory.createEntityManager();
        // 3. 创建 EntityTransaction
        entityTransaction=entityManager.getTransaction();
        // 开启事务
        entityTransaction.begin();
    }

    public EntityDao() {

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }


    public void after(){
        // 提交事务
        entityTransaction.commit();
        // 1. 关闭 EntityManager
        entityManager.close();
        // 2. 关闭 EntityManagerFactory
        entityManagerFactory.close();
    }
}
