package com.comont;

import java.util.HashMap;
import java.util.Map;

public class Result extends HashMap<String,Object> {
    public static  Result ok(){
        Result result = new Result();
        result.put("code",1);
        result.put("msg","成功");
        return result;
    }
    public static  Result erro(){
        Result result = new Result();
        result.put("code",0);
        result.put("msg","失败");
        return result;
    }
    public static  Result ok(String code,String msg){
        Result result = new Result();
        result.put("code",code);
        result.put("msg",msg);
        return result;
    }
    public static Result erro(String code,String msg){
        Result result = new Result();
        result.put("code",code);
        result.put("msg",msg);
        return result;
    }


}
