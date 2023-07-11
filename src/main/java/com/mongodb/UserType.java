package com.mongodb;

import com.pojo.Record;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserType extends MongoRepository<Record,String> {
     Record getAllByDateAndAndMsgAndName = null;
}
