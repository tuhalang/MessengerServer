package com.messenger.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import com.messenger.logger.Logging;

public abstract class CommonDAO {
	
	public Connection getConnection() {
		Logger logger = Logging.getLogger();
		
		String driver = "";
		String url = "";
		String username = "";
		String password = "";
		
		FileReader reader = null;
		Properties p = null;
		try {
			reader = new FileReader("config/application.properties");
			p = new Properties();  
		    p.load(reader);  
		    driver = p.getProperty("DRIVER");
		    url = p.getProperty("URL");
		    username = p.getProperty("USERNAME");
		    password = p.getProperty("PASSWORD");
		} catch (IOException e) {
			logger.severe("CommonDAO: " + e.getMessage());
		} finally {
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.severe("CommonDAO: " + e.getMessage());
				}
			}
		}
		
		try {
			Class.forName(driver);  
			Connection con=DriverManager.getConnection(url,username,password);
			return con;
		} catch (SQLException e) {
			logger.severe("CommonDAO: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.severe("CommonDAO: " + e.getMessage());
		}  

		return null;
	}
}
