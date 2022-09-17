package com.tanhua.model.vo;

import com.tanhua.model.domain.UserInfo;
import com.tanhua.model.mongo.Movement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sxs
 * @create 2022-09-17 16:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementVo {
    private String id; //动态id

    private Long userId; //用户id
    private String avatar; //头像
    private String nickname; //昵称
    private String gender; //性别 man woman
    private Integer age; //年龄
    private String[] tags; //标签


    private String textContent; //文字动态
    private String[] imageContent; //图片动态
    private String distance; //距离
    private String createDate; //发布时间 如: 10分钟前
    private Integer likeCount; //点赞数
    private Integer commentCount; //评论数
    private Integer loveCount; //喜欢数


    private Integer hasLiked; //是否点赞（1是，0否）
    private Integer hasLoved; //是否喜欢（1是，0否）

    public static MovementVo init(Movement movement, UserInfo userInfo) {
        MovementVo vo = new MovementVo();
        BeanUtils.copyProperties(movement,vo);
        BeanUtils.copyProperties(userInfo,vo);
        //动态id
        vo.setId(String.valueOf(movement.getId()));
        //标签
        if (!StringUtils.isEmpty(userInfo.getTags())){
            vo.setTags(userInfo.getTags().split(","));
        }
        //图片列表
        vo.setImageContent(movement.getMedias().toArray(new String[0]));
        //距离
        vo.setDistance("500米");
        //发布时间
        Long created = movement.getCreated();
        Date date = new Date(created);
        vo.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        //设置是否点赞(后续处理)
        vo.setHasLoved(0);
        vo.setHasLiked(0);
        return vo;
    }
}
