package com.weison.Contorller;

import com.weison.domain.ResultInfo;
import com.weison.domain.User;
import com.weison.services.Userservice;
import com.weison.util.MailUtils;
import com.weison.util.UuidUtil;
import com.weison.util.checkcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User 类 控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Userservice userservice;

    /**
     * 获取当前登录用户
     * @param request
     * @return 登录用户
     */
    @RequestMapping("/getloginuser")
    public @ResponseBody User getloginuser(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("loginuser");
        if (user == null){
            user = new User();
            user.setName("admin");
        }
        return user;
    }

    /**
     * 用户登录
     * @param incode 用户输入的验证码
     * @param username 用户名
     * @param password 密码
     * @return 提示信息
     */
    @RequestMapping("/login")
    public @ResponseBody ResultInfo login(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam("check") String incode, String username, String password){
        // 获取验证码
        String code = (String) request.getSession().getAttribute("code");
        // 创建一个返回消息的对象
        ResultInfo info = new ResultInfo();
        // 判断验证码
        if (code != null && checkcode.checkinput(incode, code)) {
            // 删除验证码 , 保证验证码的唯一性
            request.getSession().removeAttribute("code");
            // 获取登录对象
            User loginuser = userservice.logincheck(username, password);
            if (loginuser == null){             // 没查到即为没有该用户或者信息输入错误
                info.setFlag(false);
                info.setErrorMsg("用户名或密码输入错误 , 请刷新验证码后重试");
            } else if ("N".equals(loginuser.getStatus())){  // 查询到但是状态为N则未激活则需要先去激活
                info.setFlag(false);
                info.setErrorMsg("用户未激活 , 请先去邮箱激活");
            }else{  // 非这两中的情况下就是正常登录
                info.setFlag(true);
                request.getSession().setAttribute("loginuser",loginuser);
            }
        } else {
            info.setFlag(false);
            info.setErrorMsg("验证码输入错误 , 请刷新验证码后重试");
        }
        return info;
    }

    /**
     * 用户退出
     * @param request
     * @return 重定向页面
     */
    @RequestMapping("/exit")
    public String exit(HttpServletRequest request){
        // 销毁当前session
        request.getSession().invalidate();
        // 重定向到首页
        return "redirect:/";
    }

    /**
     * 注册页面用户校队用户名重复
     * @param username
     * @return
     */
    @RequestMapping("/checkname")
    public @ResponseBody Map<String, Boolean> checkname(String username){
        // 创建map集合用于存放返回数据
        Map<String, Boolean> data = new HashMap<String, Boolean>();
        // 判断用户是否存在
        if (username != null || username != "") {
            if (userservice.checkusername(username)) {
                data.put("hasusername", true);
            } else {
                data.put("hasusername", false);
            }
        }
        return data;
    }

    /**
     * 用户注册
     * @param request
     * @param user 封装的用户注册信息
     * @return 提示信息
     */
    @RequestMapping("/regist")
    public @ResponseBody ResultInfo regist(HttpServletRequest request,
                                          User user){
        // 删除验证码 (避免重复使用恶意注册)
        request.getSession().removeAttribute("code");
        user.setCode(UuidUtil.getUuid());
        // 注册初始状态都为未激活
        user.setStatus("N");
        // 调用userservice来注册 用户
        boolean status = userservice.registeruser(user);
        // 创建一个结果信息对象存储返回数据
        ResultInfo info = new ResultInfo();
        // 判断是否注册成功
        if (status){
            info.setFlag(true);
            // 成功发送激活邮件 获取发送邮件的信息 标题 收件地址
            String sendto = user.getEmail();
            String tittle = "黑马旅游网账号激活";
            String content = "<h1><a href=\"http://localhost/travel/user/active?code="+user.getCode()+"\">[点我激活账号]黑马旅游网</a></h1>";
            // 发送邮件
            MailUtils.sendMail(sendto, content, tittle);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败 , 用户名重复或信息异常 , 请重新输入");
        }
        return info;
    }

    /**
     * 激活用户
     * @param request
     * @param response
     * @param code 自动封装的COde标识码
     * @throws IOException
     */
    @RequestMapping("/active")
    public void active(HttpServletRequest request, HttpServletResponse response,
                       String code) throws IOException {
        // 创建usersercice对象
        boolean flag = userservice.activeuser(code);
        // 设置响应头类型
        response.setContentType("text/html;charset=utf-8");
        // 定义一个字符串存放回显消息
        String msg = null;
        if (flag) {
            msg = "<h1 style='text-alian:center;'><a href='/travel/login.html'>激活成功 , 点击进入登录页面</a></h1>";
        } else {
            msg = "<h1 style='text-alian:center;'>激活失败 , 可能由于网络或者恶意篡改激活码造成 , 请稍后重试</h1>";
        }
        response.getWriter().write(msg);
    }
}
