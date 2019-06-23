package com.weison.dao;

import com.weison.domain.RouteImg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 路线图片持久层接口
 */
@Repository("routeImgDao")
public interface RouteImgDao {

    @Select("select * from tab_route_img where rid = #{rid}")
    public List<RouteImg> findimgbyrid(@Param("rid") int rid);

}
