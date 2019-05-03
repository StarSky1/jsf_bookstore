package com.yj.bookstore.model.dto;

import lombok.Data;

@Data
public class SelectItem {

    private String column;

    private String expression;

    private Object value;

    public SelectItem() {
    }

    public SelectItem(String column, String expression, Object value) {
        this.column = column;
        this.expression = expression;
        this.value = value;
    }
}
