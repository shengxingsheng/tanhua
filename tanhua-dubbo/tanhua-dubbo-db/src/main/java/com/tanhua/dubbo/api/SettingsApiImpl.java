package com.tanhua.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tanhua.dubbo.mapper.SettingsMapper;
import com.tanhua.model.domain.Settings;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sxs
 * @create 2022-09-11 16:54
 */
@DubboService
public class SettingsApiImpl implements SettingsApi{
    @Autowired
    SettingsMapper settingsMapper;

    @Override
    public Settings findByUid(Long uid) {
        LambdaQueryWrapper<Settings> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Settings::getUserId ,uid );
        Settings settings = settingsMapper.selectOne(wrapper);
        return settings;
    }

    @Override
    public void save(Settings settings) {
        settingsMapper.insert(settings);
    }

    @Override
    public void update(Settings settings) {
        settingsMapper.updateById(settings);
    }
}
