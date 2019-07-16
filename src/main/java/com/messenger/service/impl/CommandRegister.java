package com.messenger.service.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.dao.UserDAO;
import com.messenger.dao.impl.UserDAOImpl;
import com.messenger.logger.Logging;
import com.messenger.model.Error;
import com.messenger.model.User;
import com.messenger.service.Command;

public class CommandRegister implements Command{
	private static Logger logger = Logging.getLogger();
	
	@Override
	public void excute(Socket socket, String content) {
		
		UserDAO userDAO = new UserDAOImpl();
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			User user = mapper.readValue(content, User.class);
			//validate user && check exist username
			if(isValid(user) && !userDAO.isExist(user.getUsername())) {
				user.setEnabled(1);
				userDAO.save(user);
				DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
				outToClient.writeBytes(mapper.writeValueAsString(user));
			}else {
				DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
				Error error = new Error();
				error.setCode("e1");
				//TODO set description for error
				outToClient.writeBytes("0"+mapper.writeValueAsString(error));
			}
		} catch (IOException e) {
			DataOutputStream outToClient;
			try {
				outToClient = new DataOutputStream(socket.getOutputStream());
				Error error = new Error();
				error.setCode("e");
				//TODO set description for error
				outToClient.writeBytes("0"+mapper.writeValueAsString(error));
			} catch (IOException e1) {
				logger.severe(e1.getMessage());
			}
			
			logger.severe(e.getMessage());
		}
		
	}
	
	private boolean isValid(User user) {
		
		if(user.getUsername() == null || user.getUsername().equalsIgnoreCase("")) {
			return false;
		}
		if(!user.getPassword().matches("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$")) {
			return false;
		}
		if(user.getSex() != 0 && user.getSex() != 1) {
			return false;
		}
		
		return true;
	}

}
