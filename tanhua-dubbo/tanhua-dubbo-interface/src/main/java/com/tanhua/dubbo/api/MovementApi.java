package com.tanhua.dubbo.api;

import com.tanhua.model.mongo.Movement;

/**
 * @author sxs
 * @create 2022-09-17 14:56
 */
public interface MovementApi {
    void publish(Movement movement);
}
