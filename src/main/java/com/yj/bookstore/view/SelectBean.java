package com.yj.bookstore.view;


import lombok.Data;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.Date;

@Named
@Singleton
@Data
public class SelectBean implements Serializable{

    private String bookName;

    private Date startDate;

    private Date endDate;

    private String category;

    private Double startPrice;

    private Double endPrice;

    private String brief;

    public void select(){
        FacesMessage msg = new FacesMessage("查询成功", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void reset(){
        this.bookName=null;
        this.startDate=null;
        this.endDate=null;
        this.category=null;
        this.startPrice=null;
        this.endPrice=null;
        this.brief=null;
        FacesMessage msg = new FacesMessage("查询已清空", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
