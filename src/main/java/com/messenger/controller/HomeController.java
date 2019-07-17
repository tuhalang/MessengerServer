package com.messenger.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.messenger.logger.Logging;


public class HomeController {

	
	public static Map<String, WorkerThread> users = new HashMap<String, WorkerThread>();
	
	static int numOfThread;
	
	static int serverPort;
	
	public static void main(String[] args) {
		
		Logger logger = Logging.getLogger();
		
		FileReader reader = null;
		Properties p = null;
		try {
			reader = new FileReader("config/application.properties");
			p = new Properties();  
		    p.load(reader);  
		    numOfThread = Integer.parseInt(p.getProperty("NUM_OF_THREAD"));
			serverPort = Integer.parseInt(p.getProperty("SERVER_PORT"));
		} catch (IOException e) {
			logger.severe(e.getMessage());
		} finally {
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.severe(e.getMessage());
				}
			}
		}
		
		//create NUM_OF_THREAD thread pool
		ExecutorService executorService = Executors.newFixedThreadPool(numOfThread);
		ServerSocket serverSocket = null;
		
		try {
			logger.info("Binding to port: " + serverPort);
			//create server socket with port = SERVER_PORT
			serverSocket = new ServerSocket(serverPort);
			logger.info("Server started: " + serverSocket);
			logger.info("Waiting for client ...");
			//wait client connect
			while(true) {
				try {
					Socket socket = serverSocket.accept();
					
					//get username
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
					String username = bufferedReader.readLine();
					//bufferedReader.close();
					
					logger.info("Client excepted " + username + " -- " + socket);
					
					//create WorkerThread to handle request for each client
					WorkerThread handler = new WorkerThread(socket);
					
					//manage list user connect
					users.put(username, handler);
					
					//execute thread
					executorService.execute(handler);
				}catch(IOException e) {
					logger.severe(e.getMessage());
				}
			}
		} catch (IOException e) {
			logger.severe(e.getMessage());
		} finally {
			if(serverSocket != null) {
				try {
					serverSocket.close();
					logger.info("server socket cloesed");
				} catch (IOException e) {
					logger.severe(e.getMessage());
				}
			}
		}
	}
	
	
	private static void checkOffline() {
		
	}
}
