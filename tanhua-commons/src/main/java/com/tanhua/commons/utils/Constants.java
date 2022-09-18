package com.tanhua.commons.utils;

/**
 * 常量定义
 *
 * @author sxs
 * @create 2022-09-18 1:00
 */
public class Constants {

    /**
     * 手机APP短信验证码
     */
    public static final String SMS_CODE = "CHECK_CODE_";

    /**
     * 手机APP短信验证码-更换手机号
     */
    public static final String MODIFY_CODE = "MODIFY_CODE_";

    /**
     * 手机号长度
     */
    public static final Integer PHONE_LENGTH = 11;
    /**
     * 验证码长度
     */
    public static final Integer CODE_LENGTH = 6;

    /**
     * 验证码过期时间,单位：分钟
     */
    public static final Long CODE_DEADLINE = 5L;
    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    //推荐动态
    public static final String MOVEMENTS_RECOMMEND = "MOVEMENTS_RECOMMEND_";

    //推荐视频
    public static final String VIDEOS_RECOMMEND = "VIDEOS_RECOMMEND_";

    //圈子互动KEY
    public static final String MOVEMENTS_INTERACT_KEY = "MOVEMENTS_INTERACT_";

    //动态点赞用户HashKey
    public static final String MOVEMENT_LIKE_HASHKEY = "MOVEMENT_LIKE_";

    //动态喜欢用户HashKey
    public static final String MOVEMENT_LOVE_HASHKEY = "MOVEMENT_LOVE_";

    //视频点赞用户HashKey
    public static final String VIDEO_LIKE_HASHKEY = "VIDEO_LIKE";

    //访问用户
    public static final String VISITORS = "VISITORS";

    //关注用户
    public static final String FOCUS_USER = "FOCUS_USER_{}_{}";

    //初始化密码
    public static final String INIT_PASSWORD = "123456";

    //环信用户前缀
    public static final String HX_USER_PREFIX = "hx";

    //jwt加密盐
    public static final String JWT_SECRET = "itcast";

    //jwt超时时间
    public static final int JWT_TIME_OUT = 3_600;
}
