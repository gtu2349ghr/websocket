package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
public class PvRecord implements Serializable {
    private String id;
    private  String name;
    private  String toName;
    private  String msg;
    private Date date;
}
