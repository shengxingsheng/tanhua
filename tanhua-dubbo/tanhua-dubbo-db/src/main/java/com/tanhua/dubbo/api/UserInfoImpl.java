package com.tanhua.dubbo.api;

import com.tanhua.dubbo.mapper.UserInfoMapper;
import com.tanhua.model.domain.UserInfo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sxs
 * @create 2022-09-08 21:47
 */
@DubboService
public class UserInfoImpl implements UserInfoApi{
    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public void save(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    @Override
    public void update(UserInfo userInfo) {
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public UserInfo findById(Long id) {
        return userInfoMapper.selectById(id);
    }
}
