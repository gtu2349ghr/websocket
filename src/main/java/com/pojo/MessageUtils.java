package com.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public class MessageUtils implements Serializable {
    /**
     * 将消息转换成json字符串
     * @param isSystem
     * @param fromName
     * @param message
     * @return
     */
    public static String getMessage(boolean isSystem,String fromName,Object message,Object userList,Object onSet){

        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setMessage(message);
        serverMessage.setSystem(isSystem);
        serverMessage.setOnSet(onSet);
        serverMessage.setLiSet(userList);
        if(fromName!=null){
            serverMessage.setFromName(fromName);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return  objectMapper.writeValueAsString(serverMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getMessageAll(boolean isSystem,String fromName,Object message,Object userList,Object onSet,String name,String date){

        ServerMessageAll serverMessageAll = new ServerMessageAll();
        serverMessageAll.setMessage(message);
        serverMessageAll.setSystem(isSystem);
        serverMessageAll.setOnSet(onSet);
        serverMessageAll.setLiSet(userList);
        serverMessageAll.setDate(date);
        serverMessageAll.setName(name);
        if(fromName!=null){
            serverMessageAll.setFromName(fromName);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return  objectMapper.writeValueAsString(serverMessageAll);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getMessagePv(boolean isSystem,String toName,Object message,String name,String date){

        ServerMessagePv serverMessageAll = new ServerMessagePv();
        serverMessageAll.setMessage(message);
        serverMessageAll.setSystem(isSystem);
        serverMessageAll.setDate(date);
        serverMessageAll.setName(name);
        serverMessageAll.setToName(toName);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return  objectMapper.writeValueAsString(serverMessageAll);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
