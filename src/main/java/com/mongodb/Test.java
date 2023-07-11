package com.mongodb;

import com.pojo.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
public class Test {
    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        Test test = new Test();
        test.test();
    }
    public void test(){
        List<Record> objects = mongoTemplate.findAll(Record.class);
        if(objects!=null){
            for(Record re:objects){
                System.out.println(re.getDate());
            }
        }
    }
}
