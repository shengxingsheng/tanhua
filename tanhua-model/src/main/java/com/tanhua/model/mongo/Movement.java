package com.tanhua.model.mongo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * 用用户动态表
 * @author sxs
 * @create 2022-09-17 13:52
 */
@Data
@Document("movement")
public class Movement implements Serializable {
    private ObjectId id; //主键id
    private Long pid; //Long类型，用于推荐系统的模型(自动增长)
    private Long created; //发布时间
    private Long userId;
    private String textContent; //文字
    private List<String> medias; //媒体数据，图片或小视频 url
    private String longitude; //经度
    private String latitude; //纬度
    private String locationName; //位置名称
    private Integer state = 0;//状态 0：未审（默认），1：通过，2：驳回
}
