package com.tanhua.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tanhua.dubbo.mapper.BlackListMapper;
import com.tanhua.dubbo.mapper.UserInfoMapper;
import com.tanhua.model.domain.BlackList;
import com.tanhua.model.domain.UserInfo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sxs
 * @create 2022-09-11 17:17
 */
@DubboService
public class BlackListApiImpl implements BlackListApi{
    @Autowired
    BlackListMapper blackListMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public BlackList findByUid(Long uid) {
        LambdaQueryWrapper<BlackList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BlackList::getUserId ,uid );
        BlackList blackList = blackListMapper.selectOne(wrapper);
        return blackList;
    }
    @Override
    public IPage<UserInfo> page(Long userId, Integer page, Integer size){
        Page<UserInfo> pages = new Page<>(page, size);
        return userInfoMapper.blackList(pages,userId);

    }

    @Override
    public void delete(Long id, Long blackUserId) {
        LambdaQueryWrapper<BlackList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BlackList::getUserId, id);
        wrapper.eq(BlackList::getBlackUserId, blackUserId);
        blackListMapper.delete(wrapper);
    }
}
