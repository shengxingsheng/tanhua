package com.tanhua.dubbo.api;

import com.tanhua.model.mongo.RecommendUser;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author sxs
 * @create 2022-09-15 16:40
 */
@DubboService
public class RecommendUserApiImpl implements RecommendUserApi{
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public RecommendUser queryWithBestSource(Long toUserId) {
        Query query = Query.query(Criteria.where("toUserId").is(toUserId))
                .with(Sort.by(Sort.Order.desc("score")))
                .limit(1);
        return mongoTemplate.findOne(query, RecommendUser.class);
    }
}
