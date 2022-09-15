package com.tanhua.dubbo.api;

import com.tanhua.model.domain.User;

/**
 * @author sxs
 * @create 2022-09-07 14:27
 */
public interface UserApi {

    /**
     * 根据手机号查找用户
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 新增用户
     * @param user
     */
    Long save(User user);

    void update(User user);
//    User save(User user);
}
