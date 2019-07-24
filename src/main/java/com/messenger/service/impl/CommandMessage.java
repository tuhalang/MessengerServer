package com.messenger.service.impl;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
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
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		try {
			Message message= mapper.readValue(content, Message.class);
			messageDAO.save(message);
			message.setSeen(0);
			User sourceUser = userDAO.findById(message.getSourceId());
			User targetUser = userDAO.findById(message.getTargetId());
			
			//if user is not enabled then message will be not sent
			if(targetUser.getEnabled()==0) {
				try {
					osw = new OutputStreamWriter(socket.getOutputStream());
					bw = new BufferedWriter(osw);
					Error error = new Error();
					error.setCode("e3");
					//TODO set description for error
					bw.write("0"+mapper.writeValueAsString(error));
					bw.newLine();
					bw.flush();
				} catch (IOException e1) {
					logger.severe(e1.getMessage());
				}
				return;
			}
			
			
			logger.info(HomeController.users.size()+"");
			if(HomeController.users.containsKey(targetUser.getUsername())) {
				Socket client = HomeController.users.get(targetUser.getUsername()).getSocket();
				if(client.isConnected()) {
					osw = new OutputStreamWriter(client.getOutputStream());
					bw = new BufferedWriter(osw);
					bw.write("3"+mapper.writeValueAsString(message));
					bw.newLine();
					bw.flush();
					message.setSeen(1);
					messageDAO.update(message);
					logger.info("sent message from " + sourceUser.getUsername() + " to " + targetUser.getUsername());
				}
			}
			osw = new OutputStreamWriter(socket.getOutputStream());
			bw = new BufferedWriter(osw);
			bw.write("3"+mapper.writeValueAsString(message));
			bw.newLine();
			bw.flush();
			
		} catch (IOException e) {
			
			try {
				osw = new OutputStreamWriter(socket.getOutputStream());
				bw = new BufferedWriter(osw);
				Error error = new Error();
				error.setCode("e");
				//TODO set description for error
				bw.write("0"+mapper.writeValueAsString(error));
				bw.newLine();
				bw.flush();
			} catch (IOException e1) {
				logger.severe(e1.getMessage());
			}
			
			logger.severe(e.getMessage());
		} finally {
//			if(osw != null) {
//				try {
//					osw.close();
//				} catch (IOException e) {
//					logger.log(Level.SEVERE,e.getMessage());
//				}
//			}
//			if(bw != null) {
//				try {
//					bw.close();
//				} catch (IOException e) {
//					logger.log(Level.SEVERE,e.getMessage());
//				}
//			}
		}
	}

}
