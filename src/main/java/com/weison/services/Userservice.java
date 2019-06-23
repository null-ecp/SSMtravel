package com.weison.services;

import com.weison.domain.User;

public interface Userservice {

    public boolean checkusername(String username);

    public boolean registeruser(User user);

    public boolean activeuser(String code);

    public User logincheck(String username, String password);
}
