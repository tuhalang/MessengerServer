package com.messenger.service.impl;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		try {
			User user = mapper.readValue(content, User.class);
			//validate user && check exist username
			if(isValid(user) && !userDAO.isExist(user.getUsername())) {
				user.setEnabled(1);
				userDAO.save(user);
				osw = new OutputStreamWriter(socket.getOutputStream());
				bw = new BufferedWriter(osw);
				bw.write(mapper.writeValueAsString(user));
				bw.newLine();
				bw.flush();

			}else {
				Error error = new Error();
				error.setCode("e1");
				//TODO set description for error
				osw = new OutputStreamWriter(socket.getOutputStream());
				bw = new BufferedWriter(osw);
				bw.write("0"+mapper.writeValueAsString(error));
				bw.newLine();
				bw.flush();
			}
		} catch (IOException e) {
			try {
				Error error = new Error();
				error.setCode("e");
				//TODO set description for error
				osw = new OutputStreamWriter(socket.getOutputStream());
				bw = new BufferedWriter(osw);
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
//					logger.severe(e.getMessage());
//				}
//			}
//			if(bw != null) {
//				try {
//					bw.close();
//				} catch (IOException e) {
//					logger.severe(e.getMessage());
//				}
//			}
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
