package com.weison.Contorller;

import com.weison.util.checkcode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码 控制器
 */
@Controller
public class CheckCodeController {

    /**
     * 获取验证码图片
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/checkCode")
    public void getCheckcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");

        // 宽80 高40
        BufferedImage bimg = new BufferedImage(80, 40, BufferedImage.TYPE_INT_RGB);
        // 画图
        checkcode.getcheckcode(bimg);
        // 将验证码传到session
        request.getSession().setAttribute("code", checkcode.getCode());
        System.out.println((String) request.getSession().getAttribute("code"));
        // 输出
        ImageIO.write(bimg,"PNG",response.getOutputStream());
    }

    /**
     * 验证输入验证码
     * @param request
     * @param incode 用户输入
     * @return
     */
    @RequestMapping("/getcode")
    public @ResponseBody Map<String, Boolean> getcode(HttpServletRequest request,
                                                      String incode){
        // 获取验证码
        String code = (String) request.getSession().getAttribute("code");
        // 创建一个标记来判断是否输入正确
        boolean flag = code != null && checkcode.checkinput(incode, code) ? true : false;
        // 创建map存放
        Map<String, Boolean> json = new HashMap<String, Boolean>();
        json.put("instatus", flag);
        return json;
    }
}
