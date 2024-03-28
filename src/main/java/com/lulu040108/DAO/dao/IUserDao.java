package com.lulu040108.DAO.dao;

import java.util.List;

public interface IUserDao {
    List<User> findAll();
    User findByCondition(User user);
}
