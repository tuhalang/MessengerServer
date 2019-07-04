package com.messenger.controller;

import java.net.Socket;

public class WorkerThread extends Thread{

	private Socket socket;
	
	public WorkerThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
}
