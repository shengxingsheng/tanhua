package com.tanhua.dubbo.api;

import com.tanhua.model.mongo.RecommendUser;

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
}
