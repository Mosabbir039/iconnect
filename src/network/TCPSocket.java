package network;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import javax.imageio.ImageIO;

/**
 * This class provides helper methods for socket programming
 * Example. Codes for receive and send string, objects , and file
 * Just provide the source socket in the constructor and then use this class'
 * methods to send/ recieve data via java socket
 * @author Mosabbir
 */
public class TCPSocket {
	
	Socket socket;
	
	
	BufferedReader br;			// to receive characters and string  
	PrintWriter pr;				// to send string
	ObjectOutputStream objOut;	// to send object via this socket
	ObjectInputStream objIn;	// to receive object via this socket
	
	public TCPSocket(Socket socket) {
		this.socket = socket;
		try {
			//initiate all the streams
			br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			pr = new PrintWriter(socket.getOutputStream());
			
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objIn = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public TCPSocket(InetAddress host, int port) throws IOException {
		this( new Socket(host, port));
	}
	public void send(String msg) {
		
		pr.println(msg);
		pr.flush();
	}
	
	public void send(Object obj) throws IOException {
		objOut.writeObject(obj);
		objOut.flush();
	}
	
	public void send(File file) throws Exception{
		//byte [] byteArray = new byte[(int) file.length()];
	
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		OutputStream os = socket.getOutputStream();
		byte [] byteArray = new byte[1024];
		int sum = 0;
		do {
		//bis.read(byteArray, 0, byteArray.length);
		sum +=bis.read(byteArray);
		
		//os.write(byteArray, 0, byteArray.length);
		os.write(byteArray);
		
		}while(bis.available()>0);
		os.flush();
		bis.close();
		
	
		System.out.println("File "+ file.getName()+ " sent ." + " Total: "+ sum +" bytes were written");
	}
	
	/**
	 * 
	 * @param filePath where the file will be save */
	 
	public void receiveFile(String filePath) throws  Exception{
		
		byte[] bytearray = new byte[1024];//8192
	    InputStream is = socket.getInputStream();
	    //System.out.println("Available:" + is.available());
	    FileOutputStream fos = new FileOutputStream(filePath);
	    BufferedOutputStream bos = new BufferedOutputStream(fos);
	    
	    int sum = 0;
	    do {
	    	//int bytesRead = is.read(bytearray, 0, bytearray.length);
	    	int bytesRead = is.read(bytearray);
	    	 sum += bytesRead;
	    	
	    	System.out.println("bytesRead: " +bytesRead);
	    	bos.write(bytearray, 0, bytesRead);
	    	//bos.write(bytearray);
	    	System.out.println("Available:" + is.available());
	    }while(is.available()>0);
	    
	    bos.flush();
	    bos.close();
	    
	    System.out.println("File "+ filePath +" downloaded successfully.\n" +
	    		"Check inside folder"+"Total: "+sum+" bytes were read");

	}
	
	public String  receive() throws IOException {
		String str = null;
		while((str = br.readLine())==null);
		return str;
	}
	
	public Object readObject() throws Exception {
		Object obj = null;
		while((obj= objIn.readObject())==null);
		return obj;
	}
	
	public void send(BufferedImage img) throws IOException {
		
	
	     ImageIO.write(img, "png", getOutputStream());
	        
	}
	
	public BufferedImage readBufferedImage() throws IOException {
		BufferedImage img = null;
		while((img = ImageIO.read(getInputStream()))==null);
        return img;
	
	}
	
	public OutputStream getOutputStream() throws IOException {
	
			return socket.getOutputStream();
	
	}
	
	public InputStream getInputStream() throws IOException {
		
		return socket.getInputStream();
	}

	public void close() throws IOException {
		socket.close();
		
	}
	
	public Socket getSocket() {
		return socket;
	}

	public String receiveString(String waitingMsg) throws SocketException {
		System.out.println(waitingMsg);
		String msg = null;
		try {
			while((msg = br.readLine())==null);
			System.out.println("Received: " + msg);
		} catch (IOException e) {
			
			if(e instanceof SocketException) {
				//removeOnlineUser(user);
				throw new SocketException("Socket Disconnected while waiting..");
				
			}
			else {
				e.printStackTrace();
				System.out.println("Error reading from socket while waiting for string..");
			}
		}//catch
		
		return msg;
	}

	 public void holdOutputStream() throws Exception {
		 synchronized(socket.getOutputStream()) {
			 socket.getOutputStream().wait();
		 }
		
	}
	 
	public void releaseOutputStream() throws Exception {
		synchronized(socket.getOutputStream()) {
			 socket.getOutputStream().notifyAll();
		 }
	}
}
