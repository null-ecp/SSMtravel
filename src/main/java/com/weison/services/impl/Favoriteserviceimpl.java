package com.weison.services.impl;

import com.weison.dao.FavoriteDao;
import com.weison.dao.RouteDao;
import com.weison.domain.Favorite;
import com.weison.services.Favoriteservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 收藏业务处理类
 */
@Service("favoriteservice")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class Favoriteserviceimpl implements Favoriteservice {

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private RouteDao routeDao;

    /**
     * 根据路线id和用户id判断用户是否收藏该路线
     * @param rid 路线id
     * @param uid 用户id
     */
    @Override
    public boolean isfavorite(int rid, int uid) {
        Favorite favorite = favoriteDao.findroutebyridnuid(rid, uid);
        return favorite != null;
    }

    /**
     * 获取路线的收藏次数
     * @param rid
     * @return
     */
    @Override
    public int favoritecount(int rid) {
        return favoriteDao.getfavoritecountbyrid(rid);
    }

    /**
     * 收藏路线
     * @param rid
     * @param uid
     * @return
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int favoriteroute(String rid, int uid) {
        Date date = new Date();
        int id = Integer.parseInt(rid);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(date);
        // 收藏添加进favorite表
        int status = favoriteDao.addfavorite(id, time, uid);
        // 更新路线表路线的收藏记录
        routeDao.setroutecount(id, favoriteDao.getfavoritecountbyrid(id));
        return status;
    }

}
