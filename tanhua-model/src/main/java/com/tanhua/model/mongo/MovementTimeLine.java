package com.tanhua.model.mongo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author sxs
 * @create 2022-09-17 14:03
 */
@Data
@Document(collection = "movement_timeline")
public class MovementTimeLine {
    private ObjectId id;
    private ObjectId movementId;//动态id
    private Long userId;   //发布动态用户id
    private Long friendId; // 可见好友id
    private Long created; //发布的时间
}
