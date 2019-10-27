package remoteServer;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ClientScreenReceiver extends Thread {
	
	private ObjectInputStream clientOIS = null;
	private JPanel clientPanel = null;
	private boolean continueLoop = true;
	
	public ClientScreenReceiver(ObjectInputStream ois, JPanel cPanel) {
		clientOIS = ois;
		clientPanel = cPanel;
		start();
	}
	
	@Override
	public void run() {
		
		try {
			while(continueLoop) {
				ImageIcon imageIcon = (ImageIcon) clientOIS.readObject();
				System.out.println("New Image received");
				Image image = imageIcon.getImage();
				image = image.getScaledInstance(clientPanel.getWidth(), clientPanel.getHeight(), Image.SCALE_FAST);
				
				Graphics graphics = clientPanel.getGraphics();
				graphics.drawImage(image, 0, 0, clientPanel.getWidth(), clientPanel.getHeight(), clientPanel);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	
}
