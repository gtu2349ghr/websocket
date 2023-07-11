package com.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class ServerMessagePv {
    private  boolean isSystem;
    private  String name;
    private  String toName;
    private  Object message;
    private String date;
}
