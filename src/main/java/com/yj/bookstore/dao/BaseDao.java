package com.yj.bookstore.dao;

/**
 * @Author 76355
 * @Date 2019/4/29 7:59
 * @Description
 */
public interface BaseDao<T,ID> {

    T findById(ID id);

    int updateById(T t);

    T insertRecord(T t);

    void deleteById(ID id);
}
