package com.tanhua.server.service;

import com.tanhua.dubbo.api.RecommendUserApi;
import com.tanhua.dubbo.api.UserInfoApi;
import com.tanhua.model.domain.UserInfo;
import com.tanhua.model.mongo.RecommendUser;
import com.tanhua.model.vo.TodayBest;
import com.tanhua.server.interceptor.UserHolder;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author sxs
 * @create 2022-09-15 17:47
 */
@Service
public class TanhuaService {

    @DubboReference
    RecommendUserApi recommendUserApi;
    @DubboReference
    UserInfoApi userInfoApi;
    public TodayBest todayBest() {
        Long id = UserHolder.getId();
        RecommendUser recommendUser = recommendUserApi.queryWithBestSource(id);
        Long userId = recommendUser.getUserId();
        UserInfo userInfo = userInfoApi.findById(userId);
        TodayBest todayBest = new TodayBest();
        BeanUtils.copyProperties(userInfo, todayBest);
        todayBest.setFateValue(recommendUser.getScore().longValue());
        todayBest.setTags(userInfo.getTags()==null?null:userInfo.getTags().split(","));
        return todayBest;
    }
}
