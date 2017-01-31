package network;

import iconnect.Capture;
import iconnect.Event;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class CaptureSender implements Runnable{
	
	ServerSocket ss;
	TCPSocket clientSocket;
	//UDPSocket captureSocket;
	Capture capture;
	InetAddress recieverAddress;
	int tcpPort;
	
	public CaptureSender(int tcpPort) {
		capture = new Capture();
		this.tcpPort = tcpPort;
		System.out.println("Capture Sender initiaded");
		new Thread(this).start();
		
		
		
	}

	public void acceptClient() {
		
		try {
			Socket sc = ss.accept();
			clientSocket =new TCPSocket(sc);
			recieverAddress = sc.getInetAddress();	//save the address of reciever
			
			System.out.println("Remote Controller connected");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



	@Override
	public void run() {
	//	captureSocket = new UDPSocket(Configuration.CAPTURE_SENDER_PORT);
		try {
			ss = new ServerSocket(tcpPort);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		System.out.println("Waiting for remote controller");
		acceptClient();
		
		//new thread to receive keyevents from remoteController

		final Capture capture = new Capture();
		new Thread(new Runnable() {

			
			@Override
			public void run() {
				
				while(true) {
					
					try {
						//TEMPORARY ONLY PROCESSING MOUSEEVENTS
						String event = clientSocket.receive();
						System.out.println("REciveid event: " + event);
						capture.generateMouseEvent(Event.parse(event));
						System.out.println("Key Pressed Recived");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}).start();
		
		//send capture
		try {
		while(true) {

			BufferedImage snap = capture.getCapture();
			capture.writeCapture(clientSocket.getOutputStream());//.getScreenSnap();
			//capture.write(snap, .5f, clientSocket.getOutputStream());
			//clientSocket.send(snap);
			//System.out.println("send");
		}
		
		}catch(Exception e) {
		}
	
	}
	
}
