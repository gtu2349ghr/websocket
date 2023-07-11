package com.mongodb;

import com.pojo.PvRecord;
import com.pojo.Record;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PvUserType extends MongoRepository<PvRecord,String> {

    List<PvRecord> findByName(String name,String toName);
}
