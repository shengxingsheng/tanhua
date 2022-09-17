package com.tanhua.model.mongo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 好友表:好友关系表
 * @author sxs
 * @create 2022-09-17 14:01
 */
@Data
@Document("friend")
public class Friend implements Serializable {
    private ObjectId id;
    private Long userId; //用户id
    private Long friendId; //好友id
    private Long created; //时间
}
