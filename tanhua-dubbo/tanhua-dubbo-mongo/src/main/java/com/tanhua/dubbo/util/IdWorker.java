package com.tanhua.dubbo.util;

import com.tanhua.model.mongo.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * @author sxs
 * @create 2022-09-17 13:35
 */
@Component
public class IdWorker {
    @Autowired
    private MongoTemplate mongoTemplate;
    public  Long getNextId(String collName){
        Criteria criteria = Criteria.where("collName").is(collName);
        Query query = Query.query(criteria);

        Update update = new Update();
        update.inc("seqId",1);//自增1

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true); //不存在就插入
        options.returnNew(true); //返回最新的数据

        Sequence sequence = mongoTemplate.findAndModify(query, update, options, Sequence.class);
        return sequence.getSeqId();
    }
}
