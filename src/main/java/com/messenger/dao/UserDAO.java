package com.messenger.dao;

import java.util.List;

import com.messenger.model.User;

public interface UserDAO {
	long save(User user);
	void update(User user);
	User findByUsername(String username);
	User findById(long id);
	List<User> findAll();
	List<User> find(int start, int limit);
}
