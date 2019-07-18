package com.messenger.service;

import java.net.Socket;

public interface Command {
	/* error code:
	 *  e : other error
	 * 	e1: user invalid
	 *  e2: username or password invalid
	 *  e3: user is not enabled
	 * */
	void excute(Socket socket, String content);
}
