package com.weison.dao;

import com.weison.domain.Favorite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 收藏类持久化接口
 */
@Repository("favoriteDao")
public interface FavoriteDao {

    @Select("select * from tab_favorite where rid = #{rid} and uid = #{uid}")
    public Favorite findroutebyridnuid(@Param("rid") int rid, @Param("uid") int uid);

    @Select("select count(*) from tab_favorite where rid = #{rid}")
    public int getfavoritecountbyrid(@Param("rid") int rid);

    @Insert("insert into tab_favorite values(#{rid},#{time},#{uid})")
    public int addfavorite(@Param("rid") int rid, @Param("time") String time, @Param("uid") int uid);

}
