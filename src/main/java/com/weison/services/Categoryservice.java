package com.weison.services;

import com.weison.domain.Category;

import java.util.List;

/**
 * 处理分类数据的事务接口
 */
public interface Categoryservice {

    public List<Category> getcategories();

}
