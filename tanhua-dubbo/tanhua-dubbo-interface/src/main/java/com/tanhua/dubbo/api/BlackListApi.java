package com.tanhua.dubbo.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tanhua.model.domain.BlackList;
import com.tanhua.model.domain.UserInfo;

/**
 * @author sxs
 * @create 2022-09-11 16:53
 */
public interface BlackListApi {
    /**
     * 获取黑名单信息
     * @param uid
     * @return
     */
    BlackList findByUid(Long uid);
    IPage<UserInfo> page(Long userId, Integer page, Integer size);

    void delete(Long id, Long blackUserId);
}
