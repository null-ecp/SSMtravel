package com.weison.services;

import com.weison.domain.Route;
import com.weison.domain.page;

import java.util.List;

/**
 * 路线事务处理接口
 */
public interface Routeservices {

    public page<Route> getshowpage(int cid, int current, int showtotal, String rname);

    public Route getdetial(int rid);

    public page<Route> getmyfavorite(int uid, int current, int showtotal);

    public List<Route> gethotroutes();

    public List<Route> getnewroutes();

    public List<Route> getinternalroutes();

    public List<Route> getinternationroutes();

    public List<Route> gethotshowlist();

    public page<Route> getrouteleadboard(int current, int showtotal, String rname, int minprice, int maxprice);
}
