package com.weison.domain;

import java.util.List;

/**
 * 路线显示页面bean
 * @param <Route>
 */
public class page<Route> {
    private int pagecount;                              // 总页码数
    private int current;                                // 当前页码
    private int totalcount;                             // 总记录数
    private int showtotal;                              // 每页显示条目数
    private List<Route> routeList;                      // 记录集合

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getShowtotal() {
        return showtotal;
    }

    public void setShowtotal(int showtotal) {
        this.showtotal = showtotal;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

}
