package com.tanhua.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sxs
 * @create 2022-09-11 16:44
 */
@Data
public class SettingVo implements Serializable {

    private Long id;
    /**
     * 陌生人问题
     */
    private String strangerQuestion;
    private String phone;
    /**
     * 推送喜欢通知
     */
    private Boolean likeNotification;
    /**
     * 推送评论通知
     */
    private Boolean pinglunNotification;
    /**
     *推送公告通知
     */
    private Boolean gonggaoNotification;
}
