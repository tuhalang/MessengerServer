package com.messenger.service.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.dao.UserDAO;
import com.messenger.dao.impl.UserDAOImpl;
import com.messenger.logger.Logging;
import com.messenger.model.User;
import com.messenger.service.Command;
import com.messenger.model.Error;

public class CommandLogin implements Command{

	private static Logger logger = Logging.getLogger();
	
	@Override
	public void excute(Socket socket, String content) {
		
		ObjectMapper mapper = new ObjectMapper();
		UserDAO userDAO = new UserDAOImpl();
		try {
			User user = mapper.readValue(content, User.class);
			user = userDAO.findByUsernameAndPassword(user.getUsername(), user.getPassword());
			if(user != null) {
				DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
				outToClient.writeBytes("1"+mapper.writeValueAsString(user));
			}
			else {
				DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
				Error error = new Error();
				error.setCode("e2");
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
	
}
