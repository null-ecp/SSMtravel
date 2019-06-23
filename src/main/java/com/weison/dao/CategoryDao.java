package com.weison.dao;

import com.weison.domain.Category;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分类查询持久化接口
 */
@Repository("categoryDao")
public interface CategoryDao {

    @Select("select * from tab_category")
    public List<Category> getcategories();
}
