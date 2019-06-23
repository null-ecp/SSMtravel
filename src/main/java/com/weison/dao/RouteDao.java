package com.weison.dao;

import com.weison.domain.Route;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 路线持久层接口
 */
@Repository("routeDao")
public interface RouteDao {

    /**
     * 获取分类页面路线条数
     * @param cid 分类id
     * @param rname 模糊查询名
     * @return
     */
    public int getroutecount(@Param("cid") int cid, @Param("rname") String rname);

    /**
     * 获取页面展示路线集合
     * @param cid 分类id
     * @param start 起始索引
     * @param temp 显示条数
     * @param rname 模糊查询名
     * @return
     */
    public List<Route> getshowlist(@Param("cid") int cid,
                                   @Param("start") int start,
                                   @Param("temp") int temp,
                                   @Param("rname") String rname);

    /**
     * 通过路线id查询路线
     * @param rid
     * @return
     */
    public Route finddetialbyrid(@Param("rid") int rid);


    /**
     * 通过用户id查询用户收藏路线集合
     * @param uid 用户id
     * @param start 起始索引
     * @param temp 页面显示记录数
     * @return
     */
    public List<Route> getmyfavorite(@Param("uid") int uid,
                                     @Param("start") int start,
                                     @Param("temp") int temp);

    /**
     * 用户收藏路线总条数
     * @param uid
     * @return
     */
    public int getmyfavoritecount(@Param("uid") int uid);

    /**
     * 获取首页显示的热门路线集合
     * @return
     */
    public List<Route> gethotroutes();

    /**
     * 获取最新的路线集合
     * @return
     */
    public List<Route> getnewroutes();

    /**
     * 获取国内路线集合
     * @return
     */
    public List<Route> getinternalroutes();

    /**
     * 获取国外路线集合
     * @return
     */
    public List<Route> getinternationroutes();

    /**
     * 获取分类页面的热门路线
     * @return
     */
    public List<Route> gethotshowlist();

    /**
     * 更新路线收藏次数
     * @param rid
     * @param count
     * @return
     */
    public int setroutecount(@Param("rid") int rid,@Param("count") int count);

    /**
     * 获取收藏排行榜集合
     * @return
     */
    public List<Route> getrouteleadboard(@Param("start") int start,
                                         @Param("temp") int temp,
                                         @Param("rname") String rname,
                                         @Param("minprice") int minprice,
                                         @Param("maxprice") int maxprice);

    /**
     * 获取已被收藏的路线条数
     * @return
     */
    public int gethotlistcount(@Param("rname") String rname,
                               @Param("minprice") int minprice,
                               @Param("maxprice") int maxprice);
}
