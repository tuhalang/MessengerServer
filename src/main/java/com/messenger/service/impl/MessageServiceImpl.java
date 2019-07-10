package com.messenger.service.impl;

import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Logger;

import com.messenger.logger.Logging;
import com.messenger.service.Command;
import com.messenger.service.MessageService;

public class MessageServiceImpl implements MessageService {

	@Override
	public void handle(Socket socket, String messaage) {

		Logger logger = Logging.getLogger();
		FileReader reader = null;
		Properties p = null;
		int loginCode = 1;
		int registerCode = 2;
		int messageCode = 3;
		int searchCode = 4;

		try {
			reader = new FileReader("config/application.properties");
			p = new Properties();
			p.load(reader);
			loginCode = Integer.parseInt(p.getProperty("LOGIN_CODE"));
			registerCode = Integer.parseInt(p.getProperty("REGISTER_CODE"));
			messageCode = Integer.parseInt(p.getProperty("MESSAGE_CODE"));
			searchCode = Integer.parseInt(p.getProperty("SEARCH_CODE"));
			
		} catch (IOException e) {
			logger.severe("Homecontroller: " + e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.severe("Homecontroller: " + e.getMessage());
				}
			}
		}

		int code = Integer.parseInt(messaage.substring(0, 1));
		
		Command command = null;
		
		if(code == loginCode) {
			command = new CommandLogin();
		}else if (code == registerCode) {
			command = new CommandRegister();
		}else if (code == messageCode) {
			command = new CommandMessage();
		}else if (code == searchCode) {
			command = new CommandSearch();
		}
		
		if(command != null) {
			command.excute(socket, messaage.substring(1));
		}else {
			logger.severe("Can not create Command");
		}
		
	}
}
