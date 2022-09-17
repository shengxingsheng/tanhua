package com.tanhua.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author sxs
 * @create 2022-09-11 21:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {

    /**
     * 总记录数
     * 最大值: 5000
     * 最小值: 100
     */
    private Long counts;
    /**
     * 页大小
     * 最大值: 50
     * 最小值: 5
     */
    private Integer pagesize;
    /**
     * 总页数
     * 最大值: 100
     * 最小值: 1
     */
    private Long pages;
    /**
     * 当前页码
     * 最大值: 100
     * 最小值: 1
     */
    private Integer page;
    /**
     * 列表
     * <p>
     * 最小数量: 10
     * 元素是否都不同: true
     * 最大数量: 20
     * item 类型: object
     */
    private List items = Collections.emptyList();

    public PageResult(Integer page, Integer pageSize, Long counts, List list) {
        this.page = page;
        this.pagesize = pageSize;
        this.items = list;
        this.counts = counts;
        this.pages = counts % pageSize == 0 ? counts / pageSize : counts / pageSize + 1;
    }


}
