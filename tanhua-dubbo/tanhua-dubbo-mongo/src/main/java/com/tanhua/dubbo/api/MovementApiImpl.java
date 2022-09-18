package com.tanhua.dubbo.api;

import com.tanhua.dubbo.service.TimLineService;
import com.tanhua.dubbo.util.IdWorker;
import com.tanhua.model.mongo.Movement;
import com.tanhua.model.mongo.MovementTimeLine;
import com.tanhua.model.vo.PageResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author sxs
 * @create 2022-09-17 14:57
 */
@DubboService
public class MovementApiImpl implements MovementApi{
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TimLineService timLineService;
    @Override
    public void publish(Movement movement) {
        //1.完善movement
        movement.setCreated(System.currentTimeMillis());
        movement.setPid(idWorker.getNextId("movement"));
        //2.保存动态
        mongoTemplate.save(movement);
        //3，异步保存动态时间线
        timLineService.save(movement.getId(),movement.getUserId());
    }

    @Override
    public PageResult findByUserId(Long userId, Integer page, Integer pageSize) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        long count = mongoTemplate.count(query, Movement.class);
        query = Query.query(Criteria.where("userId").is(userId))
                .limit(pageSize)
                .skip(pageSize*(page-1))
                .with(Sort.by(Sort.Order.desc("created")));
        List<Movement> movements = mongoTemplate.find(query, Movement.class);
        return new PageResult(page, pageSize, count, movements);
    }

    @Override
    public List<Movement> findByFriendId(Long friendId, Integer page, Integer pageSize) {
        //1.获取时间线
        Query query = Query.query(Criteria.where("friendId").is(friendId))
                .skip(pageSize * (page - 1))
                .limit(pageSize)
                .with(Sort.by(Sort.Order.desc("created")));
        List<MovementTimeLine> list = mongoTemplate.find(query, MovementTimeLine.class);
        //2.获取动态id集合
        List<ObjectId> ids = (List<ObjectId>) CollectionUtils.collect(list, input -> ((MovementTimeLine)input).getMovementId());
        //3.获取动态集合
        query= Query.query(Criteria.where("id").in(ids));
        List<Movement> movementList = mongoTemplate.find(query, Movement.class);
        return movementList;
    }


    /**
     * 根据pid获取
     */
    @Override
    public List<Movement> findByPids(List<Long> pidList) {
        Query query = Query.query(Criteria.where("pid").in(pidList));
        List<Movement> list = mongoTemplate.find(query, Movement.class);
        return list;
    }

    /**
     * 随机获取
     */
    @Override
    public List<Movement> randomMovements(Integer counts) {
        TypedAggregation<Movement> aggregation = Aggregation.newAggregation(Movement.class, Aggregation.sample(counts));
        AggregationResults<Movement> movements = mongoTemplate.aggregate(aggregation, Movement.class);
        return movements.getMappedResults();
    }
}
