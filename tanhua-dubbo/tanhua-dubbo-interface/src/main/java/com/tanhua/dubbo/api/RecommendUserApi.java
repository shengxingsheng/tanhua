package com.tanhua.dubbo.api;

import com.tanhua.model.mongo.RecommendUser;
import com.tanhua.model.vo.PageResult;

/**
 * @author sxs
 * @create 2022-09-15 16:36
 */
public interface RecommendUserApi {
    /**
     * 找到分数最高的
     * @param toUserId
     * @return
     */
    RecommendUser queryWithBestSource(Long toUserId);

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    PageResult queryRecommendUserList(Integer page, Integer pageSize, Long toUserid);
}
