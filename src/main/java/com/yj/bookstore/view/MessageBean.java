package com.yj.bookstore.view;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * @Author 76355
 * @Date 2019/4/27 12:22
 * @Description
 */
@ManagedBean(name = "message")
@SessionScoped
public class MessageBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private String sayWelcome = "Welcome to JSF 2.0";

    public String getSayWelcome() {
        return sayWelcome;
    }

    public void setSayWelcome(String sayWelcome) {
        this.sayWelcome = sayWelcome;
    }
}
