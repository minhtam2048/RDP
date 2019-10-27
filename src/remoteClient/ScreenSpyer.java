package remoteClient;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

class ScreenSpyer extends Thread {
	Socket clientSocket = null;
	Robot robot = null;
	Rectangle rectangle = null;
	boolean continueLoop = true;
	
	public ScreenSpyer(Socket clientSocket, Robot robot, Rectangle rect) {
		this.clientSocket = clientSocket;
		this.robot = robot;
		rectangle = rect;
		start();
	}
	
	public void run() {
		ObjectOutputStream oos = null;
		
		try {
			
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			// send screen size to server
			oos.writeObject(rectangle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(continueLoop) {
			
			BufferedImage image = robot.createScreenCapture(rectangle);
			ImageIcon imageIcon = new ImageIcon(image);
			
			//send captured screen
			try {
				System.out.println("before sending image");
				oos.writeObject(imageIcon);
				oos.reset();
				System.out.println("new screenshot sent");
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(30);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
