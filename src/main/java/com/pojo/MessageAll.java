package com.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageAll implements Serializable {
    private  String user;
    private  String msg;
    private String date;



    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
