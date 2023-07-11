package com.pojo;

import lombok.Data;

@Data
public class ServerMessageAll {
    private  boolean isSystem;
    private  String fromName;
    private  Object message;
    private  Object liSet;
    private  String name;
    private  String  date;
    private  Object onSet;

}
