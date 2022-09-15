package com.tanhua.server.interceptor;

import com.tanhua.model.domain.User;

/**
 * @author sxs
 * @create 2022-09-10 17:10
 */
public class UserHolder {
    private static ThreadLocal<User> t=new ThreadLocal<>();
    public static void set(User user){
        t.set(user);
    }
    public static User get(){
        User user = t.get();
        return user;
    }
    public static Long getId(){
        return t.get().getId();
    }
    public static String getPhone(){
        return t.get().getMobile();
    }
    public static void remove(){
        t.remove();
    }

}
