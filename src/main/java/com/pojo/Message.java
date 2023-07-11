package com.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class Message implements Serializable {
    private  String message;
    private  String toName;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(message, message1.message) && Objects.equals(toName, message1.toName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, toName);
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", toName='" + toName + '\'' +
                '}';
    }
}
