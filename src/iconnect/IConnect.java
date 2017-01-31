package iconnect;

import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import network.CaptureListener;
import network.CaptureReciever;
import network.CaptureSender;

public class IConnect extends Application implements Initializable, CaptureListener {

	@FXML private Text status;
	@FXML private Circle statusCircle;
	@FXML private TextField localip;
	@FXML private TextField password;
	@FXML private TextField remoteip;
	
	
	ImageView captureView;
	CaptureReciever captureReciever;
	
	@FXML protected void onConnectClick(ActionEvent e) {
		//new scene
		
		//TODO remove repitition
		Stage stage = new Stage();
		Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
               /// Platform.exit();
                //System.exit(0);
            	//do things for capture window closing
            	//if(captureReciever!=null)
            		//captureReceiver.stop();
            }
        });        
        
     //   if(captureReciever==null) 
        	captureReciever = new CaptureReciever(this, remoteip.getText());
        	captureView.setFitHeight(600);
        	captureView.setFitWidth(800);
        	captureView.setOnMouseClicked(new EventHandler<MouseEvent>(){
        		 
                 @Override
                 public void handle(MouseEvent event) {
                     //captureView.setImage(null);
                	 Event e = new Event(event.getX()/800, event.getY()/600, Event.MOUSE_CLICKED);
                	 captureReciever.sendMouseEventToRemotePC(e);
                	 System.out.println("Clicked at"+ event.getX()+","+event.getY());
                 }
             });
        	captureView.setOnMouseMoved(new EventHandler<MouseEvent>(){
        		 
                 @Override
                 public void handle(MouseEvent event) {

                	 System.out.println("Moved at"+ event.getX()+","+event.getY());
                	 Event e = new Event(event.getX()/800, event.getY()/600, Event.MOUSE_MOVED);
                	 captureReciever.sendMouseEventToRemotePC(e);
                	
                 }
             });
        root.getChildren().add(captureView);
        
        stage.show();
	}
	
	
	@FXML protected void onRefreshClick(ActionEvent e) {
		status.setText( "Refreshing..." );
		statusCircle.setFill(Color.YELLOW);
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			localip.setText(ip);
			status.setText("Your Id is refreshed");
			statusCircle.setFill(Color.BLUE);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String ip="";
		captureView = new ImageView();
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		localip.setText(ip);
		remoteip.setText(ip);
		
		
		//wait for clients to be recieved
		CaptureSender captureSender = new CaptureSender(Configuration.TCP_COM_PORT);
		
	}
	
	
	
	public void start(Stage stage) throws Exception {
		Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("res/iconnect.fxml"));	
		Scene scene = new Scene (root,  700, 600);
		stage.setTitle("Welcome to Iconnect");
		stage.setScene(scene);
		stage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
		System.exit(0);
		
	
	}

	@Override //this will be called as soon as a new frame is received
	public void onCaptureRecieve(BufferedImage snap) {
		final WritableImage frame = SwingFXUtils.toFXImage(snap, null);
		//System.out.println("capture Recievdd");
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				captureView.setImage(frame);
				
			}
			
		});
		
	}
	
}
