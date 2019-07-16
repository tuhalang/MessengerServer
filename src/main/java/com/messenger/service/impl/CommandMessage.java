package com.messenger.service.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.controller.HomeController;
import com.messenger.dao.MessageDAO;
import com.messenger.dao.UserDAO;
import com.messenger.dao.impl.MessageDAOImpl;
import com.messenger.dao.impl.UserDAOImpl;
import com.messenger.logger.Logging;
import com.messenger.model.Error;
import com.messenger.model.Message;
import com.messenger.model.User;
import com.messenger.service.Command;

public class CommandMessage implements Command{

	
	private static Logger logger = Logging.getLogger();
	
	@Override
	public void excute(Socket socket, String content) {
		MessageDAO messageDAO = new MessageDAOImpl();
		UserDAO userDAO = new UserDAOImpl();
		ObjectMapper mapper = new ObjectMapper();
		try {
			Message message= mapper.readValue(content, Message.class);
			messageDAO.save(message);
			User targetUser = userDAO.findById(message.getTargetId());
			
			//if user is not enabled then message will be not sent
			if(targetUser.getEnabled()==0) {
				DataOutputStream outToClient;
				try {
					outToClient = new DataOutputStream(socket.getOutputStream());
					Error error = new Error();
					error.setCode("e3");
					//TODO set description for error
					outToClient.writeBytes("0"+mapper.writeValueAsString(error));
				} catch (IOException e1) {
					logger.severe(e1.getMessage());
				}
				return;
			}
			
			if(HomeController.users.containsKey(targetUser.getUsername())) {
				Socket client = HomeController.users.get(targetUser.getUsername()).getSocket();
				DataOutputStream outToClient = new DataOutputStream(client.getOutputStream());
				outToClient.writeBytes("1"+mapper.writeValueAsString(message));
				message.setSeen(1);
				messageDAO.save(message);
			}else {
				message.setSeen(0);
				messageDAO.save(message);
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
