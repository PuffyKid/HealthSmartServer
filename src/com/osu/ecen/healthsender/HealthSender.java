package com.osu.ecen.healthsender;


import java.io.*;
import java.net.*;


public class HealthSender implements Runnable {

	/**
	 * @param args
	 */
	private Socket socket;
	public HealthSender(Socket s){
		socket=s;	
	}

	@Override
	public void run() {
		// Sets up a socket connection
		try(
				BufferedReader in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintStream out= new PrintStream(socket.getOutputStream())
		){
			out.print("HTTP/1.1 200 OK\r\n");
			out.print("Content-type: text/html\r\n\r\n");
			out.flush();
			
			out.print("Test");
			out.flush();
			
		}	
		catch (IOException e) {
				System.out.print("HTTP/1.1 500 Internal Server Error\r\n\r\n");				
		} 
		
	}
	
}
