package com.osu.ecen.healthsender;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;



public class Webserver implements Runnable {

	/**
	 * @param args
	 * use http://localhost:<port>/<person> in browser.
	 */
	public static void main(String[] args) {
		new Thread(new Webserver()).start();
		System.out.println("Starting server");
	}
	
	@Override
	public void run() {
		try(final DatagramSocket socket = new DatagramSocket()){
			  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			  String ip = socket.getLocalAddress().getHostAddress();
			  System.out.println("Starting server on: "+ ip);
		} catch (UnknownHostException | SocketException e) {
			System.out.println("Could not start server");
			e.printStackTrace();
		}
		try (ServerSocket ss=new ServerSocket(13337);		){

			Runtime.getRuntime().addShutdownHook(new Thread() {
			    public void run() {
			    	try {
						ss.close();
					} catch (IOException e) {
						System.err.println("Could not close ServerSocket");
					}
			    }
			});
			
			//sits in a loop waiting for clients to connect
			while (true) {
				Thread t =new Thread(new HealthSender(ss.accept()));
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		
	}

}
