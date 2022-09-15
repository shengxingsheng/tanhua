package com.tanhua.model.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author sxs
 * @since 2022-09-11
 */
@Data
@TableName("tb_user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户标签：多个用逗号分隔
     */
    private String tags;

    /**
     * 性别，1-男，2-女，3-未知
     */
    private String gender;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 学历
     */
    private String education;

    /**
     * 居住城市
     */
    private String city;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 封面图片
     */
    private String coverPic;

    /**
     * 行业
     */
    private String profession;

    /**
     * 收入
     */
    private String income;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    /**
     * 0：未婚，1：已婚
     */
    private Integer marriage;


}
