package com.topjoy.omtools.common.entity;

import org.springframework.data.domain.Sort;

import java.io.Serializable;


/**
 * @FileName PageModel.java
 * @Description:分页模型
 * @version V1.0
 * @createtime 2018年8月8日 下午3:39:35
 */

public class PageModel implements Serializable {

    private static final long serialVersionUID = 1L;
    // 当前页
    private Integer pagenumber = 1;
    // 当前页面条数
    private Integer pagesize = 10;
    // 排序条件
    private Sort sort;

    public Integer getPagenumber() {
        return pagenumber;
    }

    public void setPagenumber(Integer pagenumber) {
        this.pagenumber = pagenumber;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}