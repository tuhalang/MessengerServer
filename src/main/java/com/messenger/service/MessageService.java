package com.messenger.service;

import java.net.Socket;

public interface MessageService {
	void handle(Socket socket, String message);
}
