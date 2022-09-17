package com.tanhua.server.service;

import com.tanhua.dubbo.api.RecommendUserApi;
import com.tanhua.dubbo.api.UserInfoApi;
import com.tanhua.model.domain.UserInfo;
import com.tanhua.model.dto.RecommendUserDto;
import com.tanhua.model.mongo.RecommendUser;
import com.tanhua.model.vo.PageResult;
import com.tanhua.model.vo.TodayBest;
import com.tanhua.server.interceptor.UserHolder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        TodayBest todayBest = TodayBest.init(userInfo, recommendUser);
        return todayBest;
    }
    /**
     * 首页推荐
     * TODO 逻辑有点问题
     */
    public PageResult recommendation(RecommendUserDto dto) {
        Long id = UserHolder.getId();
        //1.根据touserid获取RecommendUser中的userid-分页
        PageResult pr=recommendUserApi.queryRecommendUserList(dto.getPage(),dto.getPageSize(),id);
        List<RecommendUser> items = pr.getItems();
        if (items==null||items.isEmpty()){
            return null;
        }
        //2.userId集合
        List<Long> ids=(List<Long>)CollectionUtils.collect(pr.getItems(), input -> {
            RecommendUser recommendUser=(RecommendUser)input;
            return recommendUser.getUserId();
        });
        //3.去UserInfo中查询
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(dto, userInfo);
        Map<Long,UserInfo> userInfoList = userInfoApi.findByIds(ids,userInfo);
        //4.构建vo
        ArrayList<TodayBest> todayBests = new ArrayList<>();
        for (RecommendUser item : items) {
            UserInfo info = userInfoList.get(item.getUserId());
            if (info!=null){
                todayBests.add(TodayBest.init(info, item));
            }
        }
        pr.setItems(todayBests);
        return pr;
    }
}
