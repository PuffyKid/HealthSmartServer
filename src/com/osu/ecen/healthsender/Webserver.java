package com.osu.ecen.healthsender;

import java.io.IOException;
import java.net.ServerSocket;



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
