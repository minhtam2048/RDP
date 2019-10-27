package remoteServer;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

class ClientCreation extends Thread {
	
	private Socket clientSocket = null;
	private JDesktopPane dpane = null;

	private JInternalFrame interFrame = new JInternalFrame("client", true, true, true);
	private JPanel clientPanel = new JPanel();
	
	
   public ClientCreation(Socket clientSocket,JDesktopPane dpane) {
 		// TODO Auto-generated constructor stub
        this.clientSocket = clientSocket;
        this.dpane = dpane;
        start();
	}
   
   
   public void drawGUI() {
	   interFrame.setLayout(new BorderLayout());
	   interFrame.getContentPane().add(clientPanel, BorderLayout.CENTER);
	   interFrame.setSize(100,100);
	   dpane.add(interFrame);
	   try {
		   interFrame.setMaximum(true);
	   } catch(PropertyVetoException e) {
		   e.printStackTrace();
	   }
	   
	   clientPanel.setFocusable(true);
	   interFrame.setVisible(true);
   }
   
   @Override
   public void run() {
	   
	   Rectangle clientScreenDim = null;
	   //Nhan man hinh tu client
	   ObjectInputStream ois = null;
	   drawGUI();
	   
	   try {
		   //client's screen size
		   ois = new ObjectInputStream(clientSocket.getInputStream());
		   clientScreenDim = (Rectangle) ois.readObject();
	   } catch(IOException e) {
		   e.printStackTrace();
	   } catch (ClassNotFoundException e) {
		// TODO: handle exception
		   e.printStackTrace();
	   }
	   
	   new ClientScreenReceiver(ois,clientPanel);
	   
	   new ClientCommandSender(clientSocket, clientPanel, clientScreenDim);
   }
}
