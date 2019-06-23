package com.weison.services.impl;

import com.weison.dao.CategoryDao;
import com.weison.domain.Category;
import com.weison.services.Categoryservice;
import com.weison.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 分类接口事务实现类
 */
@Service("categoryservice")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class categoryserviceimpl implements Categoryservice {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> getcategories() {
        // 先从缓存数据库中查询
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        // 判断是否有缓存
        if (category == null || category.size() == 0){
            System.out.println("select data in sql");
            // 没有缓存则进入数据库查询 并将数据添加进缓存
            List<Category> categories = categoryDao.getcategories();
            // 遍历循环添加
            for (Category c : categories) {
                jedis.zadd("category", c.getCid(), c.getCname());
            }
            return categories;
        } else {
            System.out.println("select data in redis");
            List<Category> list = new ArrayList<Category>();
            // 将获取到的缓存数据转换格式返回
            for (Tuple tuple : category) {
                Category c = new Category();
                c.setCname(tuple.getElement());
                c.setCid((int) tuple.getScore());
                list.add(c);
            }
            return list;
        }
    }
}
