package com.messenger.dao;

import java.util.List;

import com.messenger.model.Message;

public interface MessageDAO {
	long save(Message message);
	void update(Message message);
	List<Message> getConversation(int sourceId, int targetId, int start, int limit);
	List<Message> getConversation(int sourceId, int targetId);
	List<Message> getNewMessage(int target);
}
