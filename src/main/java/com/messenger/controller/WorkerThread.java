package com.messenger.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Logger;

import com.messenger.logger.Logging;
import com.messenger.service.MessageService;
import com.messenger.service.impl.MessageServiceImpl;

public class WorkerThread extends Thread {

	private Socket socket;

	public WorkerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		Logger logger = Logging.getLogger();
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		LinkedList<String> messages = new LinkedList<String>();
		MessageService messageService = new MessageServiceImpl();
		try {
			while (true) {
				inputStreamReader = new InputStreamReader(this.socket.getInputStream());
				bufferedReader = new BufferedReader(inputStreamReader);
				String content = bufferedReader.readLine();
				if (content != null) {
					messages.push(content);
				}
				while(messages.size() > 0) {
					logger.info("message from " + socket + " is : " + messages.peek());
					messageService.handle(socket, messages.pop());
				}
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					logger.severe(e.getMessage());
//				}
			}
		} catch (IOException e) {
			logger.severe(e.getMessage());
		} finally {
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.severe(e.getMessage());
				}
			}
			
			if(inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					logger.severe(e.getMessage());
				}
			}
			
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					logger.severe(e.getMessage());
				}
			}
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	
}
