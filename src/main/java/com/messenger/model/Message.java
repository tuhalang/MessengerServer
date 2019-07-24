package com.messenger.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 6342463331900978047L;
	private long messageId;
	private long sourceId;
	private long targetId;
	private String icon;
	private String content;
	private String image;
	private int seen;
	private Date date;

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

	public int getSeen() {
		return seen;
	}
	public void setSeen(int seen) {
		this.seen = seen;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
