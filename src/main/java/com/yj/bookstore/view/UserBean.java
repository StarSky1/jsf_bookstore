package com.yj.bookstore.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * @Author 76355
 * @Date 2019/4/27 11:57
 * @Description
 */
//=================关键1===============
@ManagedBean(name = "user")
@SessionScoped
public class UserBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private String name;

    @Inject
    private MessageBean messageBean;


    public MessageBean getMessageBean() {
        return messageBean;
    }

    public void setMessageBean(MessageBean messageBean) {
        this.messageBean = messageBean;
    }

    public String getSayWelcome(){
        //check if null?
        if("".equals(name) || name ==null){
            return "";
        }else{
            if(messageBean!=null){
                return messageBean.getSayWelcome()+name;
            }
            return name;
        }
    }

    //=================关键2===============
    @PostConstruct
    public void init() {
        System.out.println("UserBean.init()");
        setName("我是76355");
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }




}
