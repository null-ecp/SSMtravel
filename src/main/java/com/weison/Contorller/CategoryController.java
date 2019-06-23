package com.weison.Contorller;

import com.weison.domain.Category;
import com.weison.services.Categoryservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 分类 控制器
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private Categoryservice categoryservice;

    /**
     * 获取所有的分类集合
     * @return json类型的数据对象
     */
    @RequestMapping("/getall")
    public @ResponseBody List<Category> getCategory(){
        return categoryservice.getcategories();
    }

}
