package remoteServer;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.ByteBuffer;

class ClientCreation extends Thread {
	
	private Socket clientSocket = null;
	private JDesktopPane dpane = null;
	
	private Rectangle clientScreenDim = null;

	private JInternalFrame interFrame = new JInternalFrame("client", true, true, true);
	private JPanel clientPanel = new JPanel();
	
	
   public ClientCreation(Socket clientSocket,JDesktopPane dpane) {
        this.clientSocket = clientSocket;
        this.dpane = dpane;
        start();
	}
   
   
   public void drawGUI() throws PropertyVetoException {
	   interFrame.setLayout(new BorderLayout());
	   interFrame.getContentPane().add(clientPanel, BorderLayout.CENTER);
	   interFrame.setSize(100,100);
	   dpane.add(interFrame);
	   
	  
	   interFrame.setMaximum(true);
	  
	   
	   clientPanel.setFocusable(true);
	   interFrame.setVisible(true);
   }
   
   @Override
   public void run() {
	   
	   boolean continueLoop = true;
	   
	   try {
		   DataInputStream configGraphicStream = new DataInputStream(clientSocket.getInputStream());
		   
		 //Nhan man hinh tu client
		   drawGUI();
		   
		   InputStream is = clientSocket.getInputStream();
		   BufferedImage image = null;
		   
		   while(continueLoop) {
			   
			    byte[] sizeInByte = new byte[64];
			    is.read(sizeInByte);
				int length = ByteBuffer.wrap(sizeInByte).asIntBuffer().get();
				byte[] img = new byte[length];
				configGraphicStream.readFully(img);
				
				
				image = ImageIO.read(new ByteArrayInputStream(img));
					
			    image =  (BufferedImage) image.getScaledInstance(clientPanel.getWidth(), clientPanel.getHeight(), Image.SCALE_FAST);
			   
				Graphics graphics = clientPanel.getGraphics();
				graphics.drawImage(image, 0, 0, clientPanel.getWidth(), clientPanel.getHeight(), clientPanel);
				image.flush();
				 
				System.out.println("Receiving image");
				Thread.sleep(30);
		   }
	   } catch (Exception e) {
		   e.printStackTrace();
	}
	   
	   
	
	
	
//	   try {
//		   //client's screen size
//		   InputStream is = clientSocket.getInputStream();
//		   clientScreenDim = (Rectangle) is.read();
//	   } catch(IOException e) {
//		   e.printStackTrace();
//	   } catch (ClassNotFoundException e) {
//		// TODO: handle exception
//		   e.printStackTrace();
//	   }
	   
	  
	   
   }
}
