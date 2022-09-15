package com.tanhua.dubbo.api;

import com.tanhua.model.domain.Settings;

/**
 * @author sxs
 * @create 2022-09-11 16:49
 */
public interface SettingsApi {

    /**
     * 获取设置信息
     * @param uid
     * @return
     */
    Settings findByUid(Long uid);

    void save(Settings settings);

    void update(Settings settings);
}
