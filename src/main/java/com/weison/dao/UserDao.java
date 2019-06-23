package com.weison.dao;

import com.weison.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository("userdao")
public interface UserDao {

    @Select("select * from tab_user where username = #{username}")
    public User findbyusername(@Param("username") String username);

    @Insert("insert into tab_user values(null,#{username},#{password}," +
            "#{name},#{birthday},#{sex},#{telephone},#{email},#{status},#{code})")
    public int adduser(User user);

    @Update("update tab_user set status = 'Y' where code = #{code}")
    public int updateustatus(@Param("code") String code);

    @Select("select * from tab_user where username = #{username} and password = #{password}")
    public User findbynmnpd(@Param("username") String username,@Param("password") String password);

    @Select("select * from tab_user where uid = #{uid}")
    public User findbyuid(int uid);
}
