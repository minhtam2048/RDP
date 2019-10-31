package remoteServer;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class initServer {
	
	private JFrame frame = new JFrame();
	//include screen of connected client
	private JDesktopPane dpane = new JDesktopPane();
	
	public static void main(String[] args) {
		String port = JOptionPane.showInputDialog("Tạo cổng kết nối: ");
		new initServer().initialize(Integer.parseInt(port));
	}
	
	public void initialize(int port) {
		
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();
			System.out.println("Connected");

			//vẽ giao diện server
			drawGUI();
			
			while(true) {
				new ClientCreation(clientSocket, dpane);
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public void drawGUI() {
		frame.add(dpane, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame in highest status
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
}
