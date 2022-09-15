package com.tanhua.model.vo;

import lombok.Data;

/**
 * @author sxs
 * @create 2022-09-15 17:43
 */
@Data
public class TodayBest {
    private Long id;
    private String avatar;
    private String nickname;
    private String gender;
    private Integer age;
    private String[] tags;
    private Long fateValue;
}
