package com.tanhua.dubbo.api;

import com.tanhua.model.mongo.Movement;
import com.tanhua.model.vo.PageResult;

import java.util.List;

/**
 * @author sxs
 * @create 2022-09-17 14:56
 */
public interface MovementApi {
    void publish(Movement movement);

    PageResult findByUserId(Long userId, Integer page, Integer pageSize);

    List<Movement> findByFriendId(Long userId, Integer page, Integer pageSize);

    List<Movement> findByPids(List<Long> pidList);

    List<Movement> randomMovements(Integer pageSize);

    Movement findById(String id);
}
