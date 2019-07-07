package com.messenger.model;

import java.io.Serializable;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 6342463331900978047L;
	private long messageId;
	private long sourceId;
	private long targetId;
	private String content;
	private String image;
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public long getSourceId() {
		return sourceId;
	}
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	public long getTargetId() {
		return targetId;
	}
	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "{\"messageId\":"+messageId+","
				+ "\"sourceId\":"+sourceId+","
				+ "\"targetId\":"+targetId+","
				+ "\"content\":\""+content+"\","
				+ "\"image\":\""+image+"\""
				+ "}";
	}
}
