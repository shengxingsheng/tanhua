package com.tanhua.model.vo;

import com.tanhua.model.domain.UserInfo;
import com.tanhua.model.mongo.RecommendUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author sxs
 * @create 2022-09-15 17:43
 */
@Data
public class TodayBest {
    private Long id;
    private String avatar;
    private String nickname;
    private String gender;
    private Integer age;
    private String[] tags;
    private Long fateValue;

    public static TodayBest init(UserInfo userInfo, RecommendUser recommendUser){
        TodayBest todayBest = new TodayBest();
        BeanUtils.copyProperties(userInfo, todayBest);
        todayBest.setFateValue(recommendUser.getScore().longValue());
        todayBest.setTags(userInfo.getTags()==null?null:userInfo.getTags().split(","));
        return todayBest;
    }
}
