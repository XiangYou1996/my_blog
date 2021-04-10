package com.blog.Service;

import com.blog.Entity.BlogTagCount;
import com.blog.Util.PageQueryUtil;
import com.blog.Util.PageResult;

import java.util.List;

public interface TagService {

    PageResult getBlogTagPage(PageQueryUtil pageUtil);

    int getTotalTags();

    Boolean saveTag(String tagName);

    Boolean deleteBatch(Integer[] ids);

    List<BlogTagCount> getBlogTagCountForIndex();
}
