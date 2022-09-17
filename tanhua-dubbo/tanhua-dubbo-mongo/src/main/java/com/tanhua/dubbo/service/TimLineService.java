package com.tanhua.dubbo.service;

import com.tanhua.model.mongo.Friend;
import com.tanhua.model.mongo.MovementTimeLine;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sxs
 * @create 2022-09-17 15:11
 */
@Service
public class TimLineService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Async
    public void save(ObjectId id,Long userId) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        List<Friend> friendList = mongoTemplate.find(query, Friend.class);
        for (Friend friend : friendList) {
            MovementTimeLine timeLine = new MovementTimeLine();
            timeLine.setMovementId(id);
            timeLine.setCreated(System.currentTimeMillis());
            timeLine.setUserId(userId);
            timeLine.setFriendId(friend.getFriendId());
            mongoTemplate.save(timeLine);
        }
    }
}
