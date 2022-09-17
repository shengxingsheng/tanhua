package com.tanhua.dubbo.api;

import com.tanhua.model.mongo.RecommendUser;
import com.tanhua.model.vo.PageResult;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

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

    @Override
    public PageResult queryRecommendUserList(Integer page, Integer pageSize, Long toUserid) {
        Query query = Query.query(Criteria.where("toUserId").is(toUserid));
        long count = mongoTemplate.count(query, RecommendUser.class);
        query = Query.query(Criteria.where("toUserId").is(toUserid)).with(Sort.by(Sort.Order.desc("score")))
                .limit(pageSize)
                .skip(pageSize*(page-1));
        List<RecommendUser> list = mongoTemplate.find(query, RecommendUser.class);

        return new PageResult(page, pageSize, count, list);
    }
}
