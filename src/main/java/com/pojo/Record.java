package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
@NoArgsConstructor
@Accessors(chain = true)
@AllArgsConstructor
@Data
public class Record implements Serializable {
    private String id;
    private  String name;
    private  String msg;
    private Date  date;


}
