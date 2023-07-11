package com;

import com.pojo.Record;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.Action;
import java.util.Date;

@SpringBootTest

@RunWith(SpringRunner.class)
class SpringbootSocketApplicationTests {
@Autowired
private UserType userType;
    @Test
    void contextLoads() {
//        Record record = new Record();
//        record.setDate(new Date());
//        record.setName("李四");
//        record.setMsg("去死吧");
//        userType.insert(record);
        System.out.println("22");
    }

}
