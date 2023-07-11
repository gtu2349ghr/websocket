package com.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.UserType;
import com.pojo.MessageAll;
import com.pojo.MessagePv;
import com.pojo.MessageUtils;
import com.pojo.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Component
@ServerEndpoint(value = "/talk/{user}")
public class SocketEndpoint {
   private  Jedis jedis;
//   @Resource
//   RedisTemplate redisTemplate;//这个是注入不进去的
    //存储用户列表
    private static  Map<String,SocketEndpoint>  onlearn=new ConcurrentHashMap<>();
    //这个是在线的列表
    private  static   Set<String> set1 = new HashSet();
    private  static   Set<String> allSet = new HashSet();
    //存储session，通过他将消息发送给指定用户
    private  Session session;
    //存储httpsesion获取用户名
    /**
     * 建立连接时自动调用
     * @param session
     * @param config
     */
    @OnOpen
    public void open(@PathParam(value = "user") String user,
                     Session session, EndpointConfig config){
        String name1=null;
        try{
            jedis = new Jedis("127.0.0.1",6379);
            jedis.auth("518610");
            //添加到set集合中
            jedis.sadd("set1",user);
            jedis.sadd("allSet",user);
            set1.add(user);
           name1= jedis.hget("map",user);
            this.session=session;
//        String user = redisTemplate.opsForValue().get("username").toString();

            onlearn.put(user,this);
            Set<String> liSet = liSet();
            Set<String> onSet = getOnSet();
            //然后进行消息的通知，谁加入群聊通知给所有，通知的话通知的是在线的，但是消息总数是所有
            System.out.println("-------------"+name1+"这个是name1");
            if(name1==null){
                String message = MessageUtils.getMessage(true, null,user,liSet,onSet);
                //然后进行推送
                SendAllMessage(message);
            }else {
                String message = MessageUtils.getMessage(false, null,name1,liSet,onSet);
                //然后进行推送
              SendPvMessage(message,user,name1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }

        }


    public void SendAllMessage(String message){
//        //这里只有在线的才拿到对象
        Set<String> set = jedis.sdiff("set1");
        for(String s:set){
            System.out.println(s+"这个是set集合里的");
            SocketEndpoint socketEndpoint = onlearn.get(s);
            try {
                socketEndpoint.session.getBasicRemote().sendText(message);
             } catch (IOException e) {
                 e.printStackTrace();
             }
        }

    }


    public void SendPvMessage(String message,String user,String toName){
            SocketEndpoint socketEndpoint = onlearn.get(toName);
            SocketEndpoint socketEndpoint1 = onlearn.get(user);
            try {
                socketEndpoint.session.getBasicRemote().sendText(message);
                socketEndpoint1.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }






    /**
     * 拿到所有人的用户名
     * @return
     */
    public Set<String> getName(){
       return  allSet = jedis.sdiff("allSet");
//        return onlearn.keySet();
    }

    /**
     * 在线列表
     * @return
     */
    public Set<String> getOnSet(){
        return  allSet = jedis.sdiff("set1");
//        return onlearn.keySet();
    }

    /**
     * 收到消息时掉用
     * @param message
     * @param session
     */
    @OnMessage
    public  void message(@PathParam(value = "user")String user,
            String message,Session session){

        this.session=session;
        jedis = new Jedis("127.0.0.1",6379);
        jedis.auth("518610");
        String toName1 = jedis.hget("map", user);
        //收到消息
        if(toName1==null){
            ObjectMapper objectMapper = new ObjectMapper();
            //将其json转化为我们指定的类型
            try {

                MessageAll messageAll = objectMapper.readValue(message, MessageAll.class);
                //封装参数
                String   name=messageAll.getUser();
                String  date=messageAll.getDate();
                String  msg=messageAll.getMsg();
                liSet().remove(user);
                System.out.println("这里开始调用了");
                Set<String> liSet11 = liSet();
                Set<String> onSet = getOnSet();
                String messageAll1 = MessageUtils.getMessageAll(true, "1", msg,liSet11,onSet, name, date);
                SendAllMessage(messageAll1);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else {
            ObjectMapper objectMapper = new ObjectMapper();
            //将其json转化为我们指定的类型
            try {

                MessagePv messagePv = objectMapper.readValue(message, MessagePv.class);
                //封装参数
                String   name=messagePv.getUser();
                String  date=messagePv.getDate();
                String  msg=messagePv.getMsg();
                String name111=messagePv.getToName();
                //这里表示发消息了,提示新消息
                Set<String> liSet11 = liSet();
                Set<String> onSet = getOnSet();
                String messageAll1 = MessageUtils.getMessageAll(false, "6", msg,liSet11,onSet, name, date);
                SendAllMessage(messageAll1);
                //并且将新消息加载到jedis中，第一次进页面的时候进行显示




//下面是私发

                //私发的时候
                String messagePv1 = MessageUtils.getMessagePv(false,name111,msg,name,date);
                SendPvMessage(messagePv1,name,name111);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * 求不在线的人列表
     */
    public  Set<String> liSet(){
        Set<String> sdiff = jedis.sdiff("allSet", "set1");
        for(String s:sdiff){
            System.out.println(s+"差集");
        }
        return jedis.sdiff("allSet","set1");
    }

    /**
     * 连接断开时调用
     */

    @OnClose
    public  void close(@PathParam(value = "user")String user,Session session){
        //用jedis维护在线列表，因为发消息的时候只有在线的才能发
        //从redis移除不在线的成员
        jedis.srem("set1",user);
        set1.remove(user);
//        Object user = redisTemplate.opsForValue().get("user");
//        removeSet(user);
        System.out.println("断开连接"+user);
        this.session=session;
//        String user = redisTemplate.opsForValue().get("username").toString();
        //这里是维护在线列表：当有人退出的时候就通知，然后列表里想减
        String message = MessageUtils.getMessage(true, "2", user,liSet(),getOnSet());
        //然后进行推送
        SendAllMessage(message);
    }
    @OnError
    public  void erro(@PathParam(value = "user")String user,Session session,Throwable throwable){
        this.session=session;
        System.out.println(user+"前置定位伟");
        jedis.srem("set1",user);
        jedis.hdel("map",user);
        set1.remove(user);
    }

    /**\
     * 浏览器强制关闭的时候去移除用户
     * @param user
     */
    public  void removeSet(String user){
        jedis.del(user);
        set1.remove(user);
        Set<String> strings = onlearn.keySet();
        for(String s:strings){
            if(s.equals(user)){
                onlearn.remove(s);
            }
        }

    }



}
