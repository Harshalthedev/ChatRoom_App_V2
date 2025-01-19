package com.realtimechat.model;

public class Message {
    private String content;
    private String roomId;
    private String currentLoggedUser;

    public Message() {
    	
	}

	public Message(String content, String roomId, String currentLoggedUser) {
		super();
		this.content = content;
		this.roomId = roomId;
		this.currentLoggedUser = currentLoggedUser;
	}

	// Getters and Setters

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getCurrentLoggedUser() {
		return currentLoggedUser;
	}

	public void setCurrentLoggedUser(String currentLoggedUser) {
		this.currentLoggedUser = currentLoggedUser;
	}
    
}
