package com.tanhua.model.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 设置表
 * </p>
 *
 * @author sxs
 * @since 2022-09-11
 */
@Data
@TableName("tb_settings")
public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    /**
     * 推送喜欢通知
     */
    private Integer likeNotification;

    /**
     * 推送评论通知
     */
    private Integer pinglunNotification;

    /**
     * 推送公告通知
     */
    private Integer gonggaoNotification;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;


}
