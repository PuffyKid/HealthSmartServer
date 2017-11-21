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
		System.out.println("Connection from: "+s.getRemoteSocketAddress());
	}

	@Override
	public void run() {
		// Sets up a socket connection
		try(
				BufferedReader in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintStream out= new PrintStream(socket.getOutputStream())
		){
		String line;
		while(socket.isConnected()&&!socket.isClosed()) {
			line = in.readLine();
			
			if (line != null) {
				String[] splitInput = line.split(",");
				if(!PersonalHealthTracker.isValidRequest(line,splitInput)) {
					System.out.println("Bad request");
					continue;
				}
				System.out.println("Recv: "+line);
			
				out.print(PersonalHealthTracker.getStuff(splitInput[1], splitInput[2])+"\n");
				out.flush();
			}
		}
			
		}	
		catch (IOException e) {
				System.out.print("HTTP/1.1 500 Internal Server Error\r\n\r\n");				
		} 
		
	}
	
}
