package com.messenger.dao;

import java.util.List;

import com.messenger.model.User;

public interface UserDAO {
	long save(User user);
	void update(User user);
	User findByUsernameAndPassword(String username, String password);
	User findById(long id);
	List<User> findAll();
	List<User> find(int start, int limit);
	List<User> findLikeUsername(String username);
	boolean isExist(String username);
}
