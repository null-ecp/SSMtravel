package com.weison.services.impl;

import com.weison.dao.UserDao;
import com.weison.domain.User;
import com.weison.services.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userservice")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class Userserviceimpl implements Userservice {

    // 创建一个userdao对象为成员对象
    @Autowired
    private UserDao userdao;


    /**
     * 判断用户名是否存在
     * @param username 判断的用户名
     * @return
     */
    @Override
    public boolean checkusername(String username) {
        // 查询指定用户
        User user = userdao.findbyusername(username);
        // 判断
        if (user != null){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 注册用户
     * @param user 封装的用户信息
     * @return 返回注册状态
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean registeruser(User user) {
        // 先判断是否重复用户名
        if(this.checkusername(user.getUsername())){
            return false;
        }
        // 修改默认信息
        if (user.getName() == null ||user.getName().equals("")) {
            user.setName(user.getUsername());
        }
        if ( user.getBirthday() == null || user.getBirthday().equals("")) {
            user.setBirthday("2000-01-01");
        }
        // 添加用户
        int status = userdao.adduser(user);
        if (status > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 激活用户
     * @param code 用户的唯一标识码
     * @return 返回是否成功激活
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean activeuser(String code) {
        // 调用userdao来完成数据库的信息修改
        int update = userdao.updateustatus(code);
        // 影响行数不为0 则为修改成功
        if (update != 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 登录校验
     * @param username 用户名
     * @param password 密码
     * @return 返回登录成功的user对象
     */
    @Override
    public User logincheck(String username, String password) {
        return userdao.findbynmnpd(username, password);
    }
}
