package com.yj.bookstore.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author 76355
 * @Date 2019/4/29 10:21
 * @Description
 */
@Data
public class PageObject<T> {
    private List<T> list;
    private Integer total;

    public PageObject() {
    }

    public PageObject(List<T> list, Integer total) {
        this.list = list;
        this.total = total;
    }
}
