package com.messenger.dao.impl;

import java.sql.Connection;
import java.util.List;

import com.messenger.dao.UserDAO;
import com.messenger.mapper.UserMapper;
import com.messenger.model.User;

public class UserDAOImpl extends CommonDAOImpl<User> implements UserDAO{

	@Override
	public long save(User user) {
		String sql = "insert into user(username, password, sex, enabled) value(?,?,?,?)";
		return insert(sql, user.getUsername(), user.getPassword(), user.getSex(), user.getEnabled());
	}

	@Override
	public void update(User user) {
		String sql = "update uset set username=?, password=?, sex=?, enabled=? where userId=?";
		update(sql, user.getUsername(), user.getPassword(), user.getSex(), user.getEnabled(), user.getUserId());
		
	}

	@Override
	public User findByUsername(String username) {
		String sql = "select * from user where username=?";
		List<User> users = query(sql,new UserMapper(), username);
		if(users.isEmpty())
			return null;
		return users.get(0);
	}

	@Override
	public User findById(long id) {
		String sql = "select * from user where userId=?";
		List<User> users = query(sql,new UserMapper(), id);
		if(users.isEmpty())
			return null;
		return users.get(0);
	}

	@Override
	public List<User> findAll() {
		String sql = "select * from user";
		List<User> users = query(sql,new UserMapper());
		return users;
	}

	@Override
	public List<User> find(int start, int limit) {
		String sql = "select * from user limit ?,?";
		List<User> users = query(sql,new UserMapper(), start, limit);
		return users;
	}

	@Override
	public boolean isExist(String username) {
		String sql = "select count(1) from user where username=? limit 1";
		long count = count(sql, username);
		if(count > 0)
			return true;
		return false;
	}

}
