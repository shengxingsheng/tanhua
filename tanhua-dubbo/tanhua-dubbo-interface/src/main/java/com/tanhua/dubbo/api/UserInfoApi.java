package com.tanhua.dubbo.api;

import com.tanhua.model.domain.UserInfo;

/**
 * @author sxs
 * @create 2022-09-08 21:36
 */
public interface UserInfoApi {

    void save(UserInfo userInfo);
    void update(UserInfo userInfo);
    UserInfo findById(Long id);
}
