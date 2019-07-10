package com.messenger.dao.impl;

import java.util.Date;
import java.util.List;

import com.messenger.dao.MessageDAO;
import com.messenger.mapper.MessageMapper;
import com.messenger.model.Message;

public class MessageDAOImpl extends CommonDAOImpl<Message> implements MessageDAO{

	@Override
	public long save(Message message) {
		String sql = "insert into message(sourceId, targetId, content, image, seen, date)";
		return insert(sql, message.getSourceId(), message.getTargetId(), message.getContent(),
				message.getImage(), message.getSeen(), new Date());
	}

	@Override
	public void update(Message message) {
		String sql = "update message set sourceId=?, targetId=?, content=?, image=?, seen=? where messageId=?";
		update(sql, message.getSourceId(), message.getTargetId(), message.getContent(),
				message.getImage(), message.getSeen(), message.getMessageId());
		
	}

	@Override
	public List<Message> getConversation(int sourceId, int targetId, int start, int limit) {
		String sql = "select * from message where (sourceId=? and targetId=?) "
				+ "or (sourceId=? and targetId=?) limit ?,? order by date desc";
		return query(sql, new MessageMapper(), sourceId, targetId, targetId, sourceId, start, limit);
	}

	@Override
	public List<Message> getConversation(int sourceId, int targetId) {
		String sql = "select * from message where (sourceId=? and targetId=?) "
				+ "or (sourceId=? and targetId=?) order by date desc";
		return query(sql, new MessageMapper(), sourceId, targetId, targetId, sourceId);
	}

	@Override
	public List<Message> getNewMessage(int targetId) {
		String sql = "select * from message where seen = ? and targetId = ?";
		return query(sql, new MessageMapper(), 0, targetId);
	}

	

}
