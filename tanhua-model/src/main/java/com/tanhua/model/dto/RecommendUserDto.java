package com.tanhua.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sxs
 * @create 2022-09-16 21:00
 */
@Data
public class RecommendUserDto implements Serializable {
    private Integer page=1;//当前页数
    private Integer pageSize=10;//页尺寸
    private String gender;//性别
    private String lastLogin;//近期登陆时间
    private Integer age;//年龄
    private String city;//居住地
    private String education;//学历
}
