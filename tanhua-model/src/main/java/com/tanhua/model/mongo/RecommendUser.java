package com.tanhua.model.mongo;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author sxs
 * @create 2022-09-15 16:27
 */
@Document("recommend_user")
@Data
public class RecommendUser implements Serializable {
    private ObjectId id; //主键id
    private Long userId; //推荐的用户id
    private Long toUserId; //用户id
    private Double score =0d; //推荐得分
    private String date; //日期
}
