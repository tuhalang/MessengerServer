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
			if(HomeController.users.containsKey(targetUser.getUsername())) {
				Socket client = HomeController.users.get(targetUser.getUsername()).getSocket();
				DataOutputStream outToClient = new DataOutputStream(client.getOutputStream());
				outToClient.writeBytes(mapper.writeValueAsString(message));
				message.setSeen(1);
				messageDAO.save(message);
			}else {
				message.setSeen(0);
				messageDAO.save(message);
			}
			
		} catch (IOException e) {
			logger.severe(e.getMessage());
		}
	}

}
