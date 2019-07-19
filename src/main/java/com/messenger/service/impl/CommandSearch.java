package com.messenger.service.impl;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.dao.UserDAO;
import com.messenger.dao.impl.UserDAOImpl;
import com.messenger.logger.Logging;
import com.messenger.model.Error;
import com.messenger.model.Key;
import com.messenger.model.User;
import com.messenger.service.Command;

public class CommandSearch implements Command{
	
	private static Logger logger = Logging.getLogger();

	@Override
	public void excute(Socket socket, String content) {
		ObjectMapper mapper = new ObjectMapper();
		UserDAO userDAO = new UserDAOImpl();
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		try {
			Key key = mapper.readValue(content, Key.class);
			List<User> users = userDAO.findLikeUsername(key.getKey());
			osw = new OutputStreamWriter(socket.getOutputStream());
			bw = new BufferedWriter(osw);
			bw.write("1"+mapper.writeValueAsString(users));
			bw.newLine();
			bw.flush();
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

}
