package com.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServerMessage  implements Serializable {
    private  boolean isSystem;
    private  String fromName;
    private  Object message;
    private  Object liSet;
    private  Object onSet;


}
