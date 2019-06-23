package com.weison.services;


/**
 * 用户收藏业务处理接口
 */
public interface Favoriteservice {

    public boolean isfavorite(int rid, int uid);

    public int favoritecount(int rid);

    public int favoriteroute(String rid, int uid);
}
