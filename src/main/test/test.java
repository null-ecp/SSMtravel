import com.weison.dao.FavoriteDao;
import com.weison.dao.RouteDao;
import com.weison.domain.Category;
import com.weison.domain.Route;
import com.weison.services.Categoryservice;
import com.weison.services.impl.categoryserviceimpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class test {

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private FavoriteDao favoriteDao;

    @Test
    public void test(){
        List<Route> routes = routeDao.gethotshowlist();
        for (Route route : routes) {
            routeDao.setroutecount(route.getRid(), favoriteDao.getfavoritecountbyrid(route.getRid()));
//            System.out.println(route);


//        cell.setCellStyle(cellStyleContent2);
//        String value = (String) projectMap.get(key);
//        cell.setCellValue(value);

        }
    }
}
