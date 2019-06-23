package com.weison.Contorller;

import com.weison.domain.Route;
import com.weison.domain.User;
import com.weison.domain.page;
import com.weison.services.Favoriteservice;
import com.weison.services.Routeservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户收藏 控制器
 */
@Controller
@RequestMapping("/Favorite")
public class FavoriteController {

    @Autowired
    private Favoriteservice favoriteservice;

    @Autowired
    private Routeservices routeservices;

    /**
     * 判断当前登录用户是否收藏
     * @param request
     * @param rid
     * @return
     */
    @RequestMapping("/checkcollect")
    public @ResponseBody boolean checkcollect(HttpServletRequest request, String rid){
        // 判断当前是否有登录的用户
        User loginuser = (User) request.getSession().getAttribute("loginuser");
        int uid;
        if (loginuser == null) {
            uid = 0;
        } else {
            uid = loginuser.getUid();
        }
        return favoriteservice.isfavorite(Integer.parseInt(rid), uid);
    }

    @RequestMapping("/favoritecount")
    public @ResponseBody int favoritecount(String rid){
        return favoriteservice.favoritecount(Integer.parseInt(rid));
    }

    @RequestMapping("/route2favorite")
    public @ResponseBody int route2favorite(HttpServletRequest request, String rid){
        // 判断当前是否有登录的用户
        User loginuser = (User) request.getSession().getAttribute("loginuser");
        int uid;
        if (loginuser == null) {
            return 0;
        } else {
            uid = loginuser.getUid();
        }
        return favoriteservice.favoriteroute(rid, loginuser.getUid());
    }

    @RequestMapping("/myfavorite")
    public @ResponseBody page<Route> getmyfavorite(int uid,
                                                   @RequestParam(name = "current", required = false) String currentstr,
                                                   @RequestParam(name = "showtotal", required = false) String showtotalstr){

        // 第一次访问默认为1
        int current = 0;
        if (currentstr != null && currentstr.length() > 0){
            current = Integer.parseInt(currentstr);
        } else {
            current = 1;
        }
        // 没有指定每页显示条目默认为12
        int showtotal = 0;
        if (showtotalstr != null && showtotalstr.length() > 0){
            showtotal = Integer.parseInt(showtotalstr);
        } else {
            showtotal = 12;
        }

        page<Route> myfavoritepage = routeservices.getmyfavorite(uid, current, showtotal);
        return myfavoritepage;
    }
}
