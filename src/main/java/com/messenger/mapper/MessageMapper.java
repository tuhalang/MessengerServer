package com.messenger.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.messenger.logger.Logging;
import com.messenger.model.Message;

public class MessageMapper implements RowMapper<Message>{

	private static Logger logger = Logging.getLogger();
	
	@Override
	public Message mapRow(ResultSet result) {
		Message message = null;
		try {
			message = new Message();
			message.setSourceId(result.getLong("sourceId"));
			message.setMessageId(result.getLong("messageId"));
			message.setTargetId(result.getLong("targerId"));
			message.setContent(result.getString("content"));
			message.setImage(result.getString("image"));
			message.setSeen(result.getInt("seen"));
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		return null;
	}

}
