package com.weison.Contorller;


import com.weison.domain.Route;
import com.weison.domain.page;
import com.weison.services.Routeservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 路线 控制器
 */
@Controller
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private Routeservices routeservices;

    /**
     * 获取显示路线记录的页面封装对象
     * @param cidstr 分类id
     * @param currentstr 当前页面
     * @param showtotalstr 显示条数
     * @param rname 路线索引名 可模糊查询
     * @return
     */
    @RequestMapping("/getshowpage")
    public @ResponseBody page<Route> getshowpage(
            @RequestParam(name = "cid", required = false) String cidstr,
            @RequestParam(name = "current", required = false) String currentstr,
            @RequestParam(name = "showtotal", required = false) String showtotalstr,
            String rname){
        // 修改
        int cid = 0;
        if (cidstr != null && cidstr.length() > 0){
            cid = Integer.parseInt(cidstr);
        }
        // 第一次访问默认为1
        int current = 0;
        if (currentstr != null && currentstr.length() > 0){
            current = Integer.parseInt(currentstr);
        } else {
            current = 1;
        }
        // 没有指定每页显示条目默认为6
        int showtotal = 0;
        if (showtotalstr != null && showtotalstr.length() > 0){
            showtotal = Integer.parseInt(showtotalstr);
        } else {
            showtotal = 6;
        }
        if (rname.equals("null")) {
            rname = "";
        }

        // 调用services进行处理
        return routeservices.getshowpage(cid, current, showtotal,rname);
    }

    /**
     * 显示路线详细信息
     * @param rid 路线id
     * @return
     */
    @RequestMapping("/detial")
    public @ResponseBody Route getroutedetial(String rid){
        // 调用service获取指定rid的详细信息
        return routeservices.getdetial(Integer.parseInt(rid));
    }

    @RequestMapping("/hotroutes")
    public @ResponseBody List<Route> gethotroutes(){
        return routeservices.gethotroutes();
    }

    @RequestMapping("/newroutes")
    public @ResponseBody List<Route> getnewroutes(){
        return routeservices.getnewroutes();
    }

    @RequestMapping("/internalroutes")
    public @ResponseBody List<Route> getinternalroutes(){
        return routeservices.getinternalroutes();
    }

    @RequestMapping("/internationroutes")
    public @ResponseBody List<Route> getinternationroutes(){
        return routeservices.getinternationroutes();
    }

    @RequestMapping("/hotshowlist")
    public @ResponseBody List<Route> gethotshowlist(){
        return routeservices.gethotshowlist();
    }

    @RequestMapping("/leadboard")
    public @ResponseBody page<Route> getrouteleadboard(
            @RequestParam(name = "current", required = false) String currentstr,
            @RequestParam(name = "showtotal", required = false) String showtotalstr,
            @RequestParam(name = "minprice", required = false) String minpricestr ,
            @RequestParam(name = "maxprice", required = false) String maxpricestr ,
            String rname){
        // 没有指定每页显示条目默认为12
        int showtotal = 8;
        if (showtotalstr != null && showtotalstr.length() > 0){
            showtotal = Integer.parseInt(showtotalstr);
        }
        int minprice = 0;
        if (minpricestr != null && minpricestr.length() > 0){
            minprice = Integer.parseInt(minpricestr);
        }
        int maxprice = 99999999;
        if (maxpricestr != null && maxpricestr.length() > 0){
            maxprice = Integer.parseInt(maxpricestr);
        }
        // 第一次访问默认为1
        int current = 1;
        if (currentstr != null && currentstr.length() > 0){
            current = Integer.parseInt(currentstr);
        }
        if (rname.equals("null")) {
            rname = "";
        }
        return routeservices.getrouteleadboard(current, showtotal, rname, minprice, maxprice);
    }
}
