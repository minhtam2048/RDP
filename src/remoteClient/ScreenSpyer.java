package remoteClient;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;


class ScreenSpyer extends Thread {
	Socket clientSocket = null;
	Robot robot = null;
	Rectangle rectangle = null;
	boolean continueLoop = true;
	OutputStream oos = null;
	
	public ScreenSpyer(Socket clientSocket, Robot robot, Rectangle rect) {
		this.clientSocket = clientSocket;
		this.robot = robot;
		rectangle = rect;
		start();
	}
	
	public void run() {
		
		while(continueLoop) {
			BufferedImage image = robot.createScreenCapture(rectangle);
			
			//send captured screen
			try {
				
			    OutputStream os = clientSocket.getOutputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] size = ByteBuffer.allocate(4).putInt(baos.size()).array();
				os.write(size);
				ImageIO.write(image, "jpeg", baos);
				os.write(baos.toByteArray());
				os.flush();
				baos.flush();
				
			} catch(IOException e) {
				e.printStackTrace();
				e.printStackTrace();
				continueLoop = false;
			}
			
			try {
				Thread.sleep(30);
			} catch(InterruptedException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				continueLoop = false;
			}
		}
	}
}
