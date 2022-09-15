package com.tanhua.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tanhua.dubbo.api.BlackListApi;
import com.tanhua.dubbo.api.QuestionApi;
import com.tanhua.dubbo.api.SettingsApi;
import com.tanhua.model.domain.Question;
import com.tanhua.model.domain.Settings;
import com.tanhua.model.domain.UserInfo;
import com.tanhua.model.vo.ErrorResult;
import com.tanhua.model.vo.PageResult;
import com.tanhua.model.vo.SettingVo;
import com.tanhua.server.exception.BusinessException;
import com.tanhua.server.interceptor.UserHolder;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author sxs
 * @create 2022-09-11 15:28
 */
@Service
public class SettingService {
    @DubboReference
    private SettingsApi settingsApi;
    @DubboReference
    private QuestionApi questionApi;
    @DubboReference
    private BlackListApi blackListApi;
    /**
     * 获取用户设置信息
     * @param
     * @return
     */
    public SettingVo getSettings() {
        Long userId = UserHolder.getId();
        SettingVo vo = new SettingVo();
        vo.setId(userId);
        vo.setPhone(UserHolder.getPhone());

        Question question = questionApi.findByUid(userId);
        vo.setStrangerQuestion(question==null?"你喜欢java吗":question.getTxt());
        Settings settings = settingsApi.findByUid(userId);
        vo.setGonggaoNotification(settings==null?true:settings.getGonggaoNotification()!=0);
        vo.setPinglunNotification(settings==null?true:settings.getPinglunNotification()!=0);
        vo.setLikeNotification(settings==null?true:settings.getLikeNotification()!=0);
        return vo;
    }

    /**
     *
     * @param content
     */
    public void saveQuestions(String content) {
        Long id = UserHolder.getId();
        Question questionOld = questionApi.findByUid(id);
        Question questionNew=new Question();
        questionNew.setTxt(content);
        if (questionOld==null){
            questionNew.setUserId(id);
            questionApi.save(questionNew);
        }else {
            questionNew.setId(questionOld.getId());
            questionApi.update(questionNew);
        }
    }

    /**
     * 通知设置 - 保存
     * @param map
     */
    public void saveNotifications(Map map) {
        Long id = UserHolder.getId();
        Settings settings = settingsApi.findByUid(id);
        Settings settingsNew=new Settings();
        settingsNew.setGonggaoNotification((Boolean)map.get("gonggaoNotification")?1:0);
        settingsNew.setPinglunNotification((Boolean) map.get("pinglunNotification")?1:0);
        settingsNew.setLikeNotification((Boolean) map.get("likeNotification")?1:0);
        if (settings==null){
            settingsNew.setUserId(id);
            settingsApi.save(settingsNew);
        }else {
            settingsNew.setId(settings.getId());
            settingsApi.update(settingsNew);
        }
    }

    /**
     * pageResult
     * @param page
     * @param pageSize
     * @return
     */
    public PageResult blacklist(int page, int pageSize) {
        Long id = UserHolder.getId();
        IPage<UserInfo> iPage = blackListApi.page(id, page, pageSize);
        long total = iPage.getTotal();//总记录数
        long pages = iPage.getPages();//总页数
        List<UserInfo> records = iPage.getRecords();
        return new PageResult( total, pageSize, pages, page, records);
    }

    public void deleteBlacklist(Long blackUserId) {
        if (blackUserId==null){
            throw new BusinessException(ErrorResult.empty());
        }
        blackListApi.delete(UserHolder.getId(),blackUserId);
    }
}
