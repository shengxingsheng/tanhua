package com.tanhua.dubbo.api;

import com.tanhua.model.domain.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @author sxs
 * @create 2022-09-08 21:36
 */
public interface UserInfoApi {

    void save(UserInfo userInfo);
    void update(UserInfo userInfo);
    UserInfo findById(Long id);

    Map<Long, UserInfo> findByIds(List<Long> ids, UserInfo userInfo);
}
