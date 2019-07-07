package com.messenger.service;

import java.net.Socket;

public interface Command {
	void excute(Socket socket);
}
