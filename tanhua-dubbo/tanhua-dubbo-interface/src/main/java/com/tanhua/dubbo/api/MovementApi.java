package com.tanhua.dubbo.api;

import com.tanhua.model.mongo.Movement;
import com.tanhua.model.vo.PageResult;

/**
 * @author sxs
 * @create 2022-09-17 14:56
 */
public interface MovementApi {
    void publish(Movement movement);

    PageResult findByUserId(Long userId, Integer page, Integer pageSize);
}
