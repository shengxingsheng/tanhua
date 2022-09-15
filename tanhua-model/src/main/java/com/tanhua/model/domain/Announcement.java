package com.tanhua.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 公告表
 * </p>
 *
 * @author sxs
 * @since 2022-09-11
 */
@Data
@TableName("tb_announcement")
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;


}
