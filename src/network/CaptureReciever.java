package network;

import iconnect.Capture;
import iconnect.Configuration;
import iconnect.Event;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CaptureReciever implements Runnable{

	CaptureListener captureListener;
	String hostIp;

	TCPSocket socket;
	
	public CaptureReciever(CaptureListener cl, String hostIp) {
		captureListener = cl;
		this.hostIp = hostIp;
		new Thread(this).start();
	}
	
	
	
	public void sendMouseEventToRemotePC(final Event e) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					socket.send(e.toString());
				} catch (Exception e) {
					System.out.println("sending event error");
					e.printStackTrace();
				}
			}
			
		}).start();
	}
	
	@Override
	public void run() {
		
		//first authenticate using tcp
		try {
			socket = new TCPSocket(InetAddress.getByName(hostIp), Configuration.TCP_COM_PORT);
			
			System.out.println("reciving image...");
		
			//create socket which will recieve image
			//UDPSocket captureSocket = new UDPSocket(Configuration.CAPTURE_RECIEVER_PORT);
			
			Capture c = new Capture();
			while(true) {
				BufferedImage capture = c.getCapture(socket.getInputStream());//socket.readBufferedImage();//= captureSocket.receiveImage();	//recive capture Image
				
				if(capture!=null)
				captureListener.onCaptureRecieve(capture);//update ui
				//System.out.println("iamge recieved...");
				
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	
}
