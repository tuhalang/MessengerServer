package com.messenger.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.messenger.dao.UserDAO;
import com.messenger.dao.impl.UserDAOImpl;
import com.messenger.model.User;

public class ClientTest {
	public static void main(String[] args) {
		UserDAO userDAO = new UserDAOImpl();
		List<User> users = userDAO.findLikeUsername("tuha");
		System.out.println(users);

	}
}
