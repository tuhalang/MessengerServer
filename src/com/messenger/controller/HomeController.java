package com.messenger.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.messenger.logger.Logging;


public class HomeController {

	
	public static final int NUM_OF_THREAD = 10;
	public static final int SERVER_PORT = 3380;
	
	
	public static void main(String[] args) {
		
		Logger logger = Logging.getLogger();
		
		//create NUM_OF_THREAD thread pool
		ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREAD);
		ServerSocket serverSocket = null;
		try {
			logger.info("Homecontroller: Binding to port: " + SERVER_PORT);
			//create server socket with port = SERVER_PORT
			serverSocket = new ServerSocket(SERVER_PORT);
			logger.info("HomeController: Server started: " + serverSocket);
			logger.info("HomeController: Waiting for client ...");
			//wait client connect
			while(true) {
				try {
					Socket socket = serverSocket.accept();
					logger.info("HomeController: Client excepted " + socket);
					
					//create WorkerThread to handle request for each client
					WorkerThread handler = new WorkerThread(socket);
					
					//execute thread
					executorService.execute(handler);
				}catch(IOException e) {
					logger.severe("Homecontroller: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			logger.severe("Homecontroller: " + e.getMessage());
		} finally {
			if(serverSocket != null) {
				try {
					serverSocket.close();
					logger.info("Homecontroller: server socket cloesed");
				} catch (IOException e) {
					logger.severe("Homecontroller: " + e.getMessage());
				}
			}
		}
	}
}
