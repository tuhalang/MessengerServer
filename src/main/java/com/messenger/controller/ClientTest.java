package com.messenger.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTest {
	public static void main(String[] args) {
		Runnable run2 = new Runnable() {
			@Override
			public void run() {
				Socket socket = null;
				BufferedWriter bw = null;
				try {
					socket = new Socket("127.0.0.1", 3308);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				while (true) {
					try {
						for (int i = 0; i < 10; i++) {
							bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
							bw.write("test");
							bw.newLine();
							bw.flush();
							
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread thread2 = new Thread(run2);
		thread2.start();

	}
}
