package com.tanhua.model.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author sxs
 * @since 2022-09-11
 */
@Data
@TableName("tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码，需要加密
     */
    private String password;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    private String hxUser;

    private String hxPassword;


}
