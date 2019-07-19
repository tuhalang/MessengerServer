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

	private int flag = 1;
	
	private Socket socket;

	public WorkerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		Logger logger = Logging.getLogger();
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		LinkedList<String> messages = new LinkedList<>();
		MessageService messageService = new MessageServiceImpl();
		try {
			while (flag == 1) {
				inputStreamReader = new InputStreamReader(this.socket.getInputStream());
				bufferedReader = new BufferedReader(inputStreamReader);
				String content = bufferedReader.readLine();
				if (content != null) {
					messages.push(content);
				}
				while(!messages.isEmpty()) {
					logger.info("message from " + socket + " is : " + messages.peek());
					messageService.handle(socket, messages.pop());
				}
				
			}
		} catch (IOException e) {
			logger.severe(e.getLocalizedMessage());
		} finally {
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.severe(e.getLocalizedMessage());
				}
			}
			
			if(inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					logger.severe(e.getLocalizedMessage());
				}
			}
			
			if(socket != null) {
				try {
					socket.close();
					
				} catch (IOException e) {
					logger.severe(e.getLocalizedMessage());
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

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}
