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
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				bw.write("1"+mapper.writeValueAsString(user));
				bw.newLine();
				bw.flush();
			}
			else {
				Error error = new Error();
				error.setCode("e2");
				//TODO set description for error
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				bw.write("0"+mapper.writeValueAsString(error));
				bw.newLine();
				bw.flush();
			}
			
		} catch (IOException e) {
			try {
				Error error = new Error();
				error.setCode("e");
				//TODO set description for error
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				bw.write("0"+mapper.writeValueAsString(error));
				bw.newLine();
				bw.flush();
			} catch (IOException e1) {
				logger.severe(e1.getMessage());
			}
			
			logger.severe(e.getMessage());
		}
	}
	
}
