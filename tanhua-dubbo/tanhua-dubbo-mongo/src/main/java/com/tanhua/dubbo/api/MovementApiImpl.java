package com.tanhua.dubbo.api;

import com.tanhua.dubbo.service.TimLineService;
import com.tanhua.dubbo.util.IdWorker;
import com.tanhua.model.mongo.Movement;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

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
}
