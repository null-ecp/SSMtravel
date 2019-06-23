package com.weison.dao;

import com.weison.domain.Seller;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 卖家信息持久层接口
 */
@Repository("sellerDao")
public interface SellerDao {

    @Select("select seller.* from tab_seller seller, tab_route route where route.rid = #{rid}")
    public Seller findsellerbyrid(@Param("rid") int rid);

}
