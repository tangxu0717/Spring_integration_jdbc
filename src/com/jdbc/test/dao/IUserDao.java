package com.jdbc.test.dao;

import com.jdbc.test.model.User;

import java.util.List;

/**
 * Created by tangxu on 2017/1/15.
 */
public interface IUserDao {
    public void add(User user,int gid);
    public void update(User user);
    public void delete(int id);
    public User load(int id);
    public List<User> list(String sql,Object[] args);
}
