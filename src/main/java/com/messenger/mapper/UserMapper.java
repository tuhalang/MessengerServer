package com.messenger.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.messenger.logger.Logging;
import com.messenger.model.User;

public class UserMapper implements RowMapper<User>{

	private static Logger logger = Logging.getLogger();
	
	@Override
	public User mapRow(ResultSet result) {
		
		try {
			User user = new User();
			user.setUserId(result.getLong("userId"));
			user.setUsername(result.getString("username"));
			user.setPassword(result.getString("password"));
			user.setSex(result.getInt("sex"));
			user.setEnabled(result.getInt("enabled"));
			return user;
		}catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		
		return null;
	}

}
