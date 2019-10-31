package remoteClient;

import java.awt.Robot;
import java.net.Socket;
import java.util.Scanner;

class ServerDelegate extends Thread {
	 Socket clientSocket = null;
	 Robot robot = null;
	 boolean continueLoop = true;
	 
	 public ServerDelegate(Socket clientSocket, Robot robot) {
		 this.clientSocket = clientSocket;
		 this.robot = robot;
		 start();
		 
	 }
	 
	 public void run() {
		 Scanner scanner = null;
		 try {
			 System.out.println("create inputStream");
			 scanner = new Scanner(clientSocket.getInputStream());
			 
			 while(continueLoop) {
				 System.out.println("waiting for command");
				 int command = scanner.nextInt();
				 System.out.println("new command: " + command);
				 switch (command) {
				 case -1:
                     robot.mousePress(scanner.nextInt());
                 break;
                 case -2:
                     robot.mouseRelease(scanner.nextInt());
                 break;
                 case -3:
                     robot.keyPress(scanner.nextInt());
                 break;
                 case -4:
                     robot.keyRelease(scanner.nextInt());
                 break; 	 
                 case -5:
                     robot.mouseMove(scanner.nextInt(), scanner.nextInt());
                 break;
				}
			 }
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
	 }
}
