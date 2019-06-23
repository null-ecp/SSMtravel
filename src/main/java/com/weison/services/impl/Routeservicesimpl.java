package com.weison.services.impl;

import com.weison.dao.RouteDao;
import com.weison.dao.RouteImgDao;
import com.weison.dao.SellerDao;
import com.weison.domain.Route;
import com.weison.domain.RouteImg;
import com.weison.domain.Seller;
import com.weison.domain.page;
import com.weison.services.Routeservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 路线查询业务处理
 */
@Service("routeservices")
// 查询事务处理
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class Routeservicesimpl implements Routeservices {

    @Autowired
    private RouteDao routeDao;
    @Autowired
    private RouteImgDao routeImgDao;
    @Autowired
    private SellerDao sellerDao;

    /**
     * 获取显示页面
     * @param cid 显示页面记录的分类id
     * @param current 当前显示页码
     * @param showtotal 当前页面显示条数
     * @param rname 搜索内容
     * @return 返回显示页面对象
     */
    @Override
    public page<Route> getshowpage(int cid, int current, int showtotal, String rname) {

        // 调用dao查询显示记录
        List<Route> routes = routeDao.getshowlist(cid, showtotal * (current - 1), showtotal, "%"+rname+"%");
        // 查询的分类线路总记录数
        int routecount = routeDao.getroutecount(cid,"%"+rname+"%");
        // 创建显示对象 并设置参数
        page<Route> showpage = new page<Route>();
        showpage.setCurrent(current);
        showpage.setPagecount(routecount % showtotal == 0 ? routecount / showtotal : routecount / showtotal + 1);
        showpage.setRouteList(routes);
        showpage.setShowtotal(showtotal);
        showpage.setTotalcount(routecount);

        return showpage;
    }

    /**
     * 根据路线id获取路线信息
     * @param rid
     * @return
     */
    @Override
    public Route getdetial(int rid) {
        // 获取指定rid的route对象
        Route route = routeDao.finddetialbyrid(rid);
        // 获取route对象的轮播图并设置
        List<RouteImg> imgs = routeImgDao.findimgbyrid(rid);
        route.setRouteImgList(imgs);
        // 获取卖家信息
        Seller seller = sellerDao.findsellerbyrid(rid);
        route.setSeller(seller);
        return route;
    }

    @Override
    public page<Route> getmyfavorite(int uid, int current, int showtotal) {
        // 获取收藏路线集合
        List<Route> routes = routeDao.getmyfavorite(uid,showtotal * (current - 1), showtotal);
        // 获取收藏记录条数
        int count = routeDao.getmyfavoritecount(uid);
        // 创建显示对象 并设置参数
        page<Route> showpage = new page<Route>();
        showpage.setRouteList(routes);
        showpage.setPagecount(count % showtotal == 0 ? count / showtotal : count / showtotal + 1);
        showpage.setCurrent(current);
        showpage.setTotalcount(count);
        showpage.setShowtotal(showtotal);
        return showpage;
    }

    @Override
    public List<Route> gethotroutes() {
        return routeDao.gethotroutes();
    }

    @Override
    public List<Route> getnewroutes() {
        return routeDao.getnewroutes();
    }

    @Override
    public List<Route> getinternalroutes() {
        return routeDao.getinternalroutes();
    }

    @Override
    public List<Route> getinternationroutes() {
        return routeDao.getinternationroutes();
    }

    @Override
    public List<Route> gethotshowlist() {
        return routeDao.gethotshowlist();
    }

    @Override
    public page<Route> getrouteleadboard(int current, int showtotal, String rname, int minprice, int maxprice) {
        // 获取收藏路线集合
        List<Route> routes = routeDao.getrouteleadboard(showtotal * (current - 1), showtotal,"%"+rname+"%", minprice, maxprice);
        // 获取收藏记录条数
        int count = routeDao.gethotlistcount("%"+rname+"%", minprice, maxprice);
        // 创建显示对象 并设置参数
        page<Route> showpage = new page<Route>();
        showpage.setTotalcount(count);
        showpage.setShowtotal(showtotal);
        showpage.setCurrent(current);
        showpage.setRouteList(routes);
        showpage.setPagecount(count % showtotal == 0 ? count / showtotal : count / showtotal + 1);
        return showpage;
    }
}
