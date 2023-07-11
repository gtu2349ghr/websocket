package com.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.comont.Result;
import com.mongodb.PvUserType;
import com.mongodb.UserType;
import com.pojo.PvRecord;
import com.pojo.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.DateUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class controller {
      @Autowired
        RedisTemplate  redisTemplate;
      @Autowired
      MongoTemplate mongoTemplate;
      @Autowired
    UserType userType;
    @Autowired
    PvUserType pvUserType;
    @RequestMapping("/index")
    public String index(){

        return "index";
    }
    @RequestMapping("chat")
    public String chat(@PathParam(value = "user") String user, Model model){
        Jedis jedis=null;
        //在群发页面的时候把两人聊天的jedis删除
        try{
           jedis = new Jedis("127.0.0.1",6379);
            jedis.auth("518610");
            jedis.hdel("map",user);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            jedis.close();
        }


        model.addAttribute("user",user);
        //开始遍历，拿聊天记录
        Query query = new Query();
        query.with(Sort.by(Sort.Order.asc("Date")));
        List<Record> objects = mongoTemplate.find(query, Record.class);
        for(Record record:objects){
            Date date = record.getDate();
            DateTime date1 = DateUtil.date(date);
            record.setDate(date1);
        }
        model.addAttribute("userName",user);
        model.addAttribute("list",objects);
        return "chat";
    }
    @RequestMapping("pvChat")
    public String pvChat(@RequestParam(value = "user") String user,
                         @RequestParam(value = "name") String name,
                         Model model
                         ){
        System.out.println(user+"这个是user"+name+"这个是name");
        HashMap<String, String> map = new HashMap<>();
        map.put(user,name);
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.auth("518610");
        //添加到set集合中
        jedis.hset("map",map);
        System.out.println(jedis.hget("map",user)+"a--------------------------");
        //这里是单聊查询，根据自己的名字和对方的名字查

        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("name").is(user),Criteria.where("toName").is(name));
        Criteria criteria1 = new Criteria();
        criteria1.andOperator(Criteria.where("name").is(name),Criteria.where("toName").is(user));
        Query query = new Query(new Criteria().orOperator(criteria,criteria1));
        query.with(Sort.by(Sort.Order.asc("Date")));
        List<PvRecord> pvList = mongoTemplate.find(query, PvRecord.class);
        for(PvRecord record:pvList){
            Date date = record.getDate();
            DateTime date1 = DateUtil.date(date);
            record.setDate(date1);
            System.out.println("查询的数据"+record.getName()+"给"+record.getToName()+"发："+record.getMsg()+record.getDate());
            //这里将date格式化
        }
        model.addAttribute("pvList",pvList);
        model.addAttribute("user",user);
        model.addAttribute("toName",name);
        return "pvChat";
    }
    @RequestMapping("/send")
    @ResponseBody
    public Result send(@RequestParam(value = "name") String name

                       ){
       redisTemplate.opsForValue().set("username",name);
        System.out.println(redisTemplate.opsForValue().get("username"));
        return Result.ok();
    }

    @RequestMapping("/sendDo")
    @ResponseBody
    public Result sendDo(@RequestParam(value = "name") String name,
                         @RequestParam(value = "msg") String msg,
                         @RequestParam(value = "date") String date

    ){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTime parse = DateUtil.parse(date, simpleDateFormat);
        Record record = new Record();
        record.setMsg(msg);
        record.setDate(parse);
        record.setName(name);
         userType.insert(record);
        return Result.ok();
    }
    @RequestMapping("/sendPvDo")
    @ResponseBody
    public Result sendPvDo(@RequestParam(value = "name") String name,
                            @RequestParam(value = "toName") String toName,
                         @RequestParam(value = "msg") String msg,
                         @RequestParam(value = "date") String date

    ){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTime parse = DateUtil.parse(date, simpleDateFormat);
        PvRecord record = new PvRecord();
        record.setMsg(msg);
        record.setDate(parse);
        record.setName(name);
        record.setToName(toName);
        pvUserType.insert(record);
        return Result.ok();
    }

}
