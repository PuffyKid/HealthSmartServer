package com.osu.ecen.healthsender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonalHealthTracker {

	private static final ArrayList<String> fieldOptions = new ArrayList<String>(Arrays.asList("heartrate","stepcount","skinconductivity","temperature","spo2"));
	private static final ArrayList<String> fieldTimeFrame = new ArrayList<String>(Arrays.asList("current","day","week","month"));
	private static final int[] lut= {1,18,7,30};
	public PersonalHealthTracker() {
		// TODO Auto-generated constructor stub
	}
	public static String getStuff(String s, String t) {
		String toReturn ="";
		for(int i = lut[fieldTimeFrame.indexOf(t)];i>0;i--) {
			toReturn=toReturn+getRightValue(s)+",";
		}
		toReturn= toReturn.substring(0, toReturn.length()-2);
		if(t.equals("day")) {
			toReturn=toReturn+",-,-,-,-,-,-";
		}
		return toReturn;
	}
	
	private static String getRightValue(String t) {
		switch(t) {
			case "heartrate":
				return (Math.random()*10+130)+"";
			case "stepcount":
				return (Math.random()*20000)+"";
			case "temperature":
				return (Math.random()*1+98.6)+"";
			case "skinconductivity":
				return (Math.random()*100)+"";
			case "spo2":
				return (Math.random()*100)+"";
			default:
				return "-";
		
		}
	}
	
	public static boolean isValidRequest(String s, String[] afterSplit) {
		
		if(afterSplit.length != 3) {
			System.err.println("Could not split: "+s);
			return false;
		}
		
		String nameRegx = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$" ;
		Pattern pattern = Pattern.compile(nameRegx,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(afterSplit[0]);
		if(!matcher.find()) {
			System.err.println("Could not parse name: "+s);
			return false;
		}
		
		if(!fieldOptions.contains(afterSplit[1])) {
			System.err.println("Could not parse option field: "+s);
			return false;
		}
		if(!fieldTimeFrame.contains(afterSplit[2])) {
			System.err.println("Could not parse time field: "+s);
			return false;
		}
		return true;
	}
	public static void main(String[] args) {
		System.out.println("Starting Tester");
		try {
			Socket socket = new Socket("10.192.149.192",13337);
			BufferedReader in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream out= new PrintStream(socket.getOutputStream());
			Scanner scan = new Scanner(System.in);
			
			while(scan.hasNextLine()) {
				String s = scan.nextLine();
				out.println(s);
				out.flush();
				System.out.println(s);
				s= in.readLine();
				System.out.println(s);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
