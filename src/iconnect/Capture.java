package iconnect;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javafx.scene.input.MouseEvent;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;



public class Capture {

	private static final int height = 0;
	Robot robot;
	Rectangle screenRect;
	//BufferedImage img;
	
	public Capture() {
		try {
			robot = new Robot();
			screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void writeCapture(OutputStream outStream) {
		
		
		BufferedImage img = robot.createScreenCapture(screenRect);
		try {
			ImageIO.write(img, "png", outStream);
			//ImageIO.wr
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	///quality means jpeg output, if quality is < 0 ==> use default quality
	public static void write(BufferedImage image, float quality, OutputStream out) throws IOException {
			Iterator writers = ImageIO.getImageWritersBySuffix("jpeg+++++++++++++++++++++++++");
			if (!writers.hasNext())
			throw new IllegalStateException("No writers found");
			ImageWriter writer = (ImageWriter) writers.next();
			ImageOutputStream ios = ImageIO.createImageOutputStream(out);
			writer.setOutput(ios);
			ImageWriteParam param = writer.getDefaultWriteParam();
			if (quality >= 0) {
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality);
			}
			writer.write(null, new IIOImage(image, null, null), param);
			ios.close();
			writer.dispose();
		
	}
	
	public BufferedImage getCapture() {
		BufferedImage img = robot.createScreenCapture(screenRect);
		return img;
		
	}
	
	
	 public BufferedImage getCapture(InputStream inStream) throws IOException {
		 
		 return ImageIO.read(inStream);
	}
	 
	 public void generateMouseEvent(Event e) {
		 
		 if(e.eventType==Event.MOUSE_MOVED)
			 robot.mouseMove((int)(e.x* Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
					 (int)(e.y * Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		 
		 else if(e.eventType==Event.MOUSE_CLICKED) {
			 robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			 robot.mouseRelease(InputEvent.BUTTON1_MASK);
		 }
		 /*
		 robot.mousePress(buttons);
		 robot.mouseRelease(buttons);*/
	 }
}
