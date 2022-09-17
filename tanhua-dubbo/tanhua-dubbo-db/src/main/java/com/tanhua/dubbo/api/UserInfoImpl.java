package com.tanhua.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tanhua.dubbo.mapper.UserInfoMapper;
import com.tanhua.model.domain.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<Long, UserInfo> findByIds(List<Long> ids, UserInfo userInfo) {
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in( UserInfo::getId, ids);
        //TODO 空指针问题
/*        wrapper.eq(userInfo!=null && userInfo.getGender()!=null, UserInfo::getGender ,userInfo.getGender());
        wrapper.le(userInfo!=null && userInfo.getAge()!=null, UserInfo::getAge ,userInfo.getAge());*/
        if (userInfo!=null){
            if (!StringUtils.isEmpty(userInfo.getGender())){
                wrapper.eq(UserInfo::getGender ,userInfo.getGender());
            }
            if (userInfo.getAge()!=null){
                wrapper.le(UserInfo::getAge, userInfo.getAge());
            }
        }
        List<UserInfo> userInfoList = userInfoMapper.selectList(wrapper);
        Map<Long, UserInfo> map = new LinkedHashMap<>();
        for (UserInfo info : userInfoList) {
            map.put(info.getId(), info);
        }
        return map;
    }
}
