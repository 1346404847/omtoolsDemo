package com.topjoy.omtools.common.dao;

import com.topjoy.omtools.common.entity.PageModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import java.io.Serializable;

/**
 * @FileName SpringDataPageable.java
 * @Description:分页公共类
 * @author JackHisen(gu.weidong)
 * @version V1.0
 * @createtime 2018年8月8日 下午4:39:35
 */

public class SpringbootPageable implements Serializable, Pageable {


    private static final long serialVersionUID = 1L;

    PageModel page;

    public  PageModel getPage()
    {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }

    @Override
    public Pageable first()
    {
        return null;
    }

    @Override
    public int getOffset()
    {
        return (page.getPagenumber() - 1) * page.getPagesize();
    }
    @Override
    public int getPageNumber() {
        // TODO Auto-generated method stub
        return page.getPagenumber();
    }
    @Override
    public int getPageSize() {
        // TODO Auto-generated method stub
        return page.getPagesize();
    }

    @Override
    public boolean hasPrevious() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Pageable next() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Sort getSort() {
        // TODO Auto-generated method stub
        return page.getSort();
    }
}