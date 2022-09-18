package com.tanhua.model.mongo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author sxs
 * @create 2022-09-18 14:16
 */
@Data
@Document("comment")
public class Comment implements Serializable {
    private ObjectId id;
    /**
     * 被评论动态id
     */
    private ObjectId publishId;
    /**
     * 评论类型，1-点赞，2-评论，3-喜欢
     */
    private Integer commentType;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论人id
     */
    private Long userId;
    /**
     * 被评论人ID
     */
    private Long publishUserId;
    /**
     * 发表时间
     */
    private Long created;
    /**
     * 当前评论的点赞数
     */
    private Integer likeCount = 0;
}
