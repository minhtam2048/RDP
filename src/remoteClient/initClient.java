package remoteClient;

import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class initClient {
	Socket clientSocket = null;
	
	public static void main(String[] args) {
		String ip = JOptionPane.showInputDialog("Nhap dia chi IP cua server");
		String port = JOptionPane.showInputDialog("Nhap cong ket noi");
	    new initClient().initialize(ip, Integer.parseInt(port));
	}
	
	public void initialize(String ip, int port) {
		
		Robot robot = null;
		Rectangle rectangle = null;
		
		try {
			System.out.println("Connecting to server");
			clientSocket = new Socket(ip, port);
			System.out.println("Connection successed!");
			
			GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
			
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			rectangle = new Rectangle(dim);
			robot = new Robot(gDev);
			
			//ve giao dien phia client
			drawGUI();
			
			//gui anh chup toi server
			new ScreenSpyer(clientSocket, robot, rectangle);
			
			// Nhan lenh tu server va thuc thi 
			new ServerDelegate(clientSocket, robot);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	private void drawGUI() {
		JFrame frame = new JFrame("remote admin");
		JButton button = new JButton("Close");
		frame.setBounds(100, 100, 150, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(button);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
        );
        frame.setVisible(true);
	}
}
